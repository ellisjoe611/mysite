package com.douzone.mysite.controller;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@RequestMapping(value = { "/join" }, method = RequestMethod.GET)
	public String join(@ModelAttribute UserVO vo) {
		return "user/join";
	}

	@RequestMapping(value = { "/join" }, method = RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVO vo, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join"; 
		}
		userService.join(vo);
		System.out.println(vo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value = { "/joinsuccess" })
	public String joinSuccess() {
		return "user/joinsuccess";
	}

	@Auth
	@RequestMapping(value = { "/update" }, method = RequestMethod.GET)
	public String update(@AuthUser UserVO authUser, Model model) {
		UserVO vo = userService.findUserInfo(authUser);
		model.addAttribute("vo", vo);
		return "user/update";
	}

	@Auth
	@RequestMapping(value = { "/update" }, method = RequestMethod.POST)
	public String udpate(@AuthUser UserVO authUser, UserVO updatedVo, Model model) {
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
