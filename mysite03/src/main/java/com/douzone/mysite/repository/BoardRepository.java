package com.douzone.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVO;

@Repository
public class BoardRepository {
	
	@Autowired
	private SqlSession sqlSession;

	// 목록을 조회하기
	// [startfrom] >>> [contents를 제외한 나머지]
	public List<BoardVO> findall(Map<String, Object> searchMap) {
		List<BoardVO> result = sqlSession.selectList("board.findall", searchMap);
		result.get(0).setCounted(sqlSession.selectOne("board.getcounted", searchMap));
		return result;
	}

	// 한 댓글의 내용을 조회하기
	// [no] >>> [VO 내용 전체]
	public BoardVO find(Long no) {
		return sqlSession.selectOne("board.find", no);
	}

	// 맨 처음으로 board를 추가했을 때
	// [title, contents, member_no] >>> [true/false]
	public Boolean insert(BoardVO vo) {
		return sqlSession.insert("board.insert", vo) == 1;
	}
	
	// 개시판 내용을 업데이트
	// [title, contents, no] >>> [true/false]
	public Boolean modify(BoardVO vo) {
		return sqlSession.update("board.modify", vo) == 1;
	}

	// 방문자 수를 늘리기
	// [no] >>> [true/false]
	public Boolean addhit(Long no) {
		return sqlSession.update("board.addhit") == 1;
	}

	// Board 제거
	// [no] >>> [true/false]
	public Boolean delete(Long no) {
		return sqlSession.delete("board.delete", no) == 1;
	}
	
	// 해당 Board를 작성한 사람의 member_no 불러오기
	// 이는 한 사용자가 URL 주소를 통해 다른 사용자의 board 수정/삭제하는 것을 방지하기 위함이다
	public Long getBoardOwnerNo(Long boardNo) {
		return sqlSession.selectOne("board.getBoardOwnerNo", boardNo);
	}
	
	
	/*********************************************** 답글 추가 *************************************************/
	// 1. 같은 그룹(g_no)의 번호를 한칸씩 뒤로 미루기
	// 이 때에 적용 대상이 0인 경우도 있으니 참고하기!
	// [대상의 g_no, 대상의 o_no] >>> [true/false]
	public Boolean updategroup(BoardVO inputVo) {
		return sqlSession.update("board.updategroup", inputVo) >= 0;
	}

	// 2. 댓글을 삽입하기
	// [title, contents, member_no, 대상의 no] -> [true/false]
	public Boolean addreply(BoardVO inputVo) {
		return sqlSession.insert("board.addreply", inputVo) >= 1;
	}

	/***********************************************************************************************/

}
