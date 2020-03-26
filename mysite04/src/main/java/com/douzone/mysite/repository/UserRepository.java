package com.douzone.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVO;

@Repository
public class UserRepository {

	@Autowired
	private SqlSession sqlSession;

	// 회원가입 ( [name, email, pw, gender] >>> [true/false] )
	public Boolean insert(UserVO vo) {
		return sqlSession.insert("user.insert", vo) == 1;
	}

	// 사용자 정보 업데이트 ( [name, pw??, gender, no] >>> [true/false] )
	public Boolean update(UserVO vo) {
		return sqlSession.update("user.update", vo) == 1;
	}

	// 로그인용 ( [email, pw] >>> [no, name] )
	public UserVO findUser(UserVO vo) {
		return sqlSession.selectOne("user.findUser", vo);
	}

	// 사용자 정보 수집용 ( [no] >>> [name, email, gender] )
	public UserVO findUserInfo(UserVO vo) {
		return sqlSession.selectOne("user.findUserInfo", vo);
	}

}
