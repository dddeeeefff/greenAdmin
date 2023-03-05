package dao;

import static db.JdbcUtil.*;
import java.sql.*;
import vo.*;

public class LoginDao {
	// 로그인 정보를 가져오는 클래스
	private static LoginDao loginDao;
	private Connection conn;
	private LoginDao() {}
	
	public static LoginDao getInstance() {
		
		if (loginDao == null)	loginDao = new LoginDao();
		
		return loginDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public AdminInfo getLoginInfo(String uid, String pwd) {
		Statement stmt = null;
		ResultSet rs = null;
		AdminInfo loginInfo = null;
		
		try {
			String sql = "select * from t_admin_info where ai_id = '" + uid + "' and ai_pw = '" + pwd + "' and ai_use = 'y'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				loginInfo = new AdminInfo();
				loginInfo.setAi_idx(rs.getInt("ai_idx"));
				loginInfo.setAi_id(rs.getString("ai_id"));
				loginInfo.setAi_pw(rs.getString("ai_pw"));
				loginInfo.setAi_name(rs.getString("ai_name"));
				loginInfo.setAi_use(rs.getString("ai_use"));
				loginInfo.setAi_date(rs.getString("ai_date"));
				
			}
			
		} catch (Exception e) {
			System.out.println("LoginDao 클래스의 getLoginInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);		close(stmt);
		}
		
		return loginInfo;
	}
}
