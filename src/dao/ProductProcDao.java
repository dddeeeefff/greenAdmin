package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import vo.*;

public class ProductProcDao {
// 상품 관련된 쿼리 작업(목록, 상세보기)들을 모두 처리하는 클래스
	private static ProductProcDao productProcDao;
	private Connection conn;
	private ProductProcDao() {}
	
	public static ProductProcDao getInstance() {
		if (productProcDao == null)	productProcDao = new ProductProcDao();
		
		return productProcDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public ProductInfo getProductInfo(String piid) {
	// 사용자가 선택한 상품의 정보를 ProductInfo형 인스턴스로 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		ProductInfo pi = null;
		
		try { 
			String sql = "select a.pi_id, b.pb_name, c.ps_name, a.pi_name, a.pi_img1, a.pi_img2, a.pi_min, a.pi_dc " + 
					" from t_product_info a, t_product_brand b, t_product_series c " + 
					" where a.pb_id = b.pb_id and a.ps_id = c.ps_id and a.pi_id = '" + piid + "' ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				pi = new ProductInfo();	// 상품정보를 저장할 인스턴스 생성
				pi.setPi_id(rs.getString("pi_id"));
				pi.setPb_name(rs.getString("pb_name"));
				pi.setPs_name(rs.getString("ps_name"));
				pi.setPi_name(rs.getString("pi_name"));
				pi.setPi_img1(rs.getString("pi_img1"));
				pi.setPi_img2(rs.getString("pi_img2"));
				pi.setPi_min(rs.getInt("pi_min"));
				pi.setPi_dc(rs.getInt("pi_dc"));
			}
			
		} catch(Exception e) {
			System.out.println("ProductProcDao 클래스의 getProductInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return pi;
	}
	
	public int getProductCount(String where) {
	// 검색 되는 상품의 개수(select 쿼리 실행결과의 레코드 개수)를 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int rcnt = 0;
		
		try { 
			String sql = "select count(*) from t_product_info a " + 
			" where a.pi_isview = 'y' " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	rcnt = rs.getInt(1);
			
		} catch(Exception e) {
			System.out.println("ProductDao 클래스의 getProductCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return rcnt;
	}
	
	public int getStock(String piid) {
	// 각각의 제품의 재고량을 계산하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			String sql = "select sum(po_stock) stock from t_product_option where pi_id = '" + piid + "' ";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	result = rs.getInt(1);
			
		} catch(Exception e) {
			System.out.println("ProductProcDao 클래스의 getStock() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}
	
	public ArrayList<ProductInfo> getProductList(int cpage, int psize, String where) {
	// 검색되는 상품 목록은 지정한 페이지에 맞춰 ArrayList<ProductInfo>형으로 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductInfo> productList = new ArrayList<ProductInfo>();
		ProductInfo pi = null;
		
		try {
			String sql = "select a.pi_id, b.pb_name, a.pi_name, sum(c.po_stock) stock, a.pi_isview from t_product_info a, t_product_brand b, t_product_option c " +  
					" where a.pb_id = b.pb_id and a.pi_id = c.pi_id " + where + " group by a.pi_id order by a.pi_date limit " + ((cpage - 1) * psize) + ", " + psize;
			// System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				pi = new ProductInfo();
				pi.setPi_id(rs.getString("pi_id"));
				pi.setPi_name(rs.getString("pi_name"));
				pi.setPb_name(rs.getString("pb_name"));
				pi.setStock(rs.getInt("stock"));
				pi.setPi_isview(rs.getString("pi_isview"));
				productList.add(pi);
			}
			
		} catch(Exception e) {
			System.out.println("ProductProcDao 클래스의 getProductList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return productList;
	}
	
	public int getView(String piid, String opt) {
	// 게시 or 미게시 여부를 결정하는 메소드
		Statement stmt = null;
		int result = 0;
		
		try { 
			String sql = "update t_product_info set pi_isview = '" + opt + "' where pi_id = '" + piid + "'";
			// System.out.println(sql);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("ProductProcDao 클래스의 getView() 메소드 오류");
			e.printStackTrace();
		} finally {	
			close(stmt);
		}
		
		return result;
	}
	
	public int getProductFormInc(ProductOption po) {
	// 각 옵션들(색깔, 등급, 용량)에 따른 증가율을 적용시켜 계산하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try { 
			String sql = "select po_inc from t_product_option " + 
				" where pi_id = '" + po.getPi_id() + "' and po_rank = '" + po.getPo_rank() + 
				"' and po_color = '" + po.getPo_color() + "' and po_memory = '" + po.getPo_memory() + "' ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	result = rs.getInt(1);
			
		} catch(Exception e) {
			System.out.println("ProductProcDao 클래스의 getProductFormInc() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return result;
	}
	
	public int getProductFormUp(String piid, String pidc, String piimg1, String piimg2) {
	// 정가, 할인율, 이미지1, 이미지2를 수정하는 메소드
		Statement stmt = null;
		int result = 0;
		
		try { 
			if (piimg1 != "" && piimg2 != "") {
				String sql = "update t_product_info set pi_dc = '" + pidc + "', pi_img1 = '" + piimg1 + 
						"', pi_img2 = '" + piimg2 + "'  where pi_id = '" + piid + "'";
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
				
			} else if (piimg1 != "" && piimg2 == "") {
				String sql = "update t_product_info set pi_dc = '" + pidc + "', pi_img1 = '" + piimg1 + 
						"' where pi_id = '" + piid + "'";
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
				
			} else if (piimg1 == "" && piimg2 != "") {
				String sql = "update t_product_info set pi_dc = '" + pidc + "', pi_img2 = '" + piimg2 + 
						"' where pi_id = '" + piid + "'";
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
				
			} else {
				String sql = "update t_product_info set pi_dc = '" + pidc + "' where pi_id = '" + piid + "'";
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
			}
			
		} catch(Exception e) {
			System.out.println("ProductProcDao 클래스의 getProductFormUp() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public ArrayList<ProductOption> getStockList(int cpage, int psize, String where){
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ProductOption> productStockList = new ArrayList<ProductOption>();
		ProductOption po = null;
		
		try {
			String sql = "select a.po_idx, b.pi_id, b.pi_name, a.po_memory, a.po_color, a.po_rank, a.po_stock " + 
				 " from t_product_option a, t_product_info b, t_product_brand c " + 
			     " where a.pi_id = b.pi_id and b.pb_id = c.pb_id " +  where +
				 " order by a.po_idx " + 
				 " limit " + ((cpage - 1) * psize) + ", " + psize;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				po = new ProductOption();
				po.setPo_idx(rs.getInt("po_idx"));
				po.setPi_id(rs.getString("pi_id"));
				po.setPi_name(rs.getString("pi_name"));
				po.setPo_memory(rs.getString("po_memory"));
				po.setPo_color(rs.getString("po_color"));
				po.setPo_rank(rs.getString("po_rank"));
				po.setPo_stock(rs.getInt("po_stock"));
				productStockList.add(po);
			}
			
		}catch(Exception e) {
			System.out.println("ProductProcDao 클래스의 getStockList() 메소드 오류");
			e.printStackTrace();
		}finally {
			close(rs);	close(stmt);
		}
		return productStockList;
	}

	public int getStockListCount(String where) {
		Statement stmt = null;
		ResultSet rs = null;
		int rcnt = 0;
		
		try {
			String sql = "select count(a.po_idx) from t_product_option a, t_product_info b, t_product_brand c " + 
					" where a.pi_id = b.pi_id and b.pb_id = c.pb_id " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	rcnt = rs.getInt(1);
			
		} catch(Exception e) {
			System.out.println("ProductProcDao 클래스의 getStockListCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return rcnt;
	}

	public int stockUpdate(ProductOption po) {
		Statement stmt = null;
		int result = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "update t_product_option set po_stock = po_stock + " + po.getPo_stock() + " where po_idx = '" + po.getPo_idx() + "' "; 
			// System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("ProductProcDao 클래스의 stockUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
}
