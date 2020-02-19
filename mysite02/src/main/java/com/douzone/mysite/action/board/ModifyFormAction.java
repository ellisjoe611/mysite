package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardDAO;
import com.douzone.mysite.vo.BoardVO;
import com.douzone.mysite.vo.UserVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		if (session == null) {
			WebUtil.redirect(request.getContextPath() + "/user?a=login", request, response);
			return;
		}

		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if (authUser == null) {
			WebUtil.redirect(request.getContextPath() + "/user?a=login", request, response);
			return;
		}

		BoardVO search = new BoardVO();
		search.setNo(Long.parseLong(request.getParameter("formno")));

		BoardVO boardVo = new BoardDAO().find(search);
		if (boardVo == null) {
			WebUtil.redirect(request.getContextPath() + "/board", request, response);
			return;
		}

		if (authUser.getName().equals(boardVo.getMember_name()) == false) {
			WebUtil.redirect(request.getContextPath() + "/board", request, response);
			return;
		}

		request.setAttribute("boardVo", boardVo);
		WebUtil.foward("/WEB-INF/views/board/modify.jsp", request, response);

	}

}
