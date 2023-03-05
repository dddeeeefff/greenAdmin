package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("/member_view")
public class MemberViewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberViewCtrl() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		AdminInfo loginInfo = (AdminInfo)session.getAttribute("loginInfo");
		
		if (loginInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		String miid = request.getParameter("miid");
		MemberViewSvc memberViewSvc = new MemberViewSvc();
		MemberInfo memberInfo = memberViewSvc.getMemberInfo(miid);
		ArrayList<SellInfo> sellInfoList = memberViewSvc.getSellInfoList(miid);
		ArrayList<BuyInfo> buyInfoList = memberViewSvc.getBuyInfoList(miid);
		ArrayList<MemberPoint> pointList = memberViewSvc.getPointList(miid);
		ArrayList<MemberQuestion> questionList = memberViewSvc.getQuestionList(miid);
		if (memberInfo == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		if (memberInfo.getMi_status().equals("b")) {
			MemberStatus memberStatus = memberViewSvc.getMemberStatus(miid);
			request.setAttribute("memberStatus", memberStatus);
		}

		String uri = request.getRequestURI().substring(12);
		request.setAttribute("uri", uri);
		request.setAttribute("memberInfo", memberInfo);
		request.setAttribute("sellInfoList", sellInfoList);
		request.setAttribute("buyInfoList", buyInfoList);
		request.setAttribute("pointList", pointList);
		request.setAttribute("questionList", questionList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("member/member_view.jsp");
		dispatcher.forward(request, response);
		
	}

}
