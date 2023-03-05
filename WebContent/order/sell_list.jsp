<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@ include file="../_inc/inc_head.jsp" %>
<%
request.setCharacterEncoding("utf-8");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
ArrayList<SellInfo> sellList = (ArrayList<SellInfo>)request.getAttribute("sellList");
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
	args = "&cpage=" + cpage + sargs;

%>
<style>
tr { height:50px; }
th { font-size:20px; }
#page-contents {
	width:1000px;	margin:50px 0 50px 500px;
}
#schkeyword { margin:30px 0; }
#pdt { width:200px; height:30px; }
#btnsch { width:70px; height:30px; font-size:20px; }
.isView { width:100px; font-size:20px;}
.btn { width:70px; font-size:20px;}
.pageinfo { margin:30px 0; }
.nothing { margin:80px 0; font-size:25px; }
</style>
<script>
function makeSch() {
// 검색 조건들로 sch의 값을 만듦 
	var frm = document.frm;
	
	// 모델명 검색어 조건
	//var keyword = frm.keyword.value;
	//if (keyword != "")	sch += keyword;
	
	document.frm.submit();
}


</script>
<%

%>
<div id="page-contents">
	<h2>전체 주문 관리</h2>
	<form name="frm" action="sell_list" method="get">
		<div id="schkeyword">
			<p align="right">
				<select name="schtype">
					<option>검색조건</option>
					<option value="si"
					<% if(schtype.equals("si")) { %>selected="selected"<% } %>>주문번호</option>
					<option value="mi"
					<% if(schtype.equals("mi")) { %>selected="selected"<% } %>>아이디</option>
				</select>
				<input type="text" style="display:none;" />
				<input type="text" name="keyword" id="keyword" placeholder="검색어를 입력해주세요." value="<%=keyword %>" />&nbsp;&nbsp;&nbsp;
				<input type="button" value="찾기" id="btnsch" onclick="makeSch()" />
			</p>
		</div>
	</form>
	<form name="frmisview">
	<table width="1000" align="center" border="1" cellpadding="3" cellspacing="0">
		<tr>
			<th width="15%">No</th>
			<th width="10%">아이디</th>
			<th width="13%">브랜드</th>
			<th width="*%">상품명</th>
			<th width="13%">결제금액</th>
			<th width="13%">진행상태</th>
			<th width="15%">구매일</th>
		</tr>
		<%
		if (rcnt > 0) {	// 검색된 상품 목록이 있을 경우
			String lnk = "sell_list?cpage=";
			for (int i = 0 ; i < sellList.size() ; i++) {
				SellInfo si = sellList.get(i);
				String brand = "애플";
				if (si.getSi_id().charAt(0) == 's')		brand = "삼성";
				
				pcnt = rcnt / psize;
				if (rcnt % psize > 0)	pcnt++;	// 전체 페이지 수(마지막 페이지 번호)
				
		%>
		<tr align="center">
			<td><a href="sell_view?siid=<%=si.getSi_id() %>"><%=si.getSi_id() %></a></td>
			<td><%=si.getMi_id() %></td>
			<td><%=brand %></td>
			<td><%=si.getSd_mname() %></td>
			<td><span><%=formatter.format(si.getSi_pay()) %></span>원</td>
			<%
			String status = si.getSi_status();
			switch (status) {
			case "a":
				status = "입금대기중";
				break;
			case "b":
				status = "배송준비중";
				break;
			case "c":
				status = "배송중";
				break;
			case "d":
				status = "배송완료";
				break;
			case "e":
				status = "구매 완료 ";
				break;
			case "f":
				status = "주문취소";
				break;
				}
			%>
			<td><%=status %></td>
			<td><%=si.getSi_date() %></td>
		</tr>
		<% 
			}
		
		out.println("</table>");
			
		String tmp = sargs;
		// 페이징 영역 링크에서 사용할 쿼리 스트링
		
		out.println("<p class='pageinfo' align='center'>");
		
		if (cpage == 1) {
			out.println("[&lt;&lt;]&nbsp;&nbsp;[&lt;]&nbsp;");
		} else {
			out.print("<a href=\"sell_list?cpage=1" + tmp + "\">");
			out.println("[&lt;&lt;]</a>&nbsp;&nbsp;");
			out.print("<a href=\"sell_list?cpage=" + (cpage - 1) + tmp + "\">");
			out.println("[&lt;]</a>&nbsp;");
		}
		
		for (int i = 1, j = spage ; i < bsize && j <= pcnt ; i++, j++) {
			if (cpage == j) {
				out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
			} else {
				out.print("&nbsp;<a href=\"sell_list?cpage=");
				out.println(j + tmp + "\">" + j +"</a>&nbsp;");
			}
		}
		
		if (cpage == pcnt) {
			out.println("&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
		} else {
			out.print("&nbsp;<a href=\"sell_list?cpage=" + (cpage + 1) + tmp + "\">");
			out.println("[&gt;]</a>&nbsp;&nbsp;");
			out.print("<a href=\"sell_list?cpage=" + pcnt + tmp + "\">");
			out.println("[&gt;&gt;]</a>");
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