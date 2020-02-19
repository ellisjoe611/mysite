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

public class ModifyAction implements Action {

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
		
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		Long no = Long.parseLong(request.getParameter("no"));
		
		BoardVO changedVo = new BoardVO();
		changedVo.setNo(no);
		changedVo.setTitle(title);
		changedVo.setContents(contents);
		
		new BoardDAO().modify(changedVo);
		WebUtil.redirect(request.getContextPath() + "/board", request, response);
	}

}
