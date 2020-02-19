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

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();

		if (session != null && session.getAttribute("authUser") != null) {
			UserVO authUser = (UserVO) session.getAttribute("authUser");
			UserVO updateVo = new UserVO();

			updateVo.setName(request.getParameter("name"));
			updateVo.setPw(request.getParameter("pw"));
			updateVo.setEmail(request.getParameter("email"));
			updateVo.setGender(request.getParameter("gender"));
			updateVo.setNo(authUser.getNo());

			if (new UserDAO().update(updateVo) == true) {
				authUser.setName(request.getParameter("name"));
				session.setAttribute("authUser", authUser);
			}
		}

		WebUtil.redirect(request.getContextPath(), request, response);
	}

}
