package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVO;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService service;

	@RequestMapping(value = { "" })
	public String index(Model model) {
		List<GuestbookVO> list = service.index();
		model.addAttribute("list", list);

		return "guestbook/list";
	}

	// guestbook 추가
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String add(@ModelAttribute("guestbookVo") GuestbookVO vo) {
		if (service.isNotNull(vo) == false) {
			return "redirect:/guestbook";
		}
		if (service.add(vo) == false) {
			return "guestbook/list";
		} else {
			return "redirect:/guestbook";
		}
	}

	// delete form 연결
	@RequestMapping(value = { "/delete/{no}" }, method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		if (no == null || service.isLong(no) == false) {
			return "redirect:/";
		}

		if (service.exists(no) == false) {
			return "redirect:/guestbook";
		}

		return "guestbook/delete";
	}

	// 삭제하기
	@RequestMapping(value = { "/delete" }, method = RequestMethod.POST)
	public String delete(@ModelAttribute("guestbookVo") GuestbookVO vo, Model model) {
		if (service.delete(vo) == false) {
			return "redirect:/guestbook/delete/" + vo.getNo();
		} else {
			return "redirect:/guestbook";
		}
	}

}
