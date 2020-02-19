package com.douzone.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.action.main.MainActionFactory;
import com.douzone.web.action.Action;
import com.douzone.web.action.ActionFactory;

public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		String configPath = this.getServletConfig().getInitParameter("config");
		System.out.println("Initializing with [" + configPath + "]");

		super.init();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.service(request, response);
		System.out.println("service() is called...");

	}

	@Override
	public void destroy() {
		super.destroy();
		System.out.println("service() is destroyed...");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String actionName = request.getParameter("a");

		ActionFactory factory = new MainActionFactory();
		Action action = factory.getAction(actionName);
		action.execute(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
