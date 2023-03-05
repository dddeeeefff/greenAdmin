package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import vo.*;

public class IndexDao {
// 메인 화면에 보여줄 각종 수치를 불러오는 클래스
	private static IndexDao indexDao;
	private Connection conn;
	private IndexDao() {}
	
	public static IndexDao getInstance() {
		
		if (indexDao == null)	indexDao = new IndexDao();
		
		return indexDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getSellSum() {
	// 올해 총 판매액을 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int sell_sum = 0;
		
		try {
			String sql = "select sum(si_pay) from t_sell_info where year(now()) = year(si_date) and si_status <> 'f'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				sell_sum = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("IndexDao 클래스의 getSellSum() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return sell_sum;
	}

	public int getBuySum() {
	// 올해 총 구매액을 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int buy_sum = 0;
		
		try {
			String sql = "select sum(bi_pay) from t_buy_info where year(now()) = year(bi_date)";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				buy_sum = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("IndexDao 클래스의 getBuySum() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return buy_sum;
	}

	public int getInMember() {
	// 정상 회원 수를 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int in_member = 0;
		
		try {
			String sql = "select count(*) from t_member_info where mi_status = 'a'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				in_member = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("IndexDao 클래스의 getInMember() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return in_member;
	}

	public int getOutMember() {
	// 탈퇴 회원 수를 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int out_member = 0;
		
		try {
			String sql = "select count(*) from t_member_info where mi_status = 'b'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				out_member = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("IndexDao 클래스의 getOutMember() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return out_member;
	}

	public int getAnswerNa() {
	// 답변 미등록 문의 수를 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int answer_na = 0;
		
		try {
			String sql = "select count(*) from t_bbs_member_question where bmq_status = 'a'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				answer_na = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("IndexDao 클래스의 getAnswerNa() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return answer_na;
	}

	public int getAnswerIn() {
	// 답변 완료 문의 수를 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int answer_in = 0;
		
		try {
			String sql = "select count(*) from t_bbs_member_question where bmq_status = 'b'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				answer_in = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("IndexDao 클래스의 getAnswerIn() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return answer_in;
	}

	public int getStockEnough() {
	// 여유 재고 상품 수를 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int stock_enough = 0;
		
		try {
			String sql = "select count(*) from t_product_option where po_isview = 'y' and po_stock >= 50";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				stock_enough = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("IndexDao 클래스의 getStockEnough() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return stock_enough;
	}

	public int getStockNa() {
	// 재고에 여유가 없는 상품 수를 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int stock_na = 0;
		
		try {
			String sql = "select count(*) from t_product_option where po_isview = 'y' and po_stock < 50";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				stock_na = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("IndexDao 클래스의 getStockNa() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return stock_na;
	}
}
