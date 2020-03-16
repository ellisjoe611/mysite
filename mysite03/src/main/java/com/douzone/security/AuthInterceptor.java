package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.vo.UserVO;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if((handler instanceof HandlerMethod) == false) {
			// DefaultServleHandler가 처리하는 경우 (보통, assets의 정적 지원 접근)
			return true;
		}
		
		// casting 시작
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		// Handler의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// Method에 @Auth가 없으면 Type가 붙어있는지 확인하기
		if(auth == null) {
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
		}
		
		// Type이나 Method에 둘 다 @Auth가 붙지 않은 경우
		if(auth == null) {
			return true;
		}
		
		// 인증 여부 (@Auth가 붙어있기 때문)
		HttpSession session = request.getSession();
		if(session == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 권한 부여(Authorization) 체크를 위해 @Auth의 role 가져오기
		String role = auth.value();
		
		
		// @Auth의 role이 'USER'인 겅우
		// authUser의 role이 USER이든 ADMIN이든 상관없음
		if("USER".equals(role)) {
			return true;
		}
		
		// @Auth의 role이 'ADMIN'인 겅우
		// 반드시 authUser의 role이 ADMIN이어야 한다
		if("ADMIN".equals(authUser.getRole()) == false) {
			response.sendRedirect(request.getContextPath());
			return false;
		} else {
			
		}
		
		// @Auth의 role => "ADMIN"
		// authUser의 role => "ADMIN"
		// 관리자 권한이 확인 되었으므로 handlerMethod 실행하기
		return true;
	}
	
}
