package com.douzone.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.UserDAO;
import com.douzone.mysite.vo.UserVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class JoinAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserVO vo = new UserVO();
		vo.setName(request.getParameter("name"));
		vo.setEmail(request.getParameter("email"));
		vo.setPw(request.getParameter("pw"));
		vo.setGender(request.getParameter("gender"));
		
		new UserDAO().insert(vo);
		WebUtil.foward("/WEB-INF/views/user/joinsuccess.jsp", request, response);
	}

}
