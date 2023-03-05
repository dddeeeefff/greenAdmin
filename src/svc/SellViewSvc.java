package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class SellViewSvc {
	public ArrayList<SellDetail> getDetailList(String siid){
		ArrayList<SellDetail> detailList = new ArrayList<SellDetail>();
		Connection conn = getConnection();
		SellProcDao sellProcDao = SellProcDao.getInstance();
		sellProcDao.setConnection(conn);
		detailList = sellProcDao.getDetailList(siid);
		close(conn);
		
		return detailList;
	}
	
	public SellInfo getSellInfo(String siid) {
		SellInfo sellInfo = null;
		Connection conn = getConnection();
		SellProcDao sellProcDao = SellProcDao.getInstance();
		sellProcDao.setConnection(conn);
		sellInfo = sellProcDao.getSellInfo(siid);
		close(conn);
		
		return sellInfo;
	}
	
	
}
