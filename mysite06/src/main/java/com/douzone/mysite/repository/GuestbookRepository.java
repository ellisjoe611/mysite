package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVO;

@Repository
public class GuestbookRepository {
	@Autowired
	private SqlSession sqlSession;

	// select (all)
	public List<GuestbookVO> findall() {
		return sqlSession.selectList("guestbook.findall");
	}

	// find if "no" exists
	public Boolean exists(GuestbookVO vo) {
		return sqlSession.selectOne("guestbook.find", vo) != null;
	}

	// create
	public Boolean insert(GuestbookVO vo) {
		return (sqlSession.insert("guestbook.insert", vo) == 1);
	}

	// delete
	public Boolean delete(GuestbookVO vo) {
		return (sqlSession.insert("guestbook.delete", vo) == 1);
	}

}
