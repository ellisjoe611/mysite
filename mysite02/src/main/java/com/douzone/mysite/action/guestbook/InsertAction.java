package com.douzone.mysite.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.GuestbookDAO;
import com.douzone.mysite.vo.GuestbookVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class InsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name = request.getParameter("name");
		String pw = request.getParameter("pw");
		String contents = request.getParameter("contents");

		GuestbookVO vo = new GuestbookVO();
		vo.setName(name);
		vo.setPw(pw);
		vo.setContents(contents);

		if (new GuestbookDAO().insert(vo) == true) {
			WebUtil.redirect(request.getContextPath() + "/guestbook", request, response);
		}

	}

}
