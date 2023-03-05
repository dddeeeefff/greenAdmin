package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import java.util.*;
import dao.*;
import vo.*;

public class SellListSvc {
	public ArrayList<SellInfo> getSellList(int cpage, int psize, String where) {
		ArrayList<SellInfo> sellList = new ArrayList<SellInfo>();
		Connection conn = getConnection();
		SellProcDao sellProcDao = SellProcDao.getInstance();
		sellProcDao.setConnection(conn);
		
		sellList = sellProcDao.getSellList(cpage, psize, where);
		close(conn);
		
		return sellList;
	}
	
	public int getSellListCount(String where) {
		int rcnt = 0;
		Connection conn = getConnection();
		SellProcDao sellProcDao = SellProcDao.getInstance();
		sellProcDao.setConnection(conn);
		
		rcnt = sellProcDao.getSellListCount(where);
		close(conn);
		
		return rcnt;
	}
	
	
	
}
