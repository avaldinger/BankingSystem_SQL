import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sql{
	
	

	public static void createDataBase(String fileName) {

		// create the file route

		String url = "jdbc:sqlite:C:/SQLite/db/" + fileName;

		// connect to the database

		try (Connection con = DriverManager.getConnection(url)) {
			if (con != null) {
				DatabaseMetaData meta = con.getMetaData();
				System.out.println("The driver name is: " + meta.getDriverName());

				System.out.println("A new database has been created.");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("The database has been created with the following path: " + url);

	}

	public static void CreateTable() {

		// set route to your database

		String url = "jdbc:sqlite:C:/SQLite/db/BankingSystem.db";

		// create table + set up fields and their features

		String sql = "CREATE TABLE IF NOT EXISTS BankingSystem (\n" + "account_id integer PRIMARY KEY, \n"
				+ "name text NOT NULL, \n " 
				+ "account_name text NOT NULL, \n"
				+ "balance real, \n "
				+ "password text NOT NULL \n" + ");";

		// connect to the database

		try (Connection conn = DriverManager.getConnection(url);

				Statement stm = conn.createStatement()) {

			stm.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("The table has been created.");

	}
	
	static Connection connectDatabase() {

		// route to the database
		String url = "jdbc:sqlite:C:/SQLite/db/BankingSystem.db";

		// connection object to connect to the database
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Connection has been established.");
		// "return' the connection
		return conn;

	}
	
	
	public void InsertTable(int account_id, String name, String account_name, int balance, String password) {

		//SQL statement 
		String sql = "INSERT INTO BankingSystem(account_id, name, account_name, balance, password) VALUES(?,?,?,?,?)";

		try (Connection conn = this.connectDatabase();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
			
			// adding the information to the fields
			pstm.setInt(1, account_id);
			pstm.setString(2, name);
			pstm.setString(3, account_name);
			pstm.setInt(4, balance);
			pstm.setString(5, password);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Mr./Miss." + name + " has been inserted into the BankingSystem table.");

	}
	
	
	public void SelectName(String name) {
		
		String sql = "SELECT * FROM BankingSystem where name = ?";
				
		Connection conn = this.connectDatabase();
		try (
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, name);
			
			ResultSet rst = pstmt.executeQuery();
			
			while(rst.next()) {
				//System.out.println(rst.getInt("id") + "\t" + rst.getString("name") + "\t");
									
			}
			}
			
			
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

	
	public static void selectById(Connection conn, int id) {
		
		String sql = "SELECT * FROM warehouses where id = ?";
				
		
		try (
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setInt(1, id);
			
			ResultSet rst = pstmt.executeQuery();
			
			
				System.out.println(rst.getString("name"));
									
			
			}
			
			
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		



public static void updatePassword(Connection conn, String password, int id) {

	String sql = "UPDATE warehouses SET password = ?" + " WHERE id = ?";

	try (PreparedStatement prsmt = conn.prepareStatement(sql)) {

		prsmt.setString(1, password);
		prsmt.setInt(2, id);
		prsmt.executeUpdate();

		System.out.println("Your password has been updated \n");

	}

	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
}