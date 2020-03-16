package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVO;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "", "/login" }, method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

//	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
//	public String login(HttpSession session, @ModelAttribute("vo") UserVO vo, Model model) {
//		UserVO authUser = userService.login(vo);
//		if (authUser == null) {
//			return "user/login";
//		} else {
//			session.setAttribute("authUser", authUser);
//			return "redirect:/";
//		}
//	}

//	@RequestMapping(value = { "/logout" })
//	public String logout(HttpSession session) {
//		session.removeAttribute("authUser");
//		session.invalidate();
//		return "redirect:/";
//	}

	@RequestMapping(value = { "/join" }, method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}

	@RequestMapping(value = { "/join" }, method = RequestMethod.POST)
	public String join(UserVO vo) {
		if (userService.join(vo) == false) {
			return "user/join";
		} else {
			return "redirect:/user/joinsuccess";
		}
	}

	@RequestMapping(value = { "/joinsuccess" })
	public String joinSuccess() {
		return "user/joinsuccess";
	}

	@Auth
	@RequestMapping(value = { "/update" }, method = RequestMethod.GET)
	public String update(@AuthUser UserVO authUser, Model model) {
//		UserVO authUser = (UserVO) session.getAttribute("authUser");
//		if (session == null || authUser == null) {
//			return "redirect:/user/login";
//		}

		UserVO vo = userService.findUserInfo(authUser);
		model.addAttribute("vo", vo);
		return "user/update";
	}

	@Auth
	@RequestMapping(value = { "/update" }, method = RequestMethod.POST)
	public String udpate(@AuthUser UserVO authUser, UserVO updatedVo, Model model) {
//		UserVO authUser = (UserVO) session.getAttribute("authUser");
//		if (session == null || authUser == null) {
//			return "redirect:/user/login";
//		}
		if(updatedVo.getName() != null && "".equals(updatedVo.getName()) != true) {
			authUser.setName(updatedVo.getName());
		}
		updatedVo.setNo(authUser.getNo());
		
		if (userService.update(updatedVo) == false) {
			model.addAttribute("vo", updatedVo);
			return "user/update";
		} else {
			return "redirect:/";
		}
	}
	
}
