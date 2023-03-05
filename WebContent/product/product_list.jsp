<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp" %>
<%
request.setCharacterEncoding("utf-8");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
//페이징과 검색 조건들을 저장하고 있는 PageInfo형 인스턴스 pageInfo를 request에서 받아옴

ArrayList<ProductInfo> productList = (ArrayList<ProductInfo>)request.getAttribute("productList");

int cpage = pageInfo.getCpage(), psize = pageInfo.getPsize();
int rcnt = pageInfo.getRcnt(), spage = pageInfo.getSpage();
int bsize = pageInfo.getBsize(), pcnt = pageInfo.getPcnt();

String args = "", sargs = "";
//쿼리 스트링으로 상세보기를 저장할 변수

String sch = pageInfo.getSch();

if (sch != null && !sch.equals(""))	sargs += "&sch=" + sch;
args = "&cpage=" + cpage + sargs;

String name = "";
if (sch != null && !sch.equals("")) {
// sch : n모델명
	char c = sch.charAt(0);
	if (c == 'n') {	// 상품명 검색일 경우(n검색어)
		name = sch.substring(1);
	}
} else {
	sch = "";
}
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
#isView { width:100px; font-size:20px;}
.btn { width:70px; font-size:20px;}
.pageinfo { margin:30px 0; }
.nothing { margin:80px 0; font-size:25px; }
</style>
<script>
function makeSch() {
// 검색 조건들로 sch의 값을 만듦 : n모델명
	var frm = document.frm2;
	var sch = "";
	
	// 모델명 검색어 조건
	var pdt = frm.pdt.value;
	if (pdt != "")	sch += "n" + pdt;
	
	document.frm1.sch.value = sch;
	document.frm1.submit();
}

function isView(opt, piid) {
// 게시 or 미게시 여부 설정
	$.ajax({
		type : "POST",
		url : "product_list_view",
		data : { "piid" : piid, "opt" : opt }, 
		success : function(chkRs) {
			if (chkRs == 1) {
				location.reload();
			} else {
				alert("수정이 되지 않았습니다.\n다시 시도하세요.");
			}
			
		}
	})
	
	
}
</script>
<div id="page-contents">
	<h2>판매 상품 관리</h2>
		<div id="schkeyword">
			<form name="frm1">
				<input type="hidden" name="sch" value="<%=sch %>" />
			</form>
		<form name="frm2">
			<p align="right">		
				<input type="text" style="display:none;" />
				<input type="text" name="pdt" id="pdt" placeholder="모델명을 입력해주세요." value="<%=name %>" />&nbsp;&nbsp;&nbsp;
				<input type="button" value="찾기" id="btnsch" onclick="makeSch();" />		
			</p>
		</form>
		</div>
	<table width="1000" align="center" border="1" cellpadding="3" cellspacing="0">
		<tr>
			<th width="15%">No</th>
			<th width="10%">브랜드</th>
			<th width="*">모델</th>
			<th width="13%">재고량</th>
			<th width="15%">게시여부</th>
			<th width="13%">관리</th>
		</tr>
		<%
		if (rcnt > 0) {	// 검색된 상품 목록이 있을 경우
			String lnk = "product_list?cpage=" + cpage + sargs;
			for (int i = 0 ; i < productList.size() ; i++) {
				ProductInfo pi = productList.get(i);
				lnk = "product_form?piid=" + pi.getPi_id();
				String isView = pi.getPi_isview();
		%>
		<input type="hidden" name="piid" value="<%=pi.getPi_id() %>" />
		<tr align="center">
			<td><%=pi.getPi_id() %></td>
			<td><%=pi.getPb_name() %></td>
			<td><%=pi.getPi_name() %></td>
			<td><%=pi.getStock() %></td>
			<td>
				<select name="isView" id="isView" onchange="isView(this.value, '<%=pi.getPi_id() %>');">
					<option value="n" id="opt" name="opt" <% if (isView.equals("n")) { %>selected="selected"<% } %>>미게시</option>
					<option value="y" id="opt" name="opt" <% if (isView.equals("y")) { %>selected="selected"<% } %>>게시</option>
				</select>
			</td>
			<td><input type="button" class="btn" value="관리" onclick="location.href='<%=lnk %>';" /></td>
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
			out.print("<a href=\"product_list?cpage=1" + tmp + "\">");
			out.println("[&lt;&lt;]</a>&nbsp;&nbsp;");
			out.print("<a href=\"product_list?cpage=" + (cpage - 1) + tmp + "\">");
			out.println("[&lt;]</a>&nbsp;");
		}
		
		for (int i = 1, j = spage ; i < bsize && j <= pcnt ; i++, j++) {
			if (cpage == j) {
				out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
			} else {
				out.print("&nbsp;<a href=\"product_list?cpage=");
				out.println(j + tmp + "\">" + j +"</a>&nbsp;");
			}
		}
		
		if (cpage == pcnt) {
			out.println("&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
		} else {
			out.print("&nbsp;<a href=\"product_list?cpage=" + (cpage + 1) + tmp + "\">");
			out.println("[&gt;]</a>&nbsp;&nbsp;");
			out.print("<a href=\"product_list?cpage=" + pcnt + tmp + "\">");
			out.println("[&gt;&gt;]</a>");
		}
			out.println("</p>");
		} else {		// 검색된 상품 목록이 없을 경우
			out.println("</table>");
			out.println("<p class='nothing' align='center'>검색 결과가 없습니다.</p>");
		} %>
</div>
</body>
</html>
