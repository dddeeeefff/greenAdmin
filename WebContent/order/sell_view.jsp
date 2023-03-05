<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp" %>
<%
request.setCharacterEncoding("utf-8");
ArrayList<SellDetail> detailList = (ArrayList<SellDetail>)request.getAttribute("detailList");
SellInfo sellInfo = (SellInfo)request.getAttribute("sellInfo");
DecimalFormat formatter = new DecimalFormat("###,###");
String pstatus = sellInfo.getSi_payment();
switch (pstatus) {
case "a":
	pstatus = "카드 결제";
	break;
case "b":
	pstatus = "휴대폰 결제";
	break;
case "c":
	pstatus = "무통장 입금";
	break;
case "d":
	pstatus = "포인트 결제(전액)";
	break;
}

String status = sellInfo.getSi_status();

String[] values = {"입금대기중", "배송준비중", "배송중", "배송완료", "구매완료", "주문취소"};
String optionAS = "<option name='opt' id='opt' value='a' selected='selected'>" + values[0] + "</option>";
String optionB = "<option name='opt' id='opt' value='b'>" + values[1] + "</option>";
String optionBS = "<option name='opt' id='opt' value='b' selected='selected'>" + values[1] + "</option>";
String optionC = "<option name='opt' id='opt' value='c'>" + values[2] + "</option>";
String optionCS = "<option name='opt' id='opt' value='c' selected='selected'>" + values[2] + "</option>";
String optionDS = "<option name='opt' id='opt' value='d' selected='selected'>" + values[3] + "</option>";
String optionD = "<option name='opt' id='opt' value='d' >" + values[3] + "</option>";
String optionE = "<option name='opt' id='opt' value='e' >" + values[4] + "</option>";
String optionF = "<option name='opt' id='opt' value='f' >" + values[5] + "</option>";


%>

<style>
#page-contents {
	width:1000px;	margin:50px 0 50px 500px;
}
#invoiceGo{ width:70px;}
.frmFrame{ display:flex; justify-content: space-between; flex-direction: column; border-top:2px solid blue; border-bottom:2px solid blue}
.frmFrame-1{ display:flex; justify-content: flex-end}
table{ border-collapse: collapse; }
.gray { border-bottom:1px solid gray; padding:0 0 10px 0;  }
.payPoint{ display:flex; justify-content: space-between; padding:0 20%;}
.buttonList{ display:flex; justify-content: space-around; }
.variableBox{ justify-content: flex-end;}
#statusBox{ width:200px; height:30px; border:1px solid black; }
#opt{ width:250px; height:30px; border:1px solid black; text-align:left; vertical-align:center;}
</style>
<script>
function sendData() {
		var select = document.getElementById("statusBox").value;
		
		if (select == '<%=sellInfo.getSi_status() %>') {
			alert('단계를 선택하세요.');
			return;
		}
		
		var status = document.getElementById("getStatus");
		
		status.value = select;
		
		document.frmButton.submit();
		
	}




function changeInvoice(si_id, invoice){
var si_id = document.frm.si_id.value;
var invoice = document.frm.invoice.value;
	$.ajax({
		type : "POST",
		url : "sell_proc",
		data : {"si_id" : si_id, "invoice" : invoice},
		success : function(chkRs){
			if (invoice == ""){
				alert("송장번호를 입력해 주세요.")
			}else{
				$("#invoice").val(invoice);
				alert("송장번호가 저장되었습니다.")
				location.reload();
			}
		}
	})
}
</script>
<div id="page-contents">
	<h2>주문 상세 정보</h2>
	<br />
	<div class="content">
	<br />

	<div class="si_id_text" >No.<%=sellInfo.getSi_id() %></div>

	<br />
	
	<div class="frmFrame">
		<div style="display:flex; justify-content: space-between;">
