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

public class InsertAction implements Action {

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
		Long member_no = ((UserVO) request.getSession().getAttribute("authUser")).getNo();

		BoardVO newVo = new BoardVO();
		newVo.setTitle(title);
		newVo.setContents(contents);
		newVo.setMember_no(member_no);

		new BoardDAO().create(newVo);
		WebUtil.redirect(request.getContextPath() + "/board", request, response);
	}

}
