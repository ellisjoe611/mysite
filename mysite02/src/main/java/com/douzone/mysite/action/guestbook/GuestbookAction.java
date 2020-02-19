package com.douzone.mysite.action.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.GuestbookDAO;
import com.douzone.mysite.vo.GuestbookVO;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class GuestbookAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<GuestbookVO> list = new GuestbookDAO().findall();
		request.setAttribute("list", list);

		WebUtil.foward("/WEB-INF/views/guestbook/list.jsp", request, response);
	}

}
