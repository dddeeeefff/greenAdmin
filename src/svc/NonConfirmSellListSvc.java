package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import java.util.*;
import dao.*;
import vo.*;

public class NonConfirmSellListSvc {
	public ArrayList<SellInfo> getNonSellList(int cpage, int psize, String where) {
		ArrayList<SellInfo> sellList = new ArrayList<SellInfo>();
		Connection conn = getConnection();
		SellProcDao sellProcDao = SellProcDao.getInstance();
		sellProcDao.setConnection(conn);
		
		sellList = sellProcDao.getNonSellList(cpage, psize, where);
		close(conn);
		
		return sellList;
	}
	
	public int getNonSellListCount(String where) {
		int rcnt = 0;
		Connection conn = getConnection();
		SellProcDao sellProcDao = SellProcDao.getInstance();
		sellProcDao.setConnection(conn);
		
		rcnt = sellProcDao.getNonSellListCount(where);
		close(conn);
		
		return rcnt;
	}
	
}
