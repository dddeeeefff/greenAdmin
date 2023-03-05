<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp" %>
<%
BuyInfo buyInfo = (BuyInfo)request.getAttribute("buyInfo");
String biid = buyInfo.getBi_id();
String brand = "애플";
if (biid.charAt(0) == 's')		brand = "삼성";
String series = buyInfo.getPi_id().substring(1, 3) + " 시리즈";
String color = "블랙";
if (buyInfo.getBi_color().equals("w"))		color = "화이트";
String invoice = "-";
if (buyInfo.getBi_invoice() != null && !buyInfo.getBi_invoice().equals(""))		invoice = buyInfo.getBi_invoice();
String predict = "-";
if (buyInfo.getBi_predict() != 0)	predict = String.format("%,d", buyInfo.getBi_predict());
String fixed = "-";
if (buyInfo.getBi_pay() != 0)		fixed = String.format("%,d", buyInfo.getBi_pay());
String desc = buyInfo.getBi_desc();
String bank = buyInfo.getBi_bank();
String account = buyInfo.getBi_account();
String rank = "";
if (buyInfo.getBi_rank() != null && !buyInfo.getBi_rank().equals(""))		rank = buyInfo.getBi_rank();
String status = buyInfo.getBi_status();
String[] values = {"판매 신청", "판매 취소", "승인 거절", "1차 검수 완료", "배송 대기", "배송중", "상품 도착", "2차 검수 완료", "대금 수령 선택", "입금 대기", "판매 완료"};
String optionA = "<option value='a' selected='selected'>" + values[0] + "</option>";
String optionC = "<option value='c'>" + values[2] + "</option>";
String optionD = "<option value='d'>" + values[3] + "</option>";
String optionF = "<option value='f' selected='selected'>" + values[5] + "</option>";
String optionG = "<option value='g'>" + values[6] + "</option>";
String optionH = "<option value='h'>" + values[7] + "</option>";
String optionJ = "<option value='j' selected='selected'>" + values[9] + "</option>";
String optionK = "<option value='k'>" + values[10] + "</option>";
%>
	<link rel="stylesheet" href="/greenAdmin/src/css/buy_form.css">
	<script>
		function sub() {
			var frm = document.frm;
<% if (status.equals("a") || status.equals("g")) { %>
			var rank = frm.birank;
			if (rank.value == "") {
				alert('등급을 선택하세요.');
				rank.focus();
				return;
			}
			var rValue = rank.value;
<% } else { %>
			var rValue = "<%=rank %>";
<% } %>
			var status = frm.bistatus;
			if (status.value == "<%=status %>") {
				alert('단계를 변경하세요.');
				status.focus();
				return;
			}
			
			var sValue = status.value;
			
			$.ajax({
				type : "POST", 
				url : "/greenAdmin/buy_proc", 
				data : {"biid" : "<%=buyInfo.getBi_id() %>", "birank" : rValue, "bistatus" : sValue}, 
				success : function(chkRs) {
					if ((status.value == "k" && chkRs == 2) || chkRs == 1) {
						location.reload();
					} else {
						alert("수정에 실패하였습니다.\\n다시 시도하세요.");
					}
				}
			});
			
		}
	</script>
	<% if (rank.equals(""))		rank = "-"; %>
    <div class="page-contents">
        <div class="content-wrapper">
            <h3>구매 상품 관리 수정</h3>
            <div class="info">
                <div class="title">
                    <h4>No. <%=biid %></h4>
                    <p>등록일 : <%=buyInfo.getBi_date().substring(0, 10) %></p>
                </div>
                <div class="detail">
                    <div class="imgs">
                        <div class="img-row">
                            <figure>
                                <img src="/greenPhone/sell/upload/<%=buyInfo.getBi_img1() %>" alt="a1" />
                            </figure>
                            <figure>
                                <img src="/greenPhone/sell/upload/<%=buyInfo.getBi_img2() %>" alt="a2" />
                            </figure>
                        </div>
                        <div class="img-row">
                            <figure>
                                <img src="/greenPhone/sell/upload/<%=buyInfo.getBi_img3() %>" alt="a3" />
                            </figure>
                            <figure>
                                <img src="/greenPhone/sell/upload/<%=buyInfo.getBi_img4() %>" alt="a4" />
                            </figure>
                        </div>
                    </div>
                    <div class="texts">
                        <div class="text-column">
                            <div class="key">
                                <p>브랜드</p>
                                <p>시리즈</p>
                                <p>모델 명</p>
                                <p>예상 가격</p>
                            </div>
                            <div class="value">
                                <p><%=brand %></p>
                                <p><%=series %></p>
                                <p><%=buyInfo.getPi_name() %></p>
                                <p id="predict"><%=predict %>원</p>
                            </div>
                        </div>
                        <div class="text-column">
                            <div class="key">
                                <p>색상</p>
                                <p>용량</p>
                                <p>송장 번호</p>
                                <p>최종 가격</p>
                            </div>
                            <div class="value">
                                <p><%=color %></p>
                                <p><%=buyInfo.getBi_memory() + " GB" %></p>
                                <p><%=invoice %></p>
                                <p id="confirmed"><%=fixed %>원</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="more">
