<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp"%>
<%
request.setCharacterEncoding("utf-8");
DecimalFormat formatter = new DecimalFormat("###,###");
MemberInfo memberInfo = (MemberInfo)request.getAttribute("memberInfo");
MemberStatus memberStatus = (MemberStatus)request.getAttribute("memberStatus");
ArrayList<SellInfo> sellInfoList = (ArrayList<SellInfo>)request.getAttribute("sellInfoList");
ArrayList<BuyInfo> buyInfoList = (ArrayList<BuyInfo>)request.getAttribute("buyInfoList");
ArrayList<MemberPoint> pointList = (ArrayList<MemberPoint>)request.getAttribute("pointList");
ArrayList<MemberQuestion> questionList = (ArrayList<MemberQuestion>)request.getAttribute("questionList");

String status = memberInfo.getMi_status();
if (status.equals("a"))		status = "정상";
else						status = "탈퇴";
%>
<style>
tr {
	height: 50px;
}

th {
	font-size: 20px;
}

hr {
	margin: 10px 0;
}

#page-contents {
	width: 1000px;
	margin: 50px 0 50px 500px;
}

#page-contents h2 {
	margin: 20px 0;
	padding-bottom: 20px;
	font-size: 2.3em;
	text-align: center;
	border-bottom: 1px solid #000;
}
#page-content span {
	width: 100%;
	height: 1px;
	border-top: 1px solid #000;
}


.container{
	margin: 0 auto;
}

ul.tabs{
	margin: 0px;
	padding: 0px;
	list-style: none;
	display: flex;
}
ul.tabs li{
	width:25%;
	background: none;
	color: #222;
	display: inline-block;
	padding: 10px 15px;
	cursor: pointer;
	border: 1px solid #000;
	text-align: center;
}

ul.tabs li.current{
	background: #ededed;
	color: #222;
}

.tab-content{
	display: none;
	margin: 20px 0;
}

.tab-content.current{
	display: inherit;
}
.tab-content td {
	text-align: center;
}


.btn {
	font-size: 2.1em;
    margin: 20px;
    padding: 10px 20px 10px 20px;
}






</style>
<script>
$(document).ready(function(){
	
	$('ul.tabs li').click(function(){
		var tab_id = $(this).attr('data-tab');

		$('ul.tabs li').removeClass('current');
		$('.tab-content').removeClass('current');

		$(this).addClass('current');
		$("#"+tab_id).addClass('current');
	})

})
</script>
<div id="page-contents">
	<div>
		<h2>회원 관리</h2>
		<span></span>
		<table width="1000" align="center">
			<tr>
				<th width="15%">아이디</th>
				<td width="35%"><%=memberInfo.getMi_id() %></td>
				<th width="15%">이름</th>
				<td width="35%"><%=memberInfo.getMi_name() %></td>
			</tr>
			<tr>
				<th width="15%">생년월일</th>
				<td width="35%"><%=memberInfo.getMi_birth() %></td>
				<th width="15%">성별</th>
				<td width="35%"><%=memberInfo.getMi_gender() %></td>
			</tr>
			<tr>
				<th width="15%">연락처</th>
				<td width="35%"><%=memberInfo.getMi_phone() %></td>
				<th width="15%">이메일</th>
				<td width="35%"><%=memberInfo.getMi_email() %></td>
			</tr>
			<tr>
				<th width="15%">포인트</th>
				<td width="35%"><%=formatter.format(memberInfo.getMi_point()) %> P</td>
				<th width="15%">상태</th>
				<td width="35%"><%=status %></td>
			</tr>
			<tr>
				<th width="15%">집주소</th>
				<td width="*" colspan="3"><%=memberInfo.getMi_addr1() %> <%=memberInfo.getMi_addr2() %></td>
			</tr>
