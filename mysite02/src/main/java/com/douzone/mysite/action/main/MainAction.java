package com.douzone.mysite.action.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.web.action.Action;

public class MainAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int count = 0;

		// cookie 읽기
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if ("visitCount".equals(c.getName())) {
					count = Integer.parseInt(c.getValue());
				}
			}
		}

		// cookie 쓰기
		Cookie cookie = new Cookie("visitCount", String.valueOf(++count));
		cookie.setMaxAge(24 * 60 * 60);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);

		request.getRequestDispatcher("/WEB-INF/views/main/index.jsp").forward(request, response);

	}

}
