package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douzone.mysite.vo.UserVO;

public class UserDAO {

	// 사용자 정보 업데이트 ( [name, pw??, gender, no, email] ---> true/false )
	public Boolean update(UserVO vo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = getConnection();

			String password = vo.getPw();
			if (password != null && "".equals(password) != true) {
				String command = "update user set name = ?, pw = password(?), gender = ? where no = ?";
				statement = conn.prepareStatement(command);
				
				statement.setString(1, vo.getName());
				statement.setString(2, vo.getPw());
				statement.setString(3, vo.getGender());
				statement.setLong(4, vo.getNo());
			} else {
				String command = "update user set name = ?, gender = ? where no = ?";
				statement = conn.prepareStatement(command);
				
				statement.setString(1, vo.getName());
				statement.setString(2, vo.getGender());
				statement.setLong(3, vo.getNo());
			}

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

	// create ( [name, email, pw, gender] ---> true/false )
	public Boolean insert(UserVO vo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = getConnection();

			String command = "insert into user values(null, ?, ?, password(?), ?, now())";
			statement = conn.prepareStatement(command);

			statement.setString(1, vo.getName());
			statement.setString(2, vo.getEmail());
			statement.setString(3, vo.getPw());
			statement.setString(4, vo.getGender());

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

	// 사용자 정보 수집용 ( [no] ---> [name, email, gender] )
	public UserVO findUserInfo(UserVO vo) {
		UserVO userVo = null;

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			conn = getConnection();

			String sql = "select name, email, gender from user where no=?";
			statement = conn.prepareStatement(sql);

			statement.setLong(1, vo.getNo());

			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				userVo = new UserVO();
				userVo.setName(resultSet.getString(1));
				userVo.setEmail(resultSet.getString(2));
				userVo.setGender(resultSet.getString(3));
			}

		} catch (SQLException e) {
			System.out.println("[ERROR] " + e);
		} finally {
			// 6. 자원정리
			try {
				if (resultSet != null) {
					resultSet.close();
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

		return userVo;
	}

	// 로그인용 ( [email, pw] ---> [no, name] )
	public UserVO findUser(UserVO vo) {
		UserVO userVo = null;

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			conn = getConnection();

			String sql = "select no, name from user where email=? and pw=password(?)";
			statement = conn.prepareStatement(sql);

			statement.setString(1, vo.getEmail());
			statement.setString(2, vo.getPw());

			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				userVo = new UserVO();
				userVo.setNo(resultSet.getLong(1));
				userVo.setName(resultSet.getString(2));
			}

		} catch (SQLException e) {
			System.out.println("[ERROR] " + e);
		} finally {
			// 6. 자원정리
			try {
				if (resultSet != null) {
					resultSet.close();
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

		return userVo;
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
