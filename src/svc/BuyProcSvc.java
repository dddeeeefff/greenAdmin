package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import dao.*;
import vo.*;

public class BuyProcSvc {
	public int buyProcUp(String biid, String birank, String bistatus, String where) {
		int result = 0;
		Connection conn = getConnection();
		BuyProcDao buyProcDao = BuyProcDao.getInstance();
		buyProcDao.setConnection(conn);
		
		result = buyProcDao.buyProcUp(biid, birank, bistatus, where);
		if ((bistatus.equals("k") && result == 2) || result == 1)		commit(conn);
		else					rollback(conn);
		close(conn);
		
		return result;
	}
}
