package Java;

import java.io.FileInputStream;
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


public class dept {
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
		
		
		try(Connection con = DriverManager.getConnection(url,user,password);){
			int choice = 1;
			int DeptId;
			String deptname,deptloc;
			System.out.println("Enter 1 to enter records and 0 to exit");
        	choice = sc.nextInt();
			while(choice == 1) {
				System.out.println("Enter department id");
				DeptId = sc.nextInt();
				sc.nextLine();
				System.out.println("Enter department name");
				deptname = sc.nextLine();
				System.out.println("Enter department location");
				deptloc = sc.nextLine();
				sc.nextLine();
				
				String template = "INSERT INTO department values(?,?,?)";
				PreparedStatement  ins = con.prepareStatement(template);
				ins.setInt(1,DeptId);
				ins.setString(2,deptname);
				ins.setString(3, deptloc);
				System.out.println("Record inserted successfully");
				ins.executeUpdate();
				System.out.println("Enter 1 to enter records and 0 to exit");
				choice = sc.nextInt();
			}
			con.close();
		}catch(SQLException e) {
			Logger lgr = Logger.getLogger(dept.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
		}

	}

}
