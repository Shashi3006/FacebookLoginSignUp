package com.facebook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet
{
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet res;
	String url = "jdbc:mysql://localhost:3306/jdbc_test";
	String un = "root";
	String pass = "Shashi@3006";
	String query = "Select * from facebook where UserId = (?) and Password = (?)";	
	
	@Override
	public void init() throws ServletException
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
			con = DriverManager.getConnection(url, un, pass);
			System.out.println("Connection Established");
		} 
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException
	{
		String userId = req.getParameter("email");
		String userPassword = req.getParameter("pass");
	   try {
		pstmt = con.prepareStatement(query);
		pstmt.setString(1, userId);
		pstmt.setString(2, userPassword);
	    res = pstmt.executeQuery();
    	PrintWriter out = resp.getWriter();
	    
	    if (res.next())
	     {
	    	out.println("Login of: "+res.getString(1)+" "+res.getString(2)+" "+res.getString(4)+" "+res.getString(5)+" is successfully done");
	     }
	    else {
	    	out.println("Login failed");
	    }
		}
	   
	   catch (SQLException | IOException e) {
		e.printStackTrace();
	}
	}
	
	@Override
	public void destroy()
	{
		try {
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}