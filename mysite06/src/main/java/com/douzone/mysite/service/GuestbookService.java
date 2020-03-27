package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVO;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookRepository repository;

	public boolean isLong(Long input) {
		try {
			Long.parseLong(String.valueOf(input));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isNotNull(GuestbookVO vo) {
		if (vo == null) {
			return false;
		}
		if (vo.getName() == null || "".equals(vo.getName())) {
			return false;
		}
		if (vo.getPw() == null || "".equals(vo.getPw())) {
			return false;
		}
		if (vo.getContents() == null || "".equals(vo.getContents())) {
			return false;
		}

		return true;
	}

	public List<GuestbookVO> index() {
		List<GuestbookVO> list = repository.findall();
		return list;
	}

	public boolean add(GuestbookVO vo) {
		return repository.insert(vo);

	}

	public boolean exists(Long no) {
		GuestbookVO vo = new GuestbookVO();
		vo.setNo(no);

		return repository.exists(vo);
	}

	public boolean delete(GuestbookVO vo) {
		return repository.delete(vo);
	}
}
