package Servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDate;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet("/deptserve")
public class Q10 extends HttpServlet{
	static Scanner sc = new Scanner(System.in);
	private static final long serialVersionUID = 1L;
	
//Methods  
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher reqd = request.getRequestDispatcher("dept.html");
		reqd.include(request, response);
		String docType = "<!DOCTYPE HTML>\n";
		String title="";
		
		int deptId = Integer.parseInt(request.getParameter("deptid"));
		if( addDept(deptId ,request.getParameter("dname"),request.getParameter("dloc"))) {
				title="Tuples Inserted Successfully";
			}else {
				title = "Failed to insert tuples";
			}
		
		out.println(docType + "<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n"
				+ "<BODY BGCOLOR=\"#FDF5E6\">\n" + "<H1 ALIGN=CENTER>" + title + "</H1>\n");
		
    }
    
	
	
	public static boolean addDept(int depId, String dname, String dloc){
		String url = "jdbc:mysql://localhost:3306/Company";
		String usr = "mahendar";
		String password = "Medha@2020";
		
		try(Connection con = DriverManager.getConnection(url,usr,password);){
			
				String template = "INSERT INTO Department values(?,?,?)";
				PreparedStatement  ins = con.prepareStatement(template);
				ins.setInt(1,depId);
				ins.setString(2,dname);
				ins.setString(3, dloc);
				ins.executeUpdate();
				
				
				con.close();
				return true ;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
		
}
