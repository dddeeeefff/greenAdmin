package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/buy_form")
public class BuyFormCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BuyFormCtrl() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String biid = request.getParameter("biid");
		
		BuyFormSvc buyFormSvc = new BuyFormSvc(); 
		BuyInfo buyInfo = buyFormSvc.getBuyInfo(biid);
		
		if (buyInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}

		String uri = request.getRequestURI().substring(12);
		request.setAttribute("uri", uri);
		request.setAttribute("buyInfo", buyInfo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("order/buy_form.jsp");
		dispatcher.forward(request, response);
		
	}

}
