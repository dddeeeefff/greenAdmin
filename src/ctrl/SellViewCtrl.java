package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

@WebServlet("/sell_view")
public class SellViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public SellViewCtrl() {  super(); }


    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	
    	String siid = request.getParameter("siid");
    	
    	HttpSession session = request.getSession();
    	AdminInfo loginInfo = (AdminInfo)session.getAttribute("loginInfo");
    	if (loginInfo == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("login_form");
			dispatcher.forward(request, response);
		}
    	
    	SellViewSvc sellViewSvc = new SellViewSvc();
    	ArrayList<SellDetail> detailList = sellViewSvc.getDetailList(siid);
    	SellInfo sellInfo = sellViewSvc.getSellInfo(siid);

		String uri = request.getRequestURI().substring(12);
		request.setAttribute("uri", uri);
    	request.setAttribute("detailList", detailList);
    	request.setAttribute("sellInfo", sellInfo);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/order/sell_view.jsp");
		dispatcher.forward(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	

}
