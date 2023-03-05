package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import java.util.*;
import dao.*;
import vo.*;

public class IndexSvc {
	public int getSellSum() {
	// 올해 총 판매액을 리턴하는 메소드
		int sell_sum = 0;
		Connection conn = getConnection();
		IndexDao indexDao = IndexDao.getInstance();
		indexDao.setConnection(conn);
		
		sell_sum = indexDao.getSellSum();
		close(conn);
		
		return sell_sum;
	}

	public int getBuySum() {
	// 올해 총 구매액을 리턴하는 메소드
		int buy_sum = 0;
		Connection conn = getConnection();
		IndexDao indexDao = IndexDao.getInstance();
		indexDao.setConnection(conn);
		
		buy_sum = indexDao.getBuySum();
		close(conn);
		
		return buy_sum;
	}

	public int getInMember() {
	// 정상 회원 수를 리턴하는 메소드
		int in_member = 0;
		Connection conn = getConnection();
		IndexDao indexDao = IndexDao.getInstance();
		indexDao.setConnection(conn);
		
		in_member = indexDao.getInMember();
		close(conn);
		
		return in_member;
	}

	public int getOutMember() {
	// 탈퇴 회원 수를 리턴하는 메소드
		int out_member = 0;
		Connection conn = getConnection();
		IndexDao indexDao = IndexDao.getInstance();
		indexDao.setConnection(conn);
		
		out_member = indexDao.getOutMember();
		close(conn);
		
		return out_member;
	}

	public int getAnswerNa() {
	// 답변 미등록 문의 수를 리턴하는 메소드
		int answer_na = 0;
		Connection conn = getConnection();
		IndexDao indexDao = IndexDao.getInstance();
		indexDao.setConnection(conn);
		
		answer_na = indexDao.getAnswerNa();
		close(conn);
		
		return answer_na;
	}

	public int getAnswerIn() {
	// 답변 완료 문의 수를 리턴하는 메소드
		int answer_in = 0;
		Connection conn = getConnection();
		IndexDao indexDao = IndexDao.getInstance();
		indexDao.setConnection(conn);
		
		answer_in = indexDao.getAnswerIn();
		close(conn);
		
		return answer_in;
	}

	public int getStockEnough() {
	// 여유 재고 상품 수를 리턴하는 메소드
		int stock_enough = 0;
		Connection conn = getConnection();
		IndexDao indexDao = IndexDao.getInstance();
		indexDao.setConnection(conn);
		
		stock_enough = indexDao.getStockEnough();
		close(conn);
		
		return stock_enough;
	}

	public int getStockNa() {
	// 재고에 여유가 없는 상품 수를 리턴하는 메소드
		int stock_na = 0;
		Connection conn = getConnection();
		IndexDao indexDao = IndexDao.getInstance();
		indexDao.setConnection(conn);
		
		stock_na = indexDao.getStockNa();
		close(conn);
		
		return stock_na;
	}
}
