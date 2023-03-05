package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/product_form_inc")
public class ProductFormIncCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductFormIncCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String piid = request.getParameter("piid");
		String color = request.getParameter("color");
		String rank = request.getParameter("rank");
		String memory = request.getParameter("memory");
		
		ProductOption po = new ProductOption();
		po.setPi_id(piid);		po.setPo_color(color);
		po.setPo_rank(rank);	po.setPo_memory(memory);
		
		ProductFormIncSvc productFormIncSvc = new ProductFormIncSvc();
		int result = productFormIncSvc.getProductFormInc(po);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.close();
	}
}
