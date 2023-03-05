package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;


@WebServlet("/product_stock_proc")
public class ProductStockProcCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductStockProcCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int poidx = Integer.parseInt(request.getParameter("poidx"));
		int cnt = Integer.parseInt(request.getParameter("cnt"));

		ProductOption po = new ProductOption();
		po.setPo_idx(poidx);	po.setPo_stock(cnt);
		ProductStockProcSvc productStockProcSvc = new ProductStockProcSvc();
		int result = productStockProcSvc.stockUpdate(po);

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
	}

}
