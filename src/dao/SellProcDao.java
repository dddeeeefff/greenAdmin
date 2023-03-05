package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import vo.*;
import java.time.LocalDate;


public class SellProcDao {
	private static SellProcDao sellProcDao;
	private Connection conn;
	private SellProcDao() {}
	
	public static SellProcDao getInstance() {
		if (sellProcDao == null)	sellProcDao = new SellProcDao();
		
		return sellProcDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	//
	public ArrayList<SellInfo> getSellList(int cpage, int psize, String where){
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<SellInfo> sellList = new ArrayList<SellInfo>();
		SellInfo si = null;
		LocalDate today = LocalDate.now();	// yyyy-mm-dd
		String td = (today + "").substring(2).replace("-", "");
		if (!where.equals(""))		where = " and a." + where;
		
		try {
			String sql = "select a.si_id, a.mi_id, b.sd_mname, a.si_pay, a.si_status, left(a.si_date, 10) wdate from t_sell_info a, t_sell_detail b " 
				+ " where a.si_id = b.si_id and sd_idx = (select min(c.sd_idx) from t_sell_detail c where a.si_id = c.si_id) " + where 
				+ " order by a.si_date desc limit " + ((cpage - 1) * psize) + ", " + psize;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	
			while (rs.next()) {
				si = new SellInfo();
				String siid = rs.getString("si_id");
				si.setSi_id(siid);
				si.setMi_id(rs.getString("mi_id"));
				int cnt = getSellDetailCount(siid);
				String model = rs.getString("sd_mname");
				if (cnt > 0)		model = model + " 외 " + cnt + "개";
				si.setSd_mname(model);
				si.setSi_pay(rs.getInt("si_pay"));
				si.setSi_status(rs.getString("si_status"));
				si.setSi_date(rs.getString("wdate"));
				sellList.add(si);
			}
			
		} catch(Exception e) {
			System.out.println("SellProcDao 클래스의 getSellList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return sellList;
	}
	
	//
	public int getSellDetailCount(String siid) {
		Statement stmt = null;
		ResultSet rs = null;
		int cnt = 0;
		
		try {
			String sql = "select count(*) - 1 from t_sell_detail where si_id = '" + siid + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			cnt = rs.getInt(1);
			
		} catch (Exception e) {
			System.out.println("SellProcDao 클래스의 getSellDetailCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return cnt;
	}
	
	public ArrayList<SellInfo> getNonSellList(int cpage, int psize, String where){
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<SellInfo> sellList = new ArrayList<SellInfo>();
		SellInfo si = null;
		LocalDate today = LocalDate.now();	// yyyy-mm-dd
		String td = (today + "").substring(2).replace("-", "");
		if (!where.equals(""))		where = " and a." + where;
		
		try {
			String sql = "select a.si_id, a.mi_id, b.sd_mname, a.si_pay, a.si_status, left(a.si_date, 10) wdate from t_sell_info a, t_sell_detail b " 
				+ " where a.si_id = b.si_id and si_status = 'd' and datediff(now(), left(a.si_last, 10)) > 7 and sd_idx = (select min(c.sd_idx) from t_sell_detail c where a.si_id = c.si_id) " + where 
				+ " order by a.si_date desc limit " + ((cpage - 1) * psize) + ", " + psize;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	
			while (rs.next()) {
				si = new SellInfo();
				String siid = rs.getString("si_id");
				si.setSi_id(siid);
				si.setMi_id(rs.getString("mi_id"));
				int cnt = getSellDetailCount(siid);
				String model = rs.getString("sd_mname");
				if (cnt > 0)		model = model + " 외 " + cnt + "개";
				si.setSd_mname(model);
				si.setSi_pay(rs.getInt("si_pay"));
				si.setSi_status(rs.getString("si_status"));
				si.setSi_date(rs.getString("wdate"));
				sellList.add(si);
			}
			
		} catch(Exception e) {
			System.out.println("SellProcDao 클래스의 getSellList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return sellList;
	}
	
	public int getSellListCount(String where) {
		int rcnt = 0;
		Statement stmt = null;
		ResultSet rs = null;
		LocalDate today = LocalDate.now();	// yyyy-mm-dd
		String td = (today + "").substring(2).replace("-", "");
		if(!where.equals(""))		where = " where " + where;
		
		try {
			String sql = "select count(si_id) from t_sell_info" + where;
			//System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	rcnt = rs.getInt(1);
			
		}catch(Exception e){
			System.out.println("SellProcDao 클래스의 getSellListCount() 메소드 오류");
			e.printStackTrace();
		}finally {
			close(rs);	close(stmt);
		}
		return rcnt;
	}
	
	public int getNonSellListCount(String where) {
		int rcnt = 0;
		Statement stmt = null;
		ResultSet rs = null;
		LocalDate today = LocalDate.now();	// yyyy-mm-dd
		String td = (today + "").substring(2).replace("-", "");
		if(!where.equals(""))		where = " where " + where;
		
		try {
			String sql = "select count(si_id) from t_sell_info where si_status = 'd' and datediff(date(now()), left(si_last, 10)) > 7 " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	rcnt = rs.getInt(1);
			
		}catch(Exception e){
			System.out.println("SellProcDao 클래스의 getNonSellListCount() 메소드 오류");
			e.printStackTrace();
		}finally {
			close(rs);	close(stmt);
		}
		return rcnt;
	}
	
	
	public ArrayList<SellDetail> getDetailList(String siid){
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<SellDetail> detailList = new ArrayList<SellDetail>();
		SellDetail sd = null;
		
		try {
			String sql = "select a.si_id, a.sd_img, a.sd_price, a.sd_cnt, left(a.sd_cdate, 10) wdate, a.sd_mname, a.sd_oname " + 
					" from t_sell_detail a, t_sell_info b where a.si_id = b.si_id and a.si_id = '" + siid + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				sd = new SellDetail();
				sd.setSi_id(rs.getString("si_id"));
				sd.setSd_img(rs.getString("sd_img"));
				sd.setSd_price(rs.getInt("sd_price"));
				sd.setSd_cnt(rs.getInt("sd_cnt"));
				sd.setSd_cdate(rs.getString("wdate"));
				sd.setSd_mname(rs.getString("sd_mname"));
				sd.setSd_oname(rs.getString("sd_oname"));
				detailList.add(sd);
				
			}
			
		}catch(Exception e) {
			System.out.println("SellProcDao 클래스의 getDetailList() 메소드 오류");
			e.printStackTrace();
		}finally {
			close(rs);	close(stmt);
		}
		return detailList;
	}
	
	public SellInfo getSellInfo(String siid) {
		Statement stmt = null;
		ResultSet rs = null;
		SellInfo sellInfo = new SellInfo();
		
		try {
			String sql = "select si_id, mi_id, si_payment, si_invoice, si_status, si_upoint, si_pay from t_sell_info where si_id='" + siid + "'";
			//System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			sellInfo.setMi_id(rs.getString("mi_id"));
			sellInfo.setSi_id(rs.getString("si_id"));
			sellInfo.setSi_payment(rs.getString("si_payment"));
			sellInfo.setSi_invoice(rs.getString("si_invoice"));
			sellInfo.setSi_status(rs.getString("si_status"));
			sellInfo.setSi_pay(rs.getInt("si_pay"));
			sellInfo.setSi_upoint(rs.getInt("si_upoint"));
			
		}catch(Exception e) {
			System.out.println("SellProcDao 클래스의 getSellInfo() 메소드 오류");
			e.printStackTrace();
		}finally {
			close(rs);	close(stmt);
		}
		return sellInfo;
	}
	
	public int upStatus(SellInfo si) {
	// sell_view에서 구매상태 변경하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			stmt = conn.createStatement();
			String sql = "update t_sell_info set si_status = '" + si.getSi_status() + "' where si_id = '" + si.getSi_id() + "'";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			if(si.getSi_status().equals("e")) {
				 sql = "select a.mi_id, b.si_pay, b.si_spoint " + 
				 		" from t_member_info a, t_sell_info b " + 
				 		" where a.mi_id = b.mi_id and b.si_id = '" + si.getSi_id() + "'";
				 System.out.println(sql);
				 rs = stmt.executeQuery(sql);
				 if(rs.next()) {
					 Statement stmt2 = conn.createStatement();
					 // t_member_info 적립 포인트 증가
			         sql = "update t_member_info set mi_point = mi_point + " + rs.getInt("si_spoint") + " where mi_id = '" + rs.getString("mi_id") + "' ";
			         System.out.println(sql);
			         result += stmt2.executeUpdate(sql);		                  
			         
			         
			         // t_member_point 테이블의 포인트 적립 내역 추가 쿼리
			         sql = "insert into t_member_point(mi_id, mp_su, mp_point, mp_desc, mp_detail) " + 
			              " values('" + rs.getString("mi_id") + "', 's', '" + rs.getInt("si_spoint") + "' , '상품 구매 적립', '" + si.getSi_id() + "') ";
			         System.out.println(sql);
			         result += stmt2.executeUpdate(sql);
			         System.out.println(result);	
				 }
				 
			} else if(si.getSi_status().equals("f")) {
				sql = "select a.mi_id, a.si_spoint, a.si_upoint, b.sd_price, b.sd_cnt, c.po_idx, c.po_name " + 
					  " from t_sell_info a, t_sell_detail b, t_product_option c " +
					  " where a.si_id = b.si_id and b.pi_id = c.pi_id and b.sd_oname = c.po_name and b.si_id = '" + si.getSi_id() + "'";
				System.out.println(sql);
				rs = stmt.executeQuery(sql);
				 
				if(rs.next()) {
					
					Statement stmt2 = conn.createStatement();
					/*
					// 적립 포인트 환수
			        sql = "update t_member_info set mi_point = mi_point - '" + rs.getInt("si_spoint") + "' where mi_id = '" + rs.getString("mi_id") + "'";
			        System.out.println(sql);
			        result += stmt2.executeUpdate(sql);
					
			        // 사용한 포인트 환불 포인트 내역 테이블에 추가
					sql = "insert into t_member_point(mi_id, mp_su, mp_point, mp_desc, mp_detail) " + 
				           " values('" + rs.getString("mi_id") + "', 'u', '" + rs.getInt("si_spoint") + "' , '적립 포인트 환수', '" + si.getSi_id() + "') ";
					System.out.println(sql);
					result += stmt2.executeUpdate(sql);
					 */
					if(rs.getInt("si_upoint") > 0) {
						 // 환불 포인트 사용한 포인트  내역 테이블에 추가
						sql = "insert into t_member_point(mi_id, mp_su, mp_point, mp_desc, mp_detail) " + 
					           " values('" + rs.getString("mi_id") + "', 's', '" + si.getSi_upoint() + "' , '주문 취소 환불', '" + si.getSi_id() + "') ";
						System.out.println(sql);
						result += stmt2.executeUpdate(sql);
					}
					
					do {	// 구입한 상품 옵션의 갯수 채우기
						Statement stmt3 = conn.createStatement();
						sql = "update t_product_option set po_stock = po_stock + '" + rs.getInt("sd_cnt") + "' where po_idx = '" + rs.getInt("po_idx") + "'";
						System.out.println(sql);
				        result += stmt3.executeUpdate(sql);
				         
				        if(rs.getInt("si_upoint") > 0) {
				       	// 사용한 포인트 환불
					        sql = "update t_member_info set mi_point = mi_point + '" + rs.getInt("sd_price") / 100 * rs.getInt("sd_cnt") + "'";
					        System.out.println(sql);
					        result += stmt3.executeUpdate(sql);
					    }
					} while(rs.next());

					System.out.println(result);
				 }
			}
		}catch(Exception e){
			System.out.println("SellProcDao 클래스의 upStatus() 메소드 오류");
			e.printStackTrace();
		}finally {
			close(rs);		close(stmt);
		}
		return result;
	}
	
	public int upInvoice(SellInfo si) {
	// 송장 및 상태 변경
		Statement stmt = null;
		int result = 0;
		try {
			stmt = conn.createStatement();
			String sql = "update t_sell_info set si_status = 'c', si_invoice = '" + si.getSi_invoice() + "' where si_id = '" + si.getSi_id() + "'"; 
			//System.out.println(sql);
			result = stmt.executeUpdate(sql);
		}catch(Exception e){
			System.out.println("SellProcDao 클래스의 upInvoice() 메소드 오류");
			e.printStackTrace();
		}finally {
			close(stmt);
		}
		return result;
	}
}
