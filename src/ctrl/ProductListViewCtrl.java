package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/product_list_view")
public class ProductListViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductListViewCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String piid = request.getParameter("piid");
		String opt = request.getParameter("opt");
		
		ProductListViewSvc productListViewSvc = new ProductListViewSvc();
		
		ProductInfo pi = new ProductInfo();
		pi.setPi_id(piid);
		
		int result = productListViewSvc.getView(piid, opt);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		// System.out.println("result : " + result);
		out.close();
	}
}
