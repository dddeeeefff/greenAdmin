package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/product_list")
public class ProductListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductListCtrl() { super(); }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	
    	HttpSession session = request.getSession();
    	AdminInfo loginInfo = (AdminInfo)session.getAttribute("loginInfo");
    	if (loginInfo == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("login_form");
			dispatcher.forward(request, response);
		}
    	
    	int cpage = 1, psize = 10, bsize = 10, rcnt = 0, pcnt = 0, spage = 0;
    	// 현재 페이지 번호, 페이지 크기, 블록 크기, 레코드(상품) 개수, 전체 페이지 개수, 시작 페이지 번호 등을 저장할 변수들 선언
    	if (request.getParameter("cpage") != null) 
			cpage = Integer.parseInt(request.getParameter("cpage"));
    	
    	// 검색 조건 where절 생성
		String where = "";
		String sch = request.getParameter("sch");	// 검색조건(모델명)
		
		if (sch != null && !sch.equals("")) {
		// 검색조건 : &sch=n모델명
			char c = sch.charAt(0);
			if (c == 'n') {	// 상품명 검색일 경우(n검색어)
				where += " and a.pi_name like '%" + sch.substring(1) + "%' ";
			}
		}
		
		ProductListSvc productListSvc = new ProductListSvc();
		
		rcnt = productListSvc.getProductCount(where);
		// 검색된 상품의 총 개수로 전체 페이지수를 구할 때 사용
	
		ArrayList<ProductInfo> productList = productListSvc.getProductList(cpage, psize, where);
		// 검색된 상품들 중 현재 페이지에서 보여줄 상품 목록을 받아옴
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;	// 전체 페이지 수(마지막 페이지 번호)
		spage = (cpage - 1) / psize * psize + 1;	// 블록 시작 페이지 번호
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setBsize(bsize);	pageInfo.setCpage(cpage);
		pageInfo.setPcnt(pcnt);		pageInfo.setPsize(psize);
		pageInfo.setRcnt(rcnt);		pageInfo.setSpage(spage);
		pageInfo.setSch(sch);
		// 페이징 관련 정보들과 검색 및 정렬 정보들을 PageInfo 인스턴스에 저장
		

		String uri = request.getRequestURI().substring(12);
		request.setAttribute("uri", uri);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("productList", productList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/product/product_list.jsp");
		dispatcher.forward(request, response);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