<%
if (status.equals("탈퇴")) {
%>
			<tr>
				<th width="15%">탈퇴 사유</th>
				<td width="*" colspan="3"><%=memberStatus.getMs_reason() %></td>
			</tr>
			<tr>
				<th width="15%">탈퇴 일자</th>
				<td width="*" colspan="3"><%=memberStatus.getMs_date() %></td>
			</tr>
<% }else { %>
		<tr style="text-align:center">
			<td colspan="2">
			<input type="button" value="포인트 관리" class="btn"
			onclick="window.open('./member/member_status.jsp', 'window_name', 'width=500, height=200, location=no, status=no, scrollbars=yes');" />
			</td>
			
			<td colspan="2">
			<input type="button" value="회원 탈퇴" class="btn" 
			onclick="window.open('https://naver.com', 'window_name', 'width=430, height=500, location=no, status=no, scrollbars=yes');"/>
			</td>
		</tr>
<%} %>
		</table>
	</div>

 <div class="container">

	<ul class="tabs">
		<li class="tab-link current" data-tab="tab-1">회원 구매 내역</li>
		<li class="tab-link" data-tab="tab-2">회원 판매 내역</li>
		<li class="tab-link" data-tab="tab-3">회원 포인트 내역</li>
		<li class="tab-link" data-tab="tab-4">회원 문의 내역</li>
	</ul>

	<div id="tab-1" class="tab-content current">
		<table width="1000" align="center" border="1" cellpadding="3" cellspacing="0">
			<tr>
				<th width="10%">구분</th>
				<th width="25%">주문번호</th>
				<th width="*%">제품명</th>
				<th width="15%">가격</th>
				<th width="15%">상태</th>
				<th width="15%">날짜</th>
			</tr>
<%
	if (sellInfoList.size() > 0) {
		for (int i = 0 ; i < sellInfoList.size() ; i++) {	
			SellInfo sellInfo = sellInfoList.get(i);
				
				
				String sellStatus = sellInfo.getSi_status();
				switch (sellStatus) {
			case "a":
				sellStatus = "입금대기중";
				break;
			case "b":
				sellStatus = "배송준비중";
				break;
			case "c":
				sellStatus = "배송중";
				break;
			case "d":
				sellStatus = "배송완료";
				break;
			case "e":
				sellStatus = "구매 완료 ";
				break;
			case "f":
				sellStatus = "주문취소";
				break;
				}
				String date = (sellInfo.getSi_date()).substring(0,10);
			
			
%>
			<tr>
				<td width="10%">구매</td>
				<td width="25%"><%=sellInfo.getSi_id() %></td>
				<td width="*%"><%=sellInfo.getSd_mname() %></td>
				<td width="15%"><%=formatter.format(sellInfo.getSi_pay()) %> 원</td>
				<td width="15%"><%=sellStatus %></td>
				<td width="15%"><%=date %></td>
			</tr>
<%}
		}%>
		</table>
	</div>
	<div id="tab-2" class="tab-content">
		<table width="1000" align="center" border="1" cellpadding="3" cellspacing="0">
			<tr>
				<th width="10%">구분</th>
				<th width="25%">주문번호</th>
				<th width="*%">제품명</th>
				<th width="15%">가격</th>
				<th width="15%">상태</th>
				<th width="15%">날짜</th>
			</tr>
