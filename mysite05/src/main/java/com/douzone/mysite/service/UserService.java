package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVO;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;

	public boolean join(UserVO vo) {
		return repository.insert(vo);
	}
	
	public UserVO login(UserVO vo) {
		return repository.findUser(vo);
	}
	
	public UserVO findUserInfo(UserVO authUser) {
		return repository.findUserInfo(authUser);
	}
	
	public boolean update(UserVO updatedVo) {
		return repository.update(updatedVo);
	}

}
