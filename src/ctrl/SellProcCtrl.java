package ctrl;

import java.io.*;
import java.net.URLEncoder;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

@WebServlet("/sell_proc")
public class SellProcCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public SellProcCtrl() { super();  }

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String invoice = request.getParameter("invoice");
		System.out.println(invoice);

		String siid = request.getParameter("si_id");
		

		SellInfo si = new SellInfo();
		si.setSi_invoice(invoice);
		si.setSi_id(siid);

		
		
		SellProcSvc sellProcSvc = new SellProcSvc();
		
		int result = sellProcSvc.upInvoice(si);
		
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
	
		
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
