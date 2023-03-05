<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp"%>
<%

request.setCharacterEncoding("utf-8");
ArrayList<MemberInfo> memberList = (ArrayList<MemberInfo>)request.getAttribute("memberList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
int bsize = pageInfo.getBsize(), cpage = pageInfo.getCpage();
int psize = pageInfo.getPsize(), pcnt = pageInfo.getPcnt();
int spage = pageInfo.getSpage(), rcnt = pageInfo.getRcnt();
/*
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
int cpage = pageInfo.getCpage(), psize = pageInfo.getPsize();
int rcnt = pageInfo.getRcnt(), spage = pageInfo.getSpage();
int bsize = pageInfo.getBsize(), pcnt = pageInfo.getPcnt();
*/


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
	display: flex;

}

#page-contents h2 {
	margin:30px 0;
	font-size: 2.3em;
	
}
.schForm {
	display: flex;
	flex-direction: column;
    align-items: center;
	margin-left:20px;
}


.schBox {
	padding:20px;
	border:1px solid #000;
	border-radius: 5px;
}
.schBtn {
	width:200px;
	height:30px;
	font-size:20px;
	margin:10px 0;
}

.schName {
	margin:10px;
}
.phone {
	width:50px;
}
.page {
	text-align: center;
}

</style>
<script>
/*
function makeSch() {
	// 검색 조건들로 sch의 값을 만듦 : n모델명,bB1,p100000~200000
		var frm = document.frm;
		var sch = "";
		
		// 모델명 검색어 조건
		var pdt = frm.pdt.value;
		if (pdt != "")	sch += "n" + pdt;
		
		// 브랜드 조건
		var arrBrd = document.frm3.brd;

		for (var i = 1 ; i < arrBrd.length ; i++) {
			if (arrBrd[i].checked) { // i 인덱스에 해당하는 체크박스가 선택되어 있다면
				if (sch != "")	sch += ",";
				sch += "b" + arrBrd[i].value;
			}
		}
		
		// 가격대 조건
		var sp = document.frm3.sp.value, ep = document.frm3.ep.value;
		if (sp != "" || ep != "") {	// 가격대 중 하나라도 값이 있으면
			if (sch != "")	sch += ",";
			sch += "p" + sp + "~" + ep;
		}
		
		if (pdt == "" && sp == "" && ep == "" && arrBrd.value == "") {
			alert("검색조건을 선택하세요.");	return;
		}
		
		document.frm1.sch.value = sch;
		document.frm1.submit();
	}
*/
</script>
<div id="page-contents">
	<div>
		<h2>회원 관리</h2>
		<form name="frm1">
			<input type="hidden" name="o" value="" />
			<input type="hidden" name="v" value="" />
			<input type="hidden" name="sch" value="" />			
		</form>
		
		<table width="1000" align="center" border="1" cellpadding="3" cellspacing="0">
			<tr>
				<th width="20%">아이디</th>
				<th width="15%">이름</th>
				<th width="15%">생년월일</th>
				<th width="10%">성별</th>
				<th width="*">연락처</th>
				<th width="15%">상태</th>
			</tr>
			<input type="hidden" name="piid" value="" />
<%
	if (memberList.size() > 0) {
	for (int i = 0; i < memberList.size(); i++) {
		MemberInfo memberInfo = memberList.get(i);
		
		
		String status = memberInfo.getMi_status();
		switch (status) {
	case "a":
		status = "정상";
		break;
	case "b":
		status = "탈퇴";
		break;
		}
%>

			<tr align="center">
				<td><a href="member_view?miid=<%=memberInfo.getMi_id() %>"><%=memberInfo.getMi_id() %></a></td>
				<td><%=memberInfo.getMi_name() %></td>
				<td><%=memberInfo.getMi_birth() %></td>
				<td><%=memberInfo.getMi_gender() %></td>
				<td><%=memberInfo.getMi_phone() %></td>
				<td><%=status %></td>
			</tr>
<%}
	}%>
		</table>
<table width="100%" cellpadding="5" class="page">
<tr>
<td width="80%">
<%
if (rcnt > 0) {	// 게시글이 있으면 - 페이지 영역을 보여줌
	String lnk = "member_list?cpage=";
	pcnt = rcnt / psize;
	if (rcnt % psize > 0)	pcnt++;	// 전체 페이지 수(마지막 페이지 번호) 
	
	if (cpage == 1) {
		out.println("[처음]&nbsp;&nbsp;&nbsp;[이전]&nbsp;&nbsp;");
	} else {
		out.println("<a href='" + lnk + 1 + "'>[처음]</a>&nbsp;&nbsp;&nbsp;");
		out.println("<a href='" + lnk + (cpage - 1) + "'>[이전]</a>&nbsp;&nbsp;");
	}
	
	spage = (cpage - 1) / bsize * bsize + 1;	// 현재 블록에서의 시작 페이지 번호
	for (int i = 1, j = spage ; i <= bsize && j <= pcnt ; i++, j++) {
	// i : 블록에서 보여줄 페이지의 개수만큼 루프를 돌릴 조건으로 사용되는 변수
	// j : 실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지 번호)를 넘지 않게 사용해야 함
		if (cpage == j) {
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
		} else {
			out.print("&nbsp;<a href='" + lnk + j +"'>");
			out.println(j + "</a>&nbsp;");
		}
	}
	
	if (cpage == pcnt) {
		out.println("&nbsp;&nbsp;[다음]&nbsp;&nbsp;&nbsp;[마지막]");
	} else {
		out.println("&nbsp;&nbsp;<a href='" + lnk + (cpage + 1) + "'>[다음]</a>");
		out.println("&nbsp;&nbsp;&nbsp;<a href='" + lnk + pcnt + "'>[마지막]</a>");
	}
}
%>
</td>
</tr>
</table>
		
		
	</div>
	<div>
		<form class="schForm" name="frm">
			<div class="schBox" id="chkBox">
				<p class="schName">아이디</p>
				<input type="text" name="id" placeholder="아이디" />
				<hr />
				<p class="schName">이름</p>
				<input type="text" name="name" placeholder="name" />
				<hr />
				<p class="schName">성별</p>
				<input type="checkbox" name="gender" value="남"/>남&nbsp;&nbsp;&nbsp;
				<input type="checkbox" name="gender" value="여"/>여
				<hr />
				<p class="schName">전화번호</p>
				<input type="text" name="p1" class="phone" placeholder="010" onkeyup="onlyNum(this);" value="" /> - 
				<input type="text" name="p2" class="phone" placeholder="0000" onkeyup="onlyNum(this);" value="" /> - 
				<input type="text" name="p3" class="phone" placeholder="0000" onkeyup="onlyNum(this);" value="" />
				<hr />
				<p class="schName">상태</p>
				<input type="checkbox" name="status" value="정상"/>정상&nbsp;&nbsp;
				<input type="checkbox" name="status" value="탈퇴"/>탈퇴
			</div>
			<p></p>
			<input type="button" class="schBtn" value="적용하기" onclick="makeSch();" />
		</form>
	</div>
</div>
</body>
</html>
