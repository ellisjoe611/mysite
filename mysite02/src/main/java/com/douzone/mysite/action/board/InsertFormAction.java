package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.UserDAO;
import com.douzone.mysite.vo.UserVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class InsertFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 접근제어부터 먼저 한다!
		// 로그인이 안되 있으면 loginform.jsp로 보내기
		HttpSession session = request.getSession();
		if (session == null) {
			WebUtil.redirect(request.getContextPath()+"/user?a=login", request, response);
			return;
		}

		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if (authUser == null) {
			WebUtil.redirect(request.getContextPath()+"/user?a=login", request, response);
			return;
		}
		
		UserVO searchVo = new UserVO();
		searchVo.setNo(authUser.getNo());
		
		request.setAttribute("currentUserInfo", new UserDAO().findUserInfo(searchVo));
		WebUtil.foward("/WEB-INF/views/board/write.jsp", request, response);
	}

}