<% if (desc != null && !desc.equals("")) { %>
                    <div class="desc">
                        <p>추가 정보</p>
                        <p><%=desc %></p>
                    </div>
<%
} 
if (bank != null && !bank.equals("") && account != null && !account.equals("")) {
%>
                    <div class="account">
                        <p>신한 110123456789</p>
                        <p>입금 대기중</p>
                    </div>
<% } %>
                </div>
                <div class="frm-wrapper">
                    <form name="frm">
                    	<input type="hidden" name="biid" value="<%=buyInfo.getBi_id() %>" />
<% if (status.equals("a") || status.equals("g")) { %>
                        <div class="frm-row">
                            <p>등급</p>
                            <select name="birank">
                                <option value="">등급 선택</option>
                                <option value="s" <% if (rank.equals("s")) { %>selected="selected"<% } %>>S</option>
                                <option value="a" <% if (rank.equals("a")) { %>selected="selected"<% } %>>A</option>
                                <option value="b" <% if (rank.equals("b")) { %>selected="selected"<% } %>>B</option>
                            </select>
                        </div>
<% } else { %>
						<div class="frm-row">
							<p>등급</p>
							<p><%=rank.toUpperCase() %></p>
						</div>
<% } %>
                        <div class="frm-row">
                            <p>단계</p>
<% if (status.equals("a") || status.equals("f") || status.equals("g") || status.equals("j")) { %>
                            <select name="bistatus">
<%
		if (status.equals("a")) {
			out.println(optionA);
			out.println(optionC);
			out.println(optionD);
		} else if (status.equals("f")) {
			out.println(optionF);
			out.println(optionG);
		} else if (status.equals("g")) {
			out.println("<option value='g' selected='selected'>" + values[6] + "</option>");
			out.println(optionC);
			out.println(optionH);
		} else if (status.equals("j")) {
			out.println(optionJ);
			out.println(optionK);
		}
%>							
                            </select>
<% } else { 
		String value = values[3];		// d
		if (status.equals("b"))				value = values[1];
		else if (status.equals("c"))		value = values[2];
		else if (status.equals("e"))		value = values[4];
		else if (status.equals("h"))		value = values[7];
		else if (status.equals("i"))		value = values[8];
		else if (status.equals("k"))		value = values[10];

%>
							<p><%=value %></p>
<% } %>
                        </div>
                    </form>
                </div>
<% if (status.equals("a") || status.equals("f") || status.equals("g") || status.equals("j")) { %>
                <div class="btns">
                    <input type="button" value="취소" onclick="javascript:history.back();" />
                    <input type="button" value="등록" onclick="sub();" />
                </div>
<% } %>
            </div>
        </div>
    </div>
</body>
</html>