package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.GuestbookVO;

public class GuestbookDAO {
	
	// select (all)
	public List<GuestbookVO> findall(){
		List<GuestbookVO> result = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet set = null;
		
		try {
			conn = getConnection();
			
			String command = "select no, name, contents, pw, reg_date from guestbook order by no desc";
			statement = conn.prepareStatement(command);
			
			set = statement.executeQuery();
			while (set.next()) {
				Long no = set.getLong(1);
				String name = set.getString(2);
				String contents = set.getString(3);
				String pw = set.getString(4);
				String reg_date = set.getString(5);
				
				GuestbookVO vo = new GuestbookVO();
				vo.setNo(no);
				vo.setName(name);
				vo.setContents(contents);
				vo.setPw(pw);
				vo.setReg_date(reg_date);
				
				result.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("[ERROR] " + e);
		} finally {
			// 6. 자원정리
			try {
				if (set != null) {
					set.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	// create
	public Boolean insert(GuestbookVO vo) {
		Boolean result = false;
		
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			conn = getConnection();
			
			String command = "insert into guestbook values(null, ?, ?, password(?), now())";
			statement = conn.prepareStatement(command);
			
			statement.setString(1, vo.getName());
			statement.setString(2, vo.getContents());
			statement.setString(3, vo.getPw());
			
			result = (statement.executeUpdate() == 1);
			
		} catch (SQLException e) {
			System.out.println("[ERROR] " + e);
		} finally {
			// 6. 자원정리
			try {
				if (statement != null) {
					statement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	// delete
	public Boolean delete(GuestbookVO vo) {
		Boolean result = false;
		
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			conn = getConnection();
			
			String command = "delete from guestbook where no = ? and pw = password(?)";
			statement = conn.prepareStatement(command);
			
			statement.setLong(1, vo.getNo());
			statement.setString(2, vo.getPw());
			
			result = (statement.executeUpdate() == 1);
			
		} catch (SQLException e) {
			System.out.println("[ERROR] " + e);
		} finally {
			// 6. 자원정리
			try {
				if (statement != null) {
					statement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	// Connection
	private Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mysql://192.168.1.118:3307/webdb";

			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("SQL 연결 실패:" + e);
		}

		return conn;
	}
}
