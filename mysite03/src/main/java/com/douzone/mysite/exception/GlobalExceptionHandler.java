package com.douzone.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	// 모든 Exception 들은 여기로 들어오게 된다
	@ExceptionHandler(Exception.class)
	public void handlerException(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
		// 1. logging (파일다가 기록 or request에 담아서 출력)
		StringWriter writer = new StringWriter(); // buffer
		e.printStackTrace(new PrintWriter(writer));
		
		request.setAttribute("errorInfo", writer.toString());
		
		// 2. 안내 페이지 표기(정상 종료)
		request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
	}

}
