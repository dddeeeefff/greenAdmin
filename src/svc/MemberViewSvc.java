package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import java.util.*;
import dao.*;
import vo.*;

public class MemberViewSvc {

	public MemberInfo getMemberInfo(String miid) {
		MemberInfo memberInfo = null;
		Connection conn = getConnection();
		MemberProcDao memberProcDao = MemberProcDao.getInstance();
		memberProcDao.setConnection(conn);
		memberInfo = memberProcDao.getMemberInfo(miid);
		close(conn);
		
		return memberInfo;
	}

	public MemberStatus getMemberStatus(String miid) {
		MemberStatus memberStatus = null;
		Connection conn = getConnection();
		MemberProcDao memberProcDao = MemberProcDao.getInstance();
		memberProcDao.setConnection(conn);
		memberStatus = memberProcDao.getMemberStatus(miid);
		close(conn);
		
		return memberStatus;
	}

	public ArrayList<SellInfo> getSellInfoList(String miid) {
		ArrayList<SellInfo> sellInfoList = null;
		Connection conn = getConnection();
		MemberProcDao memberProcDao = MemberProcDao.getInstance();
		memberProcDao.setConnection(conn);
		sellInfoList = memberProcDao.getSellInfoList(miid);
		close(conn);
		
		return sellInfoList;
	}

	public ArrayList<BuyInfo> getBuyInfoList(String miid) {
		ArrayList<BuyInfo> buyInfoList = null;
		Connection conn = getConnection();
		MemberProcDao memberProcDao = MemberProcDao.getInstance();
		memberProcDao.setConnection(conn);
		buyInfoList = memberProcDao.getBuyInfoList(miid);
		close(conn);
		
		return buyInfoList;
	}

	public ArrayList<MemberPoint> getPointList(String miid) {
		ArrayList<MemberPoint> pointList = new ArrayList<MemberPoint>();
		Connection conn = getConnection();
		MemberProcDao memberProcDao = MemberProcDao.getInstance();
		memberProcDao.setConnection(conn);
		
		pointList = memberProcDao.getPointList(miid);
		close(conn);
		
		return pointList;
	}

	public ArrayList<MemberQuestion> getQuestionList(String miid) {
		ArrayList<MemberQuestion> questionList = new ArrayList<MemberQuestion>();
		Connection conn = getConnection();
		MemberProcDao memberProcDao = MemberProcDao.getInstance();
		memberProcDao.setConnection(conn);;
		
		questionList = memberProcDao.getQuestionList(miid);
		close(conn);
		
		return questionList;
	}


}