<%
			if(status.equals("c") || status.equals("d") || status.equals("e")){
				
%>		
		
		<div style="display:flex; "><span id="opt">송장번호 : <%=sellInfo.getSi_invoice() %></span></div>
					<div class="variableBox">
			
				<select name="statusBox" id="statusBox">
<%
				if(status.equals("a")){
					out.println(optionAS);
					out.println(optionB);
					out.println(optionF);
				}else if(status.equals("b")){
					out.println(optionBS);
					out.println(optionC);
					out.println(optionF);
				}else if(status.equals("c")){
					out.println(optionCS);
					out.println(optionD);
				}else if(status.equals("d")){
					out.println(optionDS);
					out.println(optionE);
				}else if(status.equals("e")){
					out.println(optionE);
				}else if(status.equals("f")){
					out.println(optionF);
				}
%>			
				</select>
			</div>

			</div>
		<div class="frmFrame-1">

			

<%
			}else if(status.equals("b")){
%>
	
			<form name="frm" action="sell_proc" method="post" >
			<input type="hidden" name="si_id" id="si_id" value="<%=sellInfo.getSi_id() %>" />
			<div>
				송장번호:		
				<input type="text" name="invoice" id="invoice" value="" />
				<input type="button" name="invoiceGo" id="invoiceGo" value="등록" onclick="changeInvoice();" />
	
			</div>

			</form>
			<div class="variableBox">
			
				<select name="statusBox" id="statusBox">
<%
				if(status.equals("a")){
					out.println(optionAS);
					out.println(optionB);
					out.println(optionF);
				}else if(status.equals("b")){
					out.println(optionBS);
					out.println(optionC);
					out.println(optionF);
				}else if(status.equals("c")){
					out.println(optionCS);
					out.println(optionD);
				}else if(status.equals("d")){
					out.println(optionDS);
					out.println(optionE);
				}else if(status.equals("e")){
					out.println(optionE);
				}else if(status.equals("f")){
					out.println(optionF);
				}
%>			
				</select>
			</div>

<%
			}else if(status.equals("a") || status.equals("f")){
%>
			<div></div>
			<div class="variableBox">
			
				<select name="statusBox" id="statusBox">
<%
				if(status.equals("a")){
					out.println(optionAS);
					out.println(optionB);
					out.println(optionF);
				}else if(status.equals("b")){
					out.println(optionBS);
					out.println(optionC);
					out.println(optionF);
				}else if(status.equals("c")){
					out.println(optionCS);
					out.println(optionD);
				}else if(status.equals("d")){
					out.println(optionDS);
					out.println(optionE);
				}else if(status.equals("e")){
					out.println(optionE);
				}else if(status.equals("f")){
					out.println(optionF);
				}
%>			
				</select>
			</div>
<%
}%>
	

		</div>
		<br />

		<table>
<%
for(int i = 0; i < detailList.size(); i++){
	

	SellDetail sd = detailList.get(i);
%>
	
			<tr class="gray">
				<td>
					<img src="/greenAdmin/product/pdt_img/<%=sd.getSd_img() %>" width="50" height="63" style="width:33%" />
				</td>
				<td>
					<span><%=sd.getSd_mname() %></span><br />
					<span><%=sd.getSd_oname() %></span>
				</td>
				<td>
					가격 : <span><%=formatter.format(sd.getSd_price()) %></span> 원<br />
					주문 수량 : <span><%=sd.getSd_cnt() %></span><br />
<% if (sd.getSd_cdate() != null && !sd.getSd_cdate().equals("")) { %>
					구매확정일 : <span><%=sd.getSd_cdate() %></span>
<% } %>
					<!-- onkeyup="onlyNum(this);  cart의 ajax 참조-->
				</td>
				<td>
					+&nbsp;<span><%=formatter.format((sd.getSd_price()/ 100) * sd.getSd_cnt()) %></span> P
				</td>
			</tr>
	
<%

}
%>
		</table>
</div>	
		<br />
	</div>
	<br />
	<br />
	<div class="payPoint">

		<div>결제 수단 : <%=pstatus %></div><div>포인트 사용 : <span><%=formatter.format(sellInfo.getSi_upoint()) %></span> P</div><div>결제 금액 : <span><%=formatter.format(sellInfo.getSi_pay()) %></span> 원</div>

	</div>
	<br />
	
	<div class="buttonList">
	<form name="frmButton" action="sell_proc_status" method="post">
		<input type="hidden" name="si_upoint" id="si_upoint" value="<%=sellInfo.getSi_upoint() %>" />
		<input type="hidden" name="si_id" id="si_id" value="<%=sellInfo.getSi_id() %>" />
		<input type="hidden" name="getStatus" id="getStatus" value="" />
		<input type="button" value="취소" onclick="location.href='/greenAdmin/sell_list'" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="확인" onclick="sendData();" />
	</form>
	</div>	
	
	</div>


</body>
</html>