<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp" %>
<%
request.setCharacterEncoding("utf-8");

ArrayList<BuyInfo> buyList = (ArrayList<BuyInfo>)request.getAttribute("buyList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");

int bsize = pageInfo.getBsize(), cpage = pageInfo.getCpage(), psize = pageInfo.getPsize();
int rcnt = pageInfo.getRcnt(), pcnt = pageInfo.getPcnt(), spage = pageInfo.getSpage();
String schtype = pageInfo.getSchtype(), keyword = pageInfo.getKeyword();

String schargs = "", args = "";
if (schtype != null && !schtype.equals("") && keyword != null && !keyword.equals("")) {		// 검색 조건(schtype)과 검색어(keyword)가 있으면 검색관련 쿼리스트링 생성
	schargs = "&schtype=" + schtype + "&keyword=" + keyword;	
}
args = "&cpage=" + cpage + schargs;
%>
	<link rel="stylesheet" href="/greenAdmin/src/css/buy_list.css">
    <div class="page-contents">
        <div class="content-wrapper">
            <h3>구매 상품 관리</h3>
            <div class="frmSch">
                <form name="frmSch" method="get">
                    <select name="schtype">
                        <option value="">검색 항목</option> 
                        <option value="biid" <% if (schtype.equals("biid")) { %>selected="selected"<% } %>>등록 번호</option> 
                        <option value="uid" <% if (schtype.equals("uid")) { %>selected="selected"<% } %>>아이디</option> 
                        <option value="piname" <% if (schtype.equals("piname")) { %>selected="selected"<% } %>>모델 명</option> 
                    </select>
                    <input type="text" name="keyword" value="<%=keyword %>" />
                    <input type="submit" value="검색"  />
                </form>
            </div>
            <div class="list">
                <table>
                    <tr class="th">
                        <th class="biid">No.</th>
                        <th class="uid">아이디</th>
                        <th class="brand">브랜드</th>
                        <th class="model">모델 명</th>
                        <th class="predict">예상 가격</th>
                        <th class="confirmed">최종 가격</th>
                        <th class="status">진행 상태</th>
                        <th class="date">신청일</th>
                    </tr>
<%
String status = "판매 신청";
if (buyList.size() > 0) {
	for (int i = 0; i < buyList.size(); i++) {
		BuyInfo buyInfo = buyList.get(i);
		String brand = "";		// 판매 주문 번호로 구분되는 브랜드 이름
		if (buyInfo.getBi_id().charAt(0) == 'a')		brand = "애플";
		else											brand = "삼성";
		String predict = "-";		// 1차 검수 후 예상 가격
		if (buyInfo.getBi_predict() != 0)		predict = String.format("%,d", buyInfo.getBi_predict());
		String fixed = "-";			// 2차 검수 후 최종 가격
		if (buyInfo.getBi_pay() != 0)			fixed = String.format("%,d", buyInfo.getBi_pay());
		String bs = buyInfo.getBi_status();
		if (bs.equals("b"))		status = "판매 취소";
		else if (bs.equals("c"))		status = "승인 거절";
		else if (bs.equals("d"))		status = "1차 검수 완료";
		else if (bs.equals("e"))		status = "배송 대기";
		else if (bs.equals("f"))		status = "배송중";
		else if (bs.equals("g"))		status = "상품 도착";
		else if (bs.equals("h"))		status = "2차 검수 완료";
		else if (bs.equals("i"))		status = "대금 수령 선택";
		else if (bs.equals("j"))		status = "입금 대기";
		else if (bs.equals("k"))		status = "판매 완료";
%>
                    <tr class="td">
                        <td class="biid"><a href="buy_form?biid=<%=buyInfo.getBi_id() %>"><%=buyInfo.getBi_id() %></a></td>
                        <td class="uid"><%=buyInfo.getMi_id() %></td>
                        <td class="brand"><%=brand %></td>
                        <td class="model"><%=buyInfo.getPi_name() %></td>
                        <td class="predict"><%=predict %> 원</td>
                        <td class="confirmed"><%=fixed %> 원</td>
                        <td class="status"><%=status %></td>
                        <td class="date"><%=buyInfo.getBi_date() %></td>
                    </tr>
<%
	}
} else {
%>
				</table>
				<p>검색 결과가 없습니다.</p>
<% } %>
<% if (rcnt > 0) {
	out.println("</table>");
	out.println("<div class='pagination'>");
	String lnk = "buy_list?cpage=";
	pcnt = rcnt / psize;
	if (rcnt % psize > 0)		pcnt++;		// 전체 페이지 수(마지막 페이지 번호)
	
	if (cpage == 1) {
		out.println("<p class='first'>[처음]</p>");
		out.println("<p>[이전]</p>");
	} else {
		out.println("<a class='first' href='" + lnk + 1 + schargs + "'>[처음]</a>");
		out.println("<a href='" + lnk + (cpage - 1) + schargs + "'>[이전]</a>");
	}
	
	spage = (cpage - 1) / bsize * bsize + 1;		// 현재 블록에서의 시작 페이지 번호
	for (int i = 1, j = spage; i <= bsize && j <= pcnt; i++, j++) {
	// i : 블록에서 보여줄 페이지의 개수 만큼 루프를 돌릴 조건으로 사용되는 변수
	// j : 실제 출력할 페이지 번호로 전체 페이지 개수(마지막 페이지 번호)를 넘지 않게 사용해야 함
		if (cpage == j)		out.println("<p><strong>" + j + "</strong></p>");
		else 				out.println("<a href='" + lnk + j + schargs + "'>" + j + "</a>");
	}
	
	if (cpage == pcnt) {
		out.println("<p>[다음]</p>");
		out.println("<p>[마지막]</p>");
	} else {
		out.println("<a href='" + lnk + (cpage + 1) + schargs + "'>[다음]</a>");
		out.println("<a href='" + lnk + pcnt + schargs + "'>[마지막]</a>");
	}
	out.println("</div>");
}
%>
            </div>
        </div>
    </div>
</body>
</html>