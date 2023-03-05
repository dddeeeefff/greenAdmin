package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/login")
public class LoginCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginCtrl() { super(); }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		
		LoginSvc loginSvc = new LoginSvc();
		AdminInfo loginInfo = loginSvc.getLoginInfo(uid, pwd);
		
		if (loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("정보가 일치하지 않습니다.\\n다시 시도하세요.");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("loginInfo", loginInfo);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("location.replace('index');");
		out.println("</script>");
		out.close();
	}

}
