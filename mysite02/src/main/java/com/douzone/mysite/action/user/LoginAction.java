package com.douzone.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.UserDAO;
import com.douzone.mysite.vo.UserVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserVO vo = new UserVO();
		vo.setEmail(request.getParameter("email"));
		vo.setPw(request.getParameter("pw"));

		UserVO authUser = new UserDAO().findUser(vo);
		if (authUser == null) {
			// 로그인 실패
			request.setAttribute("logincheck", false);
			WebUtil.foward("/WEB-INF/views/user/loginform.jsp", request, response);
			return;
		}

		// 로그인 처리
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		WebUtil.redirect(request.getContextPath(), request, response);

	}

}
