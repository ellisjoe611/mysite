package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.vo.MainVO;
import com.douzone.security.Auth;

@Auth("ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping(value = { "", "/main" })
	public String main(Model model) {
		model.addAttribute("vo", adminService.getWelcome());
		return "admin/main";
	}
	
	@RequestMapping(value = { "/main/update" }, method = RequestMethod.POST)
	public String mainUpdate(Model model, 
			@RequestParam(value="title", required=true, defaultValue="Main") String title, 
			@RequestParam(value="welcome", required=true, defaultValue="Welcome!") String welcome, 
			@RequestParam(value="upload-file") MultipartFile multipartFile, 
			@RequestParam(value="description", required=true, defaultValue="No description yet...") String description) {
		String profile = fileUploadService.restore(multipartFile);
		
		MainVO vo = new MainVO();
		vo.setTitle(title);
		vo.setWelcome(welcome);
		vo.setProfile(profile);
		vo.setDescription(description);
		
		System.out.println(vo.toString());
		adminService.update(vo);		
		
		return "admin/main";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}