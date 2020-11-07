package com.journaldev.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			ServletContext ctx = request.getServletContext();
			ctx.setAttribute("User", "Pankaj");
			String user = (String) ctx.getAttribute("User");
			ctx.removeAttribute("User");
			
			HttpSession session = request.getSession();
			session.invalidate();
			
			PrintWriter out = response.getWriter();
			out.write("Hi "+user);
	}
	
	/*
	 * Whenever we sent any new request like:
	 
		http://localhost:8090/ServletListenerExample/MyServlet
		
	output:
		ServletRequest initialized. Remote IP=0:0:0:0:0:0:0:1
		ServletContext attribute added::{User,Pankaj}
		ServletContext attribute removed::{User,Pankaj}
		Session Created:: ID=77533692667D7731D838EEC4AECAE42F
		Session Destroyed:: ID=77533692667D7731D838EEC4AECAE42F
		ServletRequest destroyed. Remote IP=0:0:0:0:0:0:0:1
	
	/*
	 * When we deploy this application on the tomcat server:
	 * 
	 * Output: 
	 * Database connection closed for Application.
	 * ServletContext attribute added::{DBManager,DBConnectionManager [dbURL=jdbc:mysql://localhost/mysql_db, user=pankaj, password=password, con=null]}
	 * Database connection initialized for Application.
	 * ServletContext attribute added::{org.apache.jasper.compiler.TldLocationsCache,org.apache.jasper.compiler.TldLocationsCache@3774210}
	 */

}
