package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import java.util.*;
import dao.*;
import vo.*;

public class ProductStockListSvc {
	public ArrayList<ProductOption> getStockList(int cpage, int psize, String where){
		ArrayList<ProductOption> productStock = new ArrayList<ProductOption>();
		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		productStock = productProcDao.getStockList(cpage, psize, where);
		close(conn);
		
		return productStock;
	}
	
	public int getStockListCount(String where) {
		int rcnt = 0;
		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		rcnt = productProcDao.getStockListCount(where);
		close(conn);
		
		return rcnt;
	}
}
