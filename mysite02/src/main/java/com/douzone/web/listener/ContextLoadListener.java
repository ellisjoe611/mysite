package com.douzone.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextLoadListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent servletContextEvent) {	
		
		System.out.println("Application destroyed...");
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		String contextConfigLocation = context.getInitParameter("contextConfigLocation");
		
		System.out.println("Application initialized...: " + contextConfigLocation);
	}

}
