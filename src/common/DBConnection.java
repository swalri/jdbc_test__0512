package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {
	public static Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("연결중");
		String id = "java", pwd = "1234", url = "jdbc:oracle:thin:localhost:1521/xe";
		Connection con = DriverManager.getConnection(url, id, pwd);
		System.out.println("성공");
		return con;
	}
}
