package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/product_form")
public class ProductFormCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductFormCtrl() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
    	/*AdminInfo loginInfo = (AdminInfo)session.getAttribute("loginInfo");
    	if (loginInfo == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("login_form");
			dispatcher.forward(request, response);
		}*/
		
		String piid = request.getParameter("piid");
		ProductFormSvc productFormSvc = new ProductFormSvc();
		
		ProductInfo pi = productFormSvc.getProductInfo(piid);
		
		if (pi == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('상품 정보가 없습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		String uri = request.getRequestURI().substring(12);
		request.setAttribute("uri", uri);
		request.setAttribute("pi", pi);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/product/product_form.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
