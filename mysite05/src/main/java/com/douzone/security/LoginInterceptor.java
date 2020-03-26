package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVO;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private UserService service;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		UserVO vo = new UserVO();
		vo.setEmail(request.getParameter("email"));
		vo.setPw(request.getParameter("pw"));
		
		UserVO authUser = service.login(vo);
		if(authUser == null) {
			request.setAttribute("vo", vo);
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
			return false;
		}
		
//		System.out.println(authUser.toString());
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath());
		return false;
		
	}
	
	
}
