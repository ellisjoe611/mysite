package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVO;

public class BoardDAO {

	// 목록을 조회하기
	// [startfrom] -> [contents를 제외한 나머지]
	public List<BoardVO> findall(int startFrom) {
		List<BoardVO> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			conn = getConnection();

			String sql = "select B.no, B.title, B.hit, B.reg_date, B.g_no, B.o_no, B.depth, B.member_no, U.name, (select count(*) from board) as counted from board B join user U on (B.member_no = U.no) order by g_no desc, o_no asc limit ?, 5";
			statement = conn.prepareStatement(sql);

			statement.setLong(1, Integer.toUnsignedLong(startFrom));

			set = statement.executeQuery();
			while (set.next()) {
				BoardVO vo = new BoardVO();
				vo.setNo(set.getLong(1));
				vo.setTitle(set.getString(2));
				vo.setHit(set.getLong(3));
				vo.setReg_date(set.getString(4));
				vo.setG_no(set.getLong(5));
				vo.setO_no(set.getLong(6));
				vo.setDepth(set.getLong(7));
				vo.setMember_no(set.getLong(8));
				vo.setMember_name(set.getString(9));
				vo.setCounted(set.getLong(10));

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

	// 한 댓글의 내용을 조회하기
	// [no] -> [VO 내용 전체]
	public BoardVO find(BoardVO searchVo) {
		BoardVO result = new BoardVO();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			conn = getConnection();

			String sql = "select B.no, B.title, B.contents, B.hit, B.reg_date, B.g_no, B.o_no, B.depth, B.member_no, U.name from board B join user U on (B.member_no = U.no) where B.no = ?";
			statement = conn.prepareStatement(sql);

			// 파라미터 설정
			statement.setLong(1, searchVo.getNo());

			set = statement.executeQuery();
			if (set.next()) {
				result.setNo(set.getLong(1));
				result.setTitle(set.getString(2));
				result.setContents(set.getString(3));
				result.setHit(set.getLong(4));
				result.setReg_date(set.getString(5));
				result.setG_no(set.getLong(6));
				result.setO_no(set.getLong(7));
				result.setDepth(set.getLong(8));
				result.setMember_no(set.getLong(9));
				result.setMember_name(set.getString(10));
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

	// 제목을 기준으로 board 검색
	// [BoardVO.title, startFrom] -> [contents를 제외한 나머지]
	// BoardVO.title 안에는 %%가 포함되어 있다.
	public List<BoardVO> findByTitle(BoardVO searchVo, int startFrom) {
		List<BoardVO> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			conn = getConnection();

			String sql = "select B.no, B.title, B.hit, B.reg_date, B.g_no, B.o_no, B.depth, B.member_no, U.name from board B join user U on (B.member_no = U.no) where B.title like ? order by g_no desc, o_no asc, depth asc limit ?, 5;";
			statement = conn.prepareStatement(sql);

			// 파라미터 설정
			statement.setString(1, searchVo.getTitle());
			statement.setLong(2, Integer.toUnsignedLong(startFrom));

			set = statement.executeQuery();
			while (set.next()) {
				BoardVO vo = new BoardVO();
				vo.setNo(set.getLong(1));
				vo.setTitle(set.getString(2));
				vo.setHit(set.getLong(3));
				vo.setReg_date(set.getString(4));
				vo.setG_no(set.getLong(5));
				vo.setO_no(set.getLong(6));
				vo.setDepth(set.getLong(7));
				vo.setMember_no(set.getLong(8));
				vo.setMember_name(set.getString(9));

				result.add(vo);
			}

			if (result != null && result.size() > 0) {
				sql = "select count(*) from (select B.no from board B join user U on (B.member_no = U.no) where B.title like ?) V";
				set.close();
				statement.close();
				statement = conn.prepareStatement(sql);
				statement.setString(1, searchVo.getTitle());
				
				set = statement.executeQuery();
				if(set.next()) {
					Long counted = set.getLong(1);
					result.get(0).setCounted(counted);
				}
				
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

	// 맨 처음으로 board를 추가했을 때
	// [title, contents, member_no] -> [true/false]
	public Boolean create(BoardVO newVo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = getConnection();

			String sql = "insert into board(title, contents, hit, reg_date, g_no, o_no, depth, member_no) select ?, ?, 1, now(), ifnull(max(g_no)+1, 1), 1, 0, ? from board";
			statement = conn.prepareStatement(sql);

			statement.setString(1, newVo.getTitle());
			statement.setString(2, newVo.getContents());
			statement.setLong(3, newVo.getMember_no());

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

	/**********************************************
	 * 답글 추가
	 *************************************************/
	// 1.
	// 같은 그룹(g_no)의 번호를 한칸씩 뒤로 미루기
	// [대상의 g_no, 대상의 o_no] -> [true/false]
	public Boolean updategroup(BoardVO targetVo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = getConnection();

			String sql = "update board set o_no = o_no + 1 where g_no = ? and o_no > ?";
			statement = conn.prepareStatement(sql);

			statement.setLong(1, targetVo.getG_no());
			statement.setLong(2, targetVo.getO_no());

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

	// 2.
	// 댓글을 삽입하기
	// [title, contents, member_no, 대상의 no] -> [true/false]
	public Boolean addreply(BoardVO replyVo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = getConnection();

			String sql = "insert into board(title, contents, hit, reg_date, g_no, o_no, depth, member_no) select ?, ?, 1, now(), g_no, o_no + 1, depth + 1, ? from board where no = ?";
			statement = conn.prepareStatement(sql);

			statement.setString(1, replyVo.getTitle());
			statement.setString(2, replyVo.getContents());
			statement.setLong(3, replyVo.getMember_no());
			statement.setLong(4, replyVo.getNo());

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

	/***********************************************************************************************/

	// 개시판 내용을 업데이트
	// [title, contents, no] -> [true/false]
	public Boolean modify(BoardVO changedVo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = getConnection();

			String sql = "update board set title=?, contents=? where no=?";
			statement = conn.prepareStatement(sql);

			statement.setString(1, changedVo.getTitle());
			statement.setString(2, changedVo.getContents());
			statement.setLong(3, changedVo.getNo());

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

	// 방문자 수를 늘리기
	public Boolean addhit(BoardVO targetVo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = getConnection();

			String sql = "update board set hit = hit + 1 where no = ?";
			statement = conn.prepareStatement(sql);

			statement.setLong(1, targetVo.getNo());

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

	// 개시물 작성자의 no 구하기
	// [no] -> [member_no]
	public Long getBoardOwnerNo(BoardVO searchVo) {
		Long memberNo = null;

		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		try {
			conn = getConnection();

			String sql = "select member_no from board where no = ?";
			statement = conn.prepareStatement(sql);

			statement.setLong(1, searchVo.getNo());

			set = statement.executeQuery();
			if (set.next()) {
				memberNo = set.getLong(1);
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

		return memberNo;
	}

	// 개시물 삭제
	public Boolean delete(BoardVO deleteVo) {
		Boolean result = false;

		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = getConnection();

			String sql = "delete from board where no = ?";
			statement = conn.prepareStatement(sql);

			statement.setLong(1, deleteVo.getNo());

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
