package Java;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.*;

public class Q9 {
	private static Properties getConnectionData() {

        Properties props = new Properties();

        //String fileName = "src/main/resources/db.properties";
        String fileName = "/home/vboxuser/eclipse-workspace/JDBC/src/Solutions_to_e8/db.properties";

        try (FileInputStream in = new FileInputStream(fileName)) {
            props.load(in);
        } catch (IOException ex) {
            Logger lgr = Logger.getLogger(Q7.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return props;
    }
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Properties props = getConnectionData();
		
		String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.passwd");
        String query ="";
		try(Connection con = DriverManager.getConnection(url,user,password);){
				String template = "INSERT INTO department values(?,?,?)";
				PreparedStatement  ins = con.prepareStatement(template);
				ins.setInt(1,4);
				ins.setString(2,"Management");
				ins.setString(3, "UoH");
				ins.executeUpdate();
				
				try{
					con.setAutoCommit(false);
					query = "select * from employee order by emp_join limit 3;";
					Statement s = con.createStatement();
					ResultSet rs = s.executeQuery(query);
					
					// updates the department one after the other
					while (rs.next()) {
						int id =rs.getInt(1);							
						String q1 = "update employee set dept_id = "+4+" where emp_id = "+id+";";
						Statement s1 = con.createStatement();
						s1.executeUpdate(q1);
					}
					con.commit();
					System.out.println("Employees data updated successfully");
				}catch(SQLException ex) {
					try {
						 con.rollback();
						 System.out.println("Couldnt update the records!!");
					 }catch(SQLException ex1) {
						 Logger lgr = Logger.getLogger(Q9.class.getName());
		        		lgr.log(Level.WARNING, ex1.getMessage(), ex1);
					}
				}
				con.close();
		}catch(SQLException e) {
			Logger lgr = Logger.getLogger(Q9.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
		}

	}

}
