<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@ include file="../_inc/inc_head.jsp" %>
<%
request.setCharacterEncoding("utf-8");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
ArrayList<ProductOption> productStockList = (ArrayList<ProductOption>)request.getAttribute("productStockList");
DecimalFormat formatter = new DecimalFormat("###,###");

int cpage = pageInfo.getCpage(), psize = pageInfo.getPsize();
int rcnt = pageInfo.getRcnt(), spage = pageInfo.getSpage();
int bsize = pageInfo.getBsize(), pcnt = pageInfo.getPcnt();
String keyword = pageInfo.getKeyword();
String schtype = pageInfo.getSchtype();

String args = "", sargs = "";
//쿼리 스트링으로 상세보기를 저장할 변수
if (schtype != null && !schtype.equals("") &&
	keyword != null && !keyword.equals("")){
	sargs += "&schtype=" + schtype + "&keyword=" + keyword;
}
%>
<script>
function onlyNum(obj) {
	if (isNaN(obj.value)) {
		obj.value = "";
	}
}

function makeSch() {
// 검색 조건들로 sch의 값을 만듦 
	var frm = document.frm;
	var schtype = "";
	// 모델명 검색어 조건
	var keyword = frm.keyword.value;
	if (keyword != "")	schtype += keyword;
	
	if (keyword == "") {
		alert("검색어을 입력하세요.");	return;
	}
	document.frm.schtype.value = schtype;
	document.frm.submit();
}



function cartUp(poidx, cnt) {
// 장바구니 내 수량 변경
	$.ajax({
		type : "POST",
		url : "/greenAdmin/product_stock_proc",
		data : {"poidx" : poidx, "cnt" : cnt},
		success : function(chkRs) {
			if (chkRs == 0) {
				alert("상품 변경에 실패했습니다.\n다시 시도하세요.");
			}
			location.reload();
		}
	});
}

function setCnt(chk, poidx) {
// 버튼 수량 변경
	var frm = document.frmisview;
	var cnt = parseInt(eval("frm.cnt" + poidx + ".value"));

	if (chk == "+") {
		cartUp(poidx, cnt);
	} else if (chk == "-" && cnt > 0) {
		cartUp(poidx, cnt * -1);
	}
}

</script>
<style>
tr { height:50px; }
th { font-size:20px; }
.page-contents {width:1000px; margin:50px 0 50px 500px;}
#schkeyword { margin:30px 0; }
#pdt { width:200px; height:30px; }
#btnsch { width:70px; height:30px; font-size:20px; }
.isView { width:100px; font-size:20px;}
.btn { width:70px; font-size:20px;}
.pageinfo { margin:30px 0; }
.nothing { margin:80px 0; font-size:25px; }

</style>
<div class="page-contents">
	<h2>상품 재고 관리</h2>
	<form name="frm" action="product_stock_list" method="get">
		<div id="schkeyword">

			<p align="right">
				<select name="schtype">
					<option>검색조건</option>
					<option value="pi_name"
					<% if(schtype.equals("pi_name")) { %>selected="selected"<% } %>>모델명</option>
					<option value="pb_name"
					<% if(schtype.equals("pb_name")) { %>selected="selected"<% } %>>브랜드</option>
				</select>
				<input type="text" style="display:none;" />
				<input type="hidden" name="schtype" value="<%=schtype %>" />
				<input type="text" name="keyword" id="keyword" placeholder="검색어를 입력해주세요." value="<%=keyword %>" />&nbsp;&nbsp;&nbsp;
				<input type="button" value="찾기" id="btnsch" onclick="makeSch()" />
			</p>
		</div>
	</form>
	<form name="frmisview" action="product_stock_proc" method="post">
	<table width="1000" align="center" border="1" cellpadding="3" cellspacing="0">
		<tr>
			<th width="15%">No</th>
			<th width="10%">브랜드</th>
			<th width="18%">모델명</th>
			<th width="*%">용량</th>
			<th width="13%">색상</th>
			<th width="13%">등급</th>
			<th width="15%">재고량</th>
		</tr>
		<%
		if (rcnt > 0) {	// 검색된 상품 목록이 있을 경우
			String lnk = "product_stock_list?cpage=";
			for (int i = 0 ; i < productStockList.size() ; i++) {
				ProductOption po = productStockList.get(i);
				int poidx = po.getPo_idx();
				pcnt = rcnt / psize;
				if (rcnt % psize > 0)	pcnt++;	// 전체 페이지 수(마지막 페이지 번호)
				
		%>
		<tr align="center">
			<td><%=po.getPo_idx() %></td>
			<%
				String color = po.getPo_color();
				switch (color) {
				case "w":
					color = "화이트";
					break;
				case "b":
					color = "블랙";
					break;
				}
				String brand = "애플";
				if(po.getPi_id().charAt(0) == 's')	brand = "삼성";
			%>
			<td><%=brand %></td>
			<td><%=po.getPi_name() %></td>
			<td><span><%=po.getPo_memory() %></span> GB</td>
			<td><%=color %></td>
			<td><%=po.getPo_rank() %></td>
			<td>
			<%=po.getPo_stock() %>
			<input type="button" value="-" onclick="setCnt(this.value, <%=poidx %>);" />
			<input type="text" name="cnt<%=poidx %>" id="cnt" style="width:20px; text-align:right;" value="1" onkeyup="onlyNum(this);" />
			<input type="button" value="+" onclick="setCnt(this.value, <%=poidx %>);" />
			</td>
		</tr>
		<% 
			}
		
			out.println("</table>");
			
			out.println("<p class='pageinfo' align='center'>");
			
			if (cpage == 1) {
				out.println("[처음]&nbsp;&nbsp;[이전]&nbsp;");
			} else {
				out.println("<a href='" + lnk + 1 + sargs + "'>[처음]</a>&nbsp;&nbsp;&nbsp;");
				out.println("<a href='" + lnk + (cpage - 1) + sargs + "'>[이전]</a>&nbsp;&nbsp;");
			}
			
			for (int i = 1, j = spage ; i <= bsize && j <= pcnt ; i++, j++) {
				if (cpage == j) {
					out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
				} else {
					out.println("&nbsp;<a href='" + lnk + j + sargs + "'>" + j + "</a>&nbsp;");
				}
			}
			
			if (cpage == pcnt) {
				out.println("&nbsp;[다음]&nbsp;&nbsp;[마지막]");
			} else {
				out.println("&nbsp;&nbsp;<a href='" + lnk + (cpage + 1) + sargs + "'>[다음]</a>");
				out.println("&nbsp;&nbsp;&nbsp;<a href='" + lnk + pcnt + sargs + "'>[마지막]</a>");
			}
			out.println("</p>");
		} else {		// 검색된 상품 목록이 없을 경우
			out.println("</table>");
			out.println("<p class='nothing' align='center'>검색 결과가 없습니다.</p>");
		} %>
	</table>
	</form>
</div>

</body>
</html>