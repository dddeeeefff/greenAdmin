package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.util.*;
import java.net.*;
import svc.*;
import vo.*;

@WebServlet("/buy_list")
public class BuyListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public BuyListCtrl() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;
		// 현재 페이지 번호, 페이지 크기, 블록 크기, 레코드(게시글) 개수, 페이지 개수, 시작 페이지 등을 저장할 변수들
		
		if (request.getParameter("cpage") != null)		cpage = Integer.parseInt(request.getParameter("cpage")); 
		// cpage 값이 있으면 받아서 int 형으로 형변환 시킴 
		
		String schtype = request.getParameter("schtype");		// 검색 조건(회원 아이디, 주문 번호, 모델명)
		String keyword = request.getParameter("keyword");		// 검색어
		String where = "";		// 검색 조건이 있을 경우 where 절을 저장할 변수
		
		if (schtype == null || keyword == null) {
			schtype = "";		keyword = "";
		} else if (!schtype.equals("") && !keyword.equals("")) {
		// 검색 조건과 검색어가 모두 있을 경우
			URLEncoder.encode(keyword, "UTF-8");
			// 쿼리 스트링으로 주고 받는 검색어가 한글 일 경우 브라우저에 따라 문제가 발생할 수 있으므로 유니코드로 변환
			if (schtype.equals("biid")) {		// 검색 조건이 '주문 번호'일 경우
				where = " and bi_id like '%" + keyword + "%'";
			} else if (schtype.equals("uid")) {		// 검색 조건이 '회원 아이디'일 경우
				where = " and mi_id like '%" + keyword + "%'";
			} else if (schtype.equals("piname")) {
				where = " and b.pi_name like '%" + keyword + "%'";
			}
			
		}
		
		BuyListSvc buyListSvc = new BuyListSvc();
		rcnt = buyListSvc.getBuyListCount(where);
		
		ArrayList<BuyInfo> buyList = buyListSvc.getBuyList(where, cpage, psize);
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCpage(cpage);
		pageInfo.setPsize(psize);
		pageInfo.setBsize(bsize);
		pageInfo.setRcnt(rcnt);
		pageInfo.setPcnt(pcnt);
		pageInfo.setSpage(spage);
		pageInfo.setSchtype(schtype);
		pageInfo.setKeyword(keyword);
		// 페이징과 검색에 필요한 정보들을 PageInfo형 인스턴스에 저장
		
		String uri = request.getRequestURI().substring(12);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("buyList", buyList);
		request.setAttribute("uri", uri);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("order/buy_list.jsp");
		dispatcher.forward(request, response);
		
	}

}
