package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/index")
public class IndexCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public IndexCtrl() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		AdminInfo loginInfo = (AdminInfo)session.getAttribute("loginInfo");
		
		if (loginInfo == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("login_form");
			dispatcher.forward(request, response);
		}
		
		IndexSvc indexSvc = new IndexSvc();
		StatisticInfo statisticInfo = new StatisticInfo();
		statisticInfo.setSell_sum(indexSvc.getSellSum());
		statisticInfo.setBuy_sum(indexSvc.getBuySum());
		statisticInfo.setIn_member(indexSvc.getInMember());
		statisticInfo.setOut_member(indexSvc.getOutMember());
		statisticInfo.setAnswer_na(indexSvc.getAnswerNa());
		statisticInfo.setAnswer_in(indexSvc.getAnswerIn());
		statisticInfo.setStock_enough(indexSvc.getStockEnough());
		statisticInfo.setStock_na(indexSvc.getStockNa());
		request.setAttribute("statisticInfo", statisticInfo);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

}
