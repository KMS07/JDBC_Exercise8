package Java;
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

public class Q7 {
	
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
		Properties props = getConnectionData();

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.passwd");
        int empid,salary,dept_id;
        String emp_name,job_title,dob,join_date;
        
        int choice = 0;
        //Initialize scanner
        Scanner sc = new Scanner(System.in);
        try (Connection con = DriverManager.getConnection(url, user, password);) {
        	System.out.println("Enter 1 to enter records and 0 to exit");
        	choice = sc.nextInt();
        	while(choice == 1){
        		System.out.println("Enter employee id");
        		empid = sc.nextInt();
        		System.out.println("Enter employee name");
        		sc.nextLine();
        		emp_name = sc.nextLine();
        		System.out.println("Enter job title");
        		job_title = sc.nextLine();
        		System.out.println("Enter date of birth(yyyy-mm-dd):");
        		dob = sc.nextLine();
        		System.out.println("Enter date of joining(yyyy-mm-dd):");
        		join_date = sc.nextLine();
        		System.out.println("Enter employee salary:");
        		salary = sc.nextInt();
        		System.out.println("Enter department id:");
        		dept_id = sc.nextInt();
        		
        		String template = "INSERT INTO employee values(?,?,?,?,?,?,?)";
        		PreparedStatement  ins = con.prepareStatement(template);
        		ins.setInt(1,empid);
				ins.setString(2,emp_name);
				ins.setString(3, job_title);
				ins.setDate(4, java.sql.Date.valueOf(dob));
				ins.setDate(5, java.sql.Date.valueOf(join_date));
				ins.setDouble(6,salary);
				ins.setInt(7,dept_id);
				
				ins.executeUpdate();
				System.out.println("Record entered successfully");
				System.out.println("Enter 1 to enter records and any number to exit");
				choice = sc.nextInt();	
        	}
        	con.close();
        	
        }catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Q7.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
	}

}
