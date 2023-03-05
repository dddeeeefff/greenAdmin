package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ProductFormIncSvc {
	public int getProductFormInc(ProductOption po) {
		int result = 0;
		Connection conn = getConnection();
		ProductProcDao productProcDao = ProductProcDao.getInstance();
		productProcDao.setConnection(conn);
		
		result = productProcDao.getProductFormInc(po);
		close(conn);

		return result;
	}
}
