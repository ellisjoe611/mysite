package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.vo.UserVO;

public class AdminInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		if(session == null) {
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
			return false;
		}
		
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if(authUser == null) {
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
			return false;
		}
		
		// casting 시작
		HandlerMethod handlerMethod = (HandlerMethod) handler;

		// Handler의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

		// Method에 @Auth가 없으면 Type가 붙어있는지 확인하기
		if (auth == null) {
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
		}

		// Type이나 Method에 둘 다 @Auth가 붙지 않은 경우
		if (auth == null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		if ("ADMIN".equals(authUser.getRole()) == false || "ADMIN".equals(auth.value()) == false) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		// @Auth의 role => "ADMIN"
		// authUser의 role => "ADMIN"
		// 관리자 권한이 확인 되었으므로 Controller 상에서의 handlerMethod 실행하기
		return true;
	}
	
}
