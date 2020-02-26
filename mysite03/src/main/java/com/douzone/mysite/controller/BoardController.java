package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVO;
import com.douzone.mysite.vo.UserVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;

	// Board 색션의 맨 첫 페이지
	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd, Model model) {
		model = service.getUpdatedListModel(page, kwd, model);

		return "board/list";
	}

	// 해당 Board 조회하기
	@RequestMapping(value = { "/view/{no}" }, method = RequestMethod.GET)
	public String view(@PathVariable("no") Long no, HttpSession session, Model model) {
		if(no == null) {
			return "redirect:/";
		}
		
		BoardVO boardVo = service.find(no);
		if(boardVo == null) {
			return "redirect:/";
		}
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("authUser", session.getAttribute("authUser"));
		return "board/view";
	}

	// Board 추가 form
	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String add(HttpSession session) {
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if (authUser == null) {
			return "user/login";
		} else {
			return "board/write";
		}
	}
	
	// Board 추가 처리
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String add(HttpSession session, BoardVO vo) {
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if (authUser == null) {
			return "user/login";
		}
		vo.setMember_no(authUser.getNo());
		if(service.addBoard(vo) == false) {
			return "board/write";
		}
		
		return "redirect:/board?page=1";
	}
	
	// Board 수정 form
	// BoardVO의 no, title, contents 필수
	@RequestMapping(value = { "/modify/{no}" }, method = RequestMethod.GET)
	public String modify(HttpSession session, @PathVariable("no") Long no, Model model) {
		if(no == null) {
			return "redirect:/";
		}
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if (authUser == null ||  service.getPermissionForBoardManagement(no, authUser) == false) {
			return "user/login";
		}
		
		BoardVO boardVo = service.find(no);
		if(boardVo == null) {
			return "redirect:/";
		}
		model.addAttribute("boardVo", boardVo);
		return "board/write";
	}
	
	// Board 수정 실행
	// BoardVO의 no, title, contents가 request에 담겨짐
	@RequestMapping(value = { "/modify" }, method = RequestMethod.POST)
	public String modify(HttpSession session, BoardVO vo) {
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if (authUser == null) {
			return "user/login";
		}
		
		if(service.modifyBoard(vo, authUser) == false) {
			return "redirect:/";
		}
		return "redirect:/board/view/" + vo.getNo();
	}
	
	// reply 페이지 form으로 연결
	@RequestMapping(value = { "/reply/{no}" }, method = RequestMethod.GET)
	public String reply(HttpSession session, @PathVariable("no") Long no, Model model) {
		if(no == null) {
			return "redirect:/";
		}
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if (authUser == null) {
			return "user/login";
		}
		
		BoardVO targetVo = service.find(no);
		model.addAttribute("targetVo", targetVo);
		return "board/reply";
	}
	
	// reply 추가를 실행
	// 기존의 vo(대상의 no, g_no, o_no, depth, title, contents) + member_no (authUser.no)
	@RequestMapping(value = { "/reply" }, method = RequestMethod.POST)
	public String reply(HttpSession session, BoardVO inputVo) {
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		
		if(service.addReply(inputVo, authUser.getNo()) == false) {
			return "redirect:/";
		}
		
		return "redirect:/board?page=1";
	}
	
	// Board 삭제
	@RequestMapping(value = { "/delete/{no}" }, method = RequestMethod.GET)
	public String remove(HttpSession session, @PathVariable("no") Long no) {
		if(no == null) {
			return "redirect:/";
		}
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if (authUser == null ||  service.getPermissionForBoardManagement(no, authUser) == false) {
			return "redirect:/";
		}
		
		if(service.remove(no, authUser) == false) {
			return "redirect:/";
		}
		return "redirect:/board?page=1";
		
	}
	
}
