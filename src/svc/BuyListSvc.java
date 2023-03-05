package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class BuyListSvc {
	public int getBuyListCount(String where) {
		int rcnt = 0;
		Connection conn = getConnection();
		BuyProcDao buyProcDao = BuyProcDao.getInstance();
		buyProcDao.setConnection(conn);
		
		rcnt = buyProcDao.getBuyListCount(where);
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<BuyInfo> getBuyList(String where, int cpage, int psize) {
		ArrayList<BuyInfo> buyList = new ArrayList<BuyInfo>();
		Connection conn = getConnection();
		BuyProcDao buyProcDao = BuyProcDao.getInstance();
		buyProcDao.setConnection(conn);
		
		buyList = buyProcDao.getBuyList(where, cpage, psize);
		close(conn);
		
		return buyList;
	}
	
}
