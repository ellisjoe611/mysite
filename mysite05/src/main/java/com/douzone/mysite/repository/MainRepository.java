package com.douzone.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.MainVO;

@Repository
public class MainRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public MainVO find() {
		return sqlSession.selectOne("main.find");
	}
	
}
