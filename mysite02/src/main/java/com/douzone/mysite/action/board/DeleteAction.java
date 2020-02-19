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

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 로그인 여부 확인
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

		if (request.getParameter("formno") == null) {
			WebUtil.redirect(request.getContextPath() + "/board", request, response);
			return;
		}
		Long formno = Long.parseLong(request.getParameter("formno"));

		// 로그인한 사람의 no (authUser.no) == 해당 board를 작성한 사람의 no (BoardVO.member_no)
		// 위 조건을 만족 못하면 /board 페이지로 넘기기
		BoardVO searchVo = new BoardVO();
		searchVo.setNo(formno);

		Long boardOwnerNo = new BoardDAO().getBoardOwnerNo(searchVo);
		if (boardOwnerNo == null) {
			WebUtil.redirect(request.getContextPath() + "/board", request, response);
			return;
		}
		if (boardOwnerNo != authUser.getNo()) {
			WebUtil.redirect(request.getContextPath(), request, response);
			return;
		}

		// 인증이 완료되었다는 전제하에 delete 실시
		BoardVO deleteVo = new BoardVO();
		deleteVo.setNo(formno);
		new BoardDAO().delete(deleteVo);

		// 한 페이지 내의 갯수와 연관하여 redirect 실시
		if (request.getParameter("page") == null) {
			WebUtil.redirect(request.getContextPath() + "/board", request, response);
			return;
		}

		WebUtil.redirect(request.getContextPath() + "/board?page=" + request.getParameter("page"), request, response);
	}

}
