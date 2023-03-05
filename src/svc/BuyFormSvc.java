package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class BuyFormSvc {
	public BuyInfo getBuyInfo(String biid) {
		BuyInfo buyInfo = null;
		Connection conn = getConnection();
		BuyProcDao buyProcDao = BuyProcDao.getInstance();
		buyProcDao.setConnection(conn);
		
		buyInfo = buyProcDao.getBuyInfo(biid);
		close(conn);
		
		return buyInfo;
	}
}
