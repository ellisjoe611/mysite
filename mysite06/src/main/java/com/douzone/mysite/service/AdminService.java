package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.AdminRepository;
import com.douzone.mysite.vo.MainVO;

@Service
public class AdminService {

	@Autowired
	private AdminRepository repository;
	
	public MainVO getWelcome() {
		return repository.find();
	}
	
	public Boolean update(MainVO vo) {
		return repository.update(vo);
	}
}
