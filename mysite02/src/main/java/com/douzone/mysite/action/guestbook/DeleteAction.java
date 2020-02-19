package com.douzone.mysite.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.GuestbookDAO;
import com.douzone.mysite.vo.GuestbookVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		GuestbookVO vo = new GuestbookVO();
		vo.setNo(Long.valueOf(request.getParameter("no")));
		vo.setPw(request.getParameter("pw"));

		if (new GuestbookDAO().delete(vo) == true) {
			WebUtil.redirect(request.getContextPath() + "/guestbook", request, response);
		}
	}

}
