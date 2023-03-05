package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/product_form_up")
public class ProductFormUpCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductFormUpCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
    	
    	HttpSession session = request.getSession();
    	AdminInfo loginInfo = (AdminInfo)session.getAttribute("loginInfo");
    	if (loginInfo == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("login_form");
			dispatcher.forward(request, response);
		}
    	
    	String piid = request.getParameter("piid");
    	String pidc = request.getParameter("pidc");
    	String piimg1 = request.getParameter("piimg1");
    	String piimg2 = request.getParameter("piimg2");
    	
    	ProductFormUpSvc productFormUpSvc = new ProductFormUpSvc();
    	int result = productFormUpSvc.getProductFormUp(piid, pidc, piimg1, piimg2);
    	
		if (result == 1) {	// 정상적으로 수정되었으면
    		response.sendRedirect("product_form?piid=" + piid);
		} else {	// 수정 실패
			response.setContentType("text/html; charset=utf-8"); 
	    	PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정에 실패했습니다.\\n다시 시도하세요.')");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

	}
}
