package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.MainService;

@Controller
@RequestMapping("")
public class MainController {

	@Autowired
	private MainService service;
	
	@RequestMapping(value = { "", "/main" })
	public String index(Model model) {		
		model.addAttribute("vo", service.getWelcome());
		return "main/index";
	}
}