<%
if (buyInfoList.size() > 0) {
for (int i = 0; i < buyInfoList.size(); i++) {
	BuyInfo buyInfo = buyInfoList.get(i);
	
	
	String buyStatus = buyInfo.getBi_status();
	switch (buyStatus) {
	case "a":
		buyStatus = "판매 신청";
		break;
	case "b":
		buyStatus = "판매 취소";
		break;
	case "c":
		buyStatus = "승인 거절";
		break;
	case "d":
		buyStatus = "1차 검수 완료";
		break;
	case "e":
		buyStatus = "배송 대기";
		break;
	case "f":
		buyStatus = "배송중";
		break;
	case "g":
		buyStatus = "상품 도착";
		break;
	case "h":
		buyStatus = "2차 검수 완료";
		break;
	case "i":
		buyStatus = "대금 수령 선택";
		break;
	case "j":
		buyStatus = "입금 대기";
		break;
	case "k":
		buyStatus = "판매 완료";
		break;
}
	String buyDate = (buyInfo.getBi_date()).substring(0,10);
%>
			<tr>
				<td width="10%">판매</td>
				<td width="25%"><%=buyInfo.getBi_id() %></td>
				<td width="*%"><%=buyInfo.getPi_name() %></td>
				<td width="15%"><%=formatter.format(buyInfo.getBi_pay()) %> 원</td>
				<td width="15%"><%=buyStatus %></td>
				<td width="15%"><%=buyDate %></td>
			</tr>
<%
	}
}%>
		</table>
	</div>
	<div id="tab-3" class="tab-content">
		<table width="1000" align="center" border="1" cellpadding="3" cellspacing="0">
			<tr>
				<th width="10%">구분</th>
				<th width="20%">사유</th>
				<th width="20%">변동내용</th>
				<th width="20%">합계</th>
				<th width="*">상세 내역</th>
			</tr>
<%
if (pointList.size() > 0) {
	ArrayList<MemberPoint> pointList_temp = new ArrayList<MemberPoint>();
	MemberPoint memberPoint_temp = null;
	int changePoint = 0;

	for (int i = 0; i < pointList.size(); i++) {

		MemberPoint memberPoint = pointList.get(i);
		
		String pointStatus = memberPoint.getMp_su();
		int point = memberPoint.getMp_point();
		int idx = memberPoint.getMp_idx();
		switch (pointStatus) {
			case "u":
				pointStatus = "사용";
				point = point * -1;
				break;
			case "s":
				pointStatus = "적립";
				break;
		}
		changePoint += point;
		memberPoint_temp = new MemberPoint();
		memberPoint_temp.setMp_su(status);
		memberPoint_temp.setMp_desc(memberPoint.getMp_desc());
		memberPoint_temp.setMp_point(point);
		memberPoint_temp.setMp_changePoint(changePoint);
		memberPoint_temp.setMp_detail(memberPoint.getMp_detail());
		pointList_temp.add(memberPoint_temp);
	}
	for (int i = 0 ; i < pointList_temp.size() ; i++) {
		MemberPoint memberPoint = pointList_temp.get(pointList_temp.size() - i - 1);

%>
			<tr>
				<td width="10%"><%=memberPoint.getMp_su() %></td>
				<td width="20%"><%=memberPoint.getMp_desc() %></td>
				<td width="20%"><%=formatter.format(memberPoint.getMp_point()) %></td>
				<td width="20%"><%=formatter.format(memberPoint.getMp_changePoint()) %></td>
				<td width="*"><%=memberPoint.getMp_detail() %></td>
			</tr>
<%}
	}%>
		</table>
	</div>
	<div id="tab-4" class="tab-content">
		<table width="1000" align="center" border="1" cellpadding="3" cellspacing="0">
			<tr>
				<th width="15%">상태</th>
				<th width="*">제목</th>
				<th width="15%">등록일</th>
			</tr>
			
<%
if (questionList.size() > 0) {
	for (int i = 0; i < questionList.size(); i++) {
		MemberQuestion memberQuestion = questionList.get(i);
		String questionStatus = memberQuestion.getBmq_status();
		int idx = memberQuestion.getBmq_idx();
		switch (questionStatus) {
			case "a":
				questionStatus = "답변대기중";
				break;
			case "b":
				questionStatus = "답변완료";
				break;
		}
		String date = (memberQuestion.getBmq_date()).substring(0,10);
%>			
			<tr>
				<td width="15%"><%=questionStatus %></td>
				<td width="*"><%=memberQuestion.getBmq_title() %></td>
				<td width="15%"><%=date %></td>
			</tr>
<%}
	}%>
		</table>
	</div>

</div>


</div>
</body>
</html>
