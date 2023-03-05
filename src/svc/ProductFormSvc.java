package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ProductFormSvc {
	public ProductInfo getProductInfo(String piid) {
		ProductInfo pi = null;
		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		pi = productProcDao.getProductInfo(piid);
		close(conn);
		
		return pi;
	}
}
