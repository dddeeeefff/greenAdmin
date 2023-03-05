package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/buy_proc")
public class BuyProcCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public BuyProcCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String biid = request.getParameter("biid");
		String birank = request.getParameter("birank");
		String bistatus = request.getParameter("bistatus");		// c, d, g, h, k
		String where = " where bi_id = '" + biid + "'";

		BuyProcSvc buyProcSvc = new BuyProcSvc();
		
		int result = buyProcSvc.buyProcUp(biid, birank, bistatus, where);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.close();
		
	}

}
