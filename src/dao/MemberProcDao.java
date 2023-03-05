package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import vo.*;

public class MemberProcDao {
	private static MemberProcDao memberProcDao;
	private Connection conn;
	private MemberProcDao() {}
	
	public static MemberProcDao getInstance() {
		if (memberProcDao == null)	memberProcDao = new MemberProcDao();
		
		return memberProcDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public ArrayList<MemberInfo> getMemberList(int cpage, int psize) {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();
		MemberInfo memberInfo = null;
		
		try {
			String sql = "select * from t_member_info " +
			" order by mi_joindate asc " +
			" limit " + ((cpage - 1) * psize) + ", " + psize;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				memberInfo = new MemberInfo();
				memberInfo.setMi_id(rs.getString("mi_id"));
				memberInfo.setMi_name(rs.getString("mi_name"));
				memberInfo.setMi_birth(rs.getString("mi_birth"));
				memberInfo.setMi_gender(rs.getString("mi_gender"));
				memberInfo.setMi_phone(rs.getString("mi_phone"));
				memberInfo.setMi_status(rs.getString("mi_status"));
				memberList.add(memberInfo);
			}
		} catch(Exception e) {
			System.out.println("MemberProcDao 클래스의 getMemberList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return memberList;
	}

	public int getMemberListCount() {
		int result = 0;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select count(*) from t_member_info";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			result = rs.getInt(1);
			
		} catch (Exception e) {
			System.out.println("MemberProcDao 클래스의 getMemberListCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);	close(rs);
		}
		
		return result;
	}

	public MemberInfo getMemberInfo(String miid) {
		Statement stmt = null;
		ResultSet rs = null;
		MemberInfo memberInfo = null;
		
		try {
			
			String sql = "select * from t_member_info where mi_id = '" + miid + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			memberInfo = new MemberInfo();
			memberInfo.setMi_id(miid);
			memberInfo.setMi_name(rs.getString("mi_name"));
			memberInfo.setMi_birth(rs.getString("mi_birth"));
			memberInfo.setMi_gender(rs.getString("mi_gender"));
			memberInfo.setMi_phone(rs.getString("mi_phone"));
			memberInfo.setMi_email(rs.getString("mi_email"));
			memberInfo.setMi_point(rs.getInt("mi_point"));
			memberInfo.setMi_status(rs.getString("mi_status"));
			memberInfo.setMi_addr1(rs.getString("mi_addr1"));
			memberInfo.setMi_addr2(rs.getString("mi_addr2"));
			
		} catch (Exception e) {
			System.out.println("MemberProcDao 클래스의 getMemberInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);	close(rs);
		}
		
		return memberInfo;
	}

	public MemberStatus getMemberStatus(String miid) {
		Statement stmt = null;
		ResultSet rs = null;
		MemberStatus memberStatus = null;
		
		try {
			
			String sql = "select * from t_member_status where mi_id = '" + miid + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			memberStatus = new MemberStatus();
			memberStatus.setMi_id(miid);
			memberStatus.setMs_status(rs.getString("ms_status"));
			memberStatus.setMs_reason(rs.getString("ms_reason"));
			memberStatus.setMs_self(rs.getInt("ms_self"));
			memberStatus.setMs_date(rs.getString("ms_date"));
			
		} catch (Exception e) {
			System.out.println("MemberProcDao 클래스의 getMemberStatus() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);	close(rs);
		}
		
		return memberStatus;
	}
	
	
	
	
	
	public int getBuyModelCount(String oiid) {
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			String sql = "select count(*) from t_sell_detail where si_id = '" + oiid + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt(1) - 1;
			
		} catch (Exception e) {
			System.out.println("OrderProcDao 클래스의 getBuyModelCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return count;
	}

	public ArrayList<SellInfo> getSellInfoList(String miid) {
		ArrayList<SellInfo> sellInfoList = new ArrayList<SellInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		SellInfo sellInfo = null;
		
		try {
			String sql = "select a.si_id, b.sd_mname, a.si_pay, a.si_status, a.si_date " +
					" from t_sell_info a, t_sell_detail b where a.mi_id = '" + miid + "' and a.si_id=b.si_id and " + 
					" sd_idx = (select min(c.sd_idx) from t_sell_detail c where a.si_id = c.si_id)";
				System.out.println(sql);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					sellInfo = new SellInfo();
					String siid = rs.getString("si_id");
					sellInfo.setSi_id(siid);
					int cnt = getBuyModelCount(siid);
					String mname = rs.getString("sd_mname");
					if (cnt > 0)		mname = mname + " 외 " + cnt + "개";
					sellInfo.setSd_mname(mname);
					sellInfo.setSi_pay(rs.getInt("si_pay"));
					sellInfo.setSi_status(rs.getString("si_status"));
					sellInfo.setSi_date(rs.getString("si_date"));
					sellInfoList.add(sellInfo);
				}
		} catch (Exception e) {
			System.out.println("MemberProcDao 클래스의 getSellInfoList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);	close(rs);
		}
		
		return sellInfoList;
	}

	public ArrayList<BuyInfo> getBuyInfoList(String miid) {
		ArrayList<BuyInfo> buyList = new ArrayList<BuyInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		BuyInfo buyInfo = null;
		
		try {
			String sql = "select a.bi_id, b.pi_name, a.bi_pay, a.bi_status, a.bi_date " +
				" from t_buy_info a, t_product_info b where a.pi_id = b.pi_id " +	
				" order by a.bi_date desc ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				buyInfo = new BuyInfo();
				buyInfo.setBi_id(rs.getString("bi_id"));
				buyInfo.setPi_name(rs.getString("pi_name"));
				buyInfo.setBi_pay(rs.getInt("bi_pay"));
				buyInfo.setBi_status(rs.getString("bi_status"));
				buyInfo.setBi_date(rs.getString("bi_date"));
				buyList.add(buyInfo);
			}

		} catch (Exception e) {
			System.out.println("MemberProcDao 클래스의 getBuyInfoList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);	close(rs);
		}
		
		return buyList;
	}

	public ArrayList<MemberPoint> getPointList(String miid) {
		ArrayList<MemberPoint> pointList = new ArrayList<MemberPoint>();
		Statement stmt = null;
		ResultSet rs = null;
		MemberPoint memberPoint = null;
		
		try {
			String sql = "select * from t_member_point where mi_id = '" + miid + "' order by mp_date asc;";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				memberPoint = new MemberPoint();
				memberPoint.setMi_id(miid);
				memberPoint.setMp_su(rs.getString("mp_su"));
				memberPoint.setMp_desc(rs.getString("mp_desc"));
				memberPoint.setMp_point(rs.getInt("mp_point"));
				memberPoint.setMp_detail(rs.getString("mp_detail"));
				pointList.add(memberPoint);
			}

		} catch (Exception e) {
			System.out.println("MemberProcDao 클래스의 getPointList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);	close(rs);
		}
		
		return pointList;
	}

	public ArrayList<MemberQuestion> getQuestionList(String miid) {
		ArrayList<MemberQuestion> questionList = new ArrayList<MemberQuestion>();
		Statement stmt = null;
		ResultSet rs = null;
		MemberQuestion memberQuestion = null;
		
		try {
			String sql = "select * from t_bbs_member_question " +
				" where mi_id = '" + miid + "' order by bmq_date desc";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				memberQuestion = new MemberQuestion();
				memberQuestion.setBmq_idx(rs.getInt("bmq_idx"));
				memberQuestion.setMi_id(rs.getString("mi_id"));
				memberQuestion.setBmq_title(rs.getString("bmq_title"));
				memberQuestion.setBmq_img(rs.getString("bmq_img"));
				memberQuestion.setBmq_status(rs.getString("bmq_status"));
				memberQuestion.setBmq_content(rs.getString("bmq_content"));
				memberQuestion.setBmq_date(rs.getString("bmq_date"));
				questionList.add(memberQuestion);
			}

		} catch (Exception e) {
			System.out.println("MemberProcDao 클래스의 getQuestionList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);	close(rs);
		}
		
		return questionList;
	}
	
}
