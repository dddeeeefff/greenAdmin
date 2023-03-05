package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import vo.*;

public class BuyProcDao {
// 구매 상품 관련 쿼리 작업(목록, 수정)들을 모두 처리하는 클래스
	private static BuyProcDao buyProcDao;
	private Connection conn;
	private BuyProcDao() {}
	
	public static BuyProcDao getInstance() {
		
		if (buyProcDao == null)		buyProcDao = new BuyProcDao();
		
		return buyProcDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getBuyListCount(String where) {
	// 구매 상품의 검색된 결과의 레코드(게시글) 개수를 리턴하는 메소드
		int rcnt = 0;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select count(*) from t_buy_info a, t_product_info b where a.pi_id = b.pi_id " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())		rcnt = rs.getInt(1);
			
		} catch (Exception e) {
			System.out.println("BuyProcDao 클래스의 getBuyListCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return rcnt;
	}
	
	public ArrayList<BuyInfo> getBuyList(String where, int cpage, int psize) {
	// 구매 상품 정보에서 검색된 결과의 레코드(구매 상품) 목록을 ArrayList로 리턴하는 메소드
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<BuyInfo> buyList = new ArrayList<BuyInfo>();		// 검색된 게시글들을 저장할 ArrayList<BuyInfo>
			BuyInfo bi = null;		// buyList에 저장할 하나의 게시글 정보를 담을 인스턴스
		
			try {
				String sql = "select bi_id, mi_id, pi_name, bi_predict, bi_pay, bi_status, left(bi_date, 10) wdate " 
						+ " from t_buy_info a, t_product_info b where a.pi_id = b.pi_id " + where 
						+ " order by bi_date desc limit " + ((cpage - 1) * psize) + ", " + psize;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				while (rs.next()) {
					bi = new BuyInfo();
					bi.setBi_id(rs.getString("bi_id"));
					bi.setMi_id(rs.getString("mi_id"));
					bi.setPi_name(rs.getString("pi_name"));
					bi.setBi_predict(rs.getInt("bi_predict"));
					bi.setBi_pay(rs.getInt("bi_pay"));
					bi.setBi_status(rs.getString("bi_status"));
					bi.setBi_date(rs.getString("wdate"));
					buyList.add(bi);
				}
				
			} catch (Exception e) {
				System.out.println("BuyProcDao 클래스의 getBuyList() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(rs);		close(stmt);
			}

			return buyList;
	}
	
	public BuyInfo getBuyInfo(String biid) {
		Statement stmt = null;
		ResultSet rs = null;
		BuyInfo buyInfo = null;
		
		try { 
			String sql = "select a.*, b.pi_name from t_buy_info a, t_product_info b where a.bi_id = '" 
				+ biid + "' and a.pi_id = b.pi_id";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				buyInfo = new BuyInfo();
				buyInfo.setBi_id(rs.getString("bi_id"));
				buyInfo.setMi_id(rs.getString("mi_id"));
				buyInfo.setPi_id(rs.getString("pi_id"));
				buyInfo.setPi_name(rs.getString("pi_name"));
				buyInfo.setBi_color(rs.getString("bi_color"));
				buyInfo.setBi_memory(rs.getString("bi_memory"));
				buyInfo.setBi_rank(rs.getString("bi_rank"));
				buyInfo.setBi_option(rs.getInt("bi_option"));
				buyInfo.setBi_img1(rs.getString("bi_img1"));
				buyInfo.setBi_img2(rs.getString("bi_img2"));
				buyInfo.setBi_img3(rs.getString("bi_img3"));
				buyInfo.setBi_img4(rs.getString("bi_img4"));
				buyInfo.setBi_desc(rs.getString("bi_desc"));
				buyInfo.setBi_predict(rs.getInt("bi_predict"));
				buyInfo.setBi_pay(rs.getInt("bi_pay"));
				buyInfo.setBi_invoice(rs.getString("bi_invoice"));
				buyInfo.setBi_bank(rs.getString("bi_bank"));
				buyInfo.setBi_account(rs.getString("bi_account"));
				buyInfo.setBi_status(rs.getString("bi_status"));
				buyInfo.setBi_date(rs.getString("bi_date"));
				
			} 
			
		} catch (Exception e) {
			System.out.println("BuyProcDao 클래스의 getBuyInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return buyInfo;
	}
	
	public int buyProcUp(String biid, String birank, String bistatus, String where) {
		Statement stmt = null;
		int result = 0;
		
		try {
			String sql = "update t_buy_info set bi_status = '" + bistatus + "'";
			if (bistatus.equals("c") || bistatus.equals("g") || bistatus.equals("k")) {
				sql += where;
			} else if (bistatus.equals("d")) {
				int optionIdx = getOptionIdx(biid, birank);
				int predict = getPredict(biid, optionIdx);
				if (optionIdx > 0 && predict > 0) {
					sql += ", bi_rank = '" + birank + "', bi_option = " + optionIdx + ", bi_predict = " + predict + where;	
				}
			} else if (bistatus.equals("h")) {
				int optionIdx = getOptionIdx(biid, birank);
				int pay = getPay(biid, optionIdx);
				if (optionIdx > 0 && pay > 0) {
					sql += ", bi_rank = '" + birank + "', bi_option = " + optionIdx + ", bi_pay = " + pay + where;
				}
			}
			
			if (sql.indexOf("where") > 0) {
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
				
				if (bistatus.equals("k") && result == 1) {
					int optionIdx = getOptionIdx(biid, birank);
					sql = "update t_product_option set po_stock = po_stock + 1 where po_idx = " + optionIdx;
					result = stmt.executeUpdate(sql);
					
				}
			}
			
		} catch (Exception e) {
			System.out.println("BuyProcDao 클래스의 buyProcUp() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int getOptionIdx(String biid, String birank) {
		Statement stmt = null;
		ResultSet rs = null;
		int optionIdx = 0;
		
		try {
			String sql = "select po_idx from t_product_option where pi_id = (select pi_id from t_buy_info where bi_id = '" 
				+ biid + "') and po_color = (select bi_color from t_buy_info where bi_id = '" + biid + "') and " 
				+ " po_memory = (select bi_memory from t_buy_info where bi_id = '" + biid + "') and po_rank = '" 
				+ birank + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			optionIdx = rs.getInt("po_idx");
			
		} catch (Exception e) {
			System.out.println("BuyProcDao 클래스의 getOptionIdx() 메소드 오류");
		} finally {
			close(rs);		close(stmt);
		}
		
		return optionIdx;
	}
	
	public int getPredict(String biid, int optionIdx) {
		Statement stmt = null;
		ResultSet rs = null;
		int predict = 0;
		
		try {
			String sql = "select a.pi_max, b.po_dec from t_product_info a, t_product_option b where a.pi_id = b.pi_id " + 
				" and b.po_idx = " + optionIdx;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			int max = rs.getInt("pi_max");
			int dec = rs.getInt("po_dec");
			predict = max - (max * dec / 100);
			
		} catch (Exception e) {
			System.out.println("BuyProcDao 클래스의 getPredict() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return predict;
	}
	

	public int getPay(String biid, int optionIdx) {
		Statement stmt = null;
		ResultSet rs = null;
		int pay = 0;
		
		try {
			String sql = "select a.pi_max, b.po_dec from t_product_info a, t_product_option b where a.pi_id = b.pi_id " + 
				" and b.po_idx = " + optionIdx;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			int max = rs.getInt("pi_max");
			int dec = rs.getInt("po_dec");
			pay = max - (max * dec / 100);
			
		} catch (Exception e) {
			System.out.println("BuyProcDao 클래스의 getPay() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return pay;
	}
	
}
