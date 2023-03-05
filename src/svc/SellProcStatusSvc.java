package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import java.util.*;
import dao.*;
import vo.*;

public class SellProcStatusSvc {
	
	public int upStatus(SellInfo si) {
		int result = 0;
		Connection conn = getConnection();
		SellProcDao sellProcDao = SellProcDao.getInstance();
		sellProcDao.setConnection(conn);
		
		result = sellProcDao.upStatus(si);
		if (result > 0)		commit(conn);
		else				rollback(conn);
		close(conn);
		
		return result;
	}
}
