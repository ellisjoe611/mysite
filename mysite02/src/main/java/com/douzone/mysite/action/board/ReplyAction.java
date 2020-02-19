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

public class ReplyAction implements Action {

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

		Long member_no = authUser.getNo();

		Long target_no = Long.parseLong(request.getParameter("target_no"));
		Long target_g_no = Long.parseLong(request.getParameter("target_g_no"));
		Long target_o_no = Long.parseLong(request.getParameter("target_o_no"));

		String title = request.getParameter("title");
		String contents = request.getParameter("contents");

		// 1. 먼저 해당 그룹(g_no) 내의 o_no를 뒤로 미룬다.
		BoardVO targetVo = new BoardVO();
		targetVo.setG_no(target_g_no);
		targetVo.setO_no(target_o_no);
		new BoardDAO().updategroup(targetVo);

		// 2. 빈 자리에 새 댓글 삽입
		BoardVO replyVo = new BoardVO();
		replyVo.setTitle(title);
		replyVo.setContents(contents);
		replyVo.setMember_no(member_no);
		replyVo.setNo(target_no);
		new BoardDAO().addreply(replyVo);

		WebUtil.redirect(request.getContextPath() + "/board", request, response);

	}

}
