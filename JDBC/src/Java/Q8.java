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
import java.util.*;
import java.text.*;
import java.time.LocalDate;



public class Q8 {
	private static Properties getConnectionData() {

        Properties props = new Properties();

        //String fileName = "src/main/resources/db.properties";
        String fileName = "/home/vboxuser/eclipse-workspace/JDBC/src/Solutions_to_e8/db.properties";

        try (FileInputStream in = new FileInputStream(fileName)) {
            props.load(in);
        } catch (IOException ex) {
            Logger lgr = Logger.getLogger(Q8.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return props;
    }

	public static void main(String[] args) {
		
		Properties props = getConnectionData();

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.passwd");
        
        Scanner sc = new Scanner(System.in);
//        String query1 = "SELECT *, DATE_FORMAT(FROM_DAYS(DATEDIFF(NOW(), dob)), '%Y') + 0 AS age "
//				+ "FROM employee where emp_join > ? and age > ?;";
		try (Connection con = DriverManager.getConnection(url, user, password);) {
			System.out.print("Enter join date (yyyy-MM-dd): ");
            String joinDatestr = sc.nextLine();
            System.out.println("enter age");
			int n = sc.nextInt();	
            
            LocalDate d = LocalDate.parse(joinDatestr);
			LocalDate current = LocalDate.now();
			String mdate = (current.getYear()-n)+"-"+"0"+current.getMonthValue()+"-"+current.getDayOfMonth();
			LocalDate updatedDate = LocalDate.parse(mdate);
			
			
			String query = "select * from employee where emp_join>'"+d+"' and dob < '"+updatedDate+"';";
           
            
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(query);
            
            while(rs.next()) {
            	System.out.printf("%d %s %s %s %s %d %d\n",rs.getInt("emp_id"),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt("salary"),rs.getInt("dept_id"));
            }
       
		}catch (SQLException e) {
			Logger lgr = Logger.getLogger(Q8.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
    	}

	}

}
