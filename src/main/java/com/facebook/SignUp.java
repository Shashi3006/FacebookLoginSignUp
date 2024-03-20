package com.facebook;

import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUp extends HttpServlet
{
	Connection con = null;
	PreparedStatement pstmt = null;
	String url="jdbc:mysql://localhost:3306/jdbc_test";
	String un = "root";
	String password = "Shashi@3006";
	String query = "insert into facebook values(?,?,?,?,?)";
	
	@Override
	public void init() throws ServletException
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, un, password);
		}
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
		try {
			pstmt = con.prepareStatement(query);
			
			String name = req.getParameter("fs");
			String lname = req.getParameter("ls");
			String uid = req.getParameter("email");
			String pass = req.getParameter("pass");
			String dob = req.getParameter("dob");
			String gender = req.getParameter("gender");
			
			System.out.println(name+" "+lname+" "+uid+" "+pass+" "+dob+" "+gender);
			
			pstmt.setString(1, name+" "+lname);
			pstmt.setString(2, uid);
			pstmt.setString(3, pass);
			pstmt.setString(4, dob);
			pstmt.setString(5, gender);
			pstmt.executeUpdate();
			
			PrintWriter out = resp.getWriter();
			out.println("Successfully created an acount");
		} 
		catch (SQLException e) {
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
