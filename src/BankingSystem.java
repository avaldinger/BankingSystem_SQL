import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingSystem {

	public static void main(String[] args)
			throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {

		// create an instance of the Sql class
		Sql table = new Sql();

		// variable to connect to the database
		Connection connect = table.connectDatabase();

		// create the database if it doesn't exists
		Sql.createDataBase("BankingSystem");
		// create the table if it doesn't exists
		table.CreateTable();

		BankingSystem bs = new BankingSystem();

		bs.createAccount("Daniel Craig", 6500, "Adult");
		bs.createAccount("John Doe", 8000, "Adult");

		System.out.println();

		boolean quit = false;
		do {
			System.out.println("1. Display Account Details");
			System.out.println("2. Deposit money");
			System.out.println("3. Withdraw money");
			System.out.println("4. Check balance");
			System.out.println("5. Change password");
			System.out.println("0. to quit: \n");
			System.out.print("Please enter your the number of the chosen function: ");
			Scanner number = new Scanner(System.in);
			try {
				int userChoice = number.nextInt();
				switch (userChoice) {
				case 1:
					System.out.println("Please enter your account number: ");
					try {
						int accn = number.nextInt();
						System.out.println("Please enter your password: ");
						String pw = number.next();
						bs.checkIdPassword(connect, accn, pw);
					} catch (InputMismatchException e) {
						System.out.println("Input Mismatch! Please enter Numbers\n");

					}
					break;
				case 2:
					System.out.println("Please enter your account number: ");
					try {
						int accn = number.nextInt();
						System.out.println("Please enter your password: ");
						String pw = number.next();
						System.out.println("Please enter the amount you would like to deposit: ");
						int balance = number.nextInt();
						DepositMoney(connect, accn, pw, balance);
					} catch (InputMismatchException e) {
						System.out.println("Input Mismatch! Please enter Numbers\n");

					}
					break;

				case 3:
					System.out.println("Please enter your account number: ");
					try {
						int accn = number.nextInt();
						System.out.println("Please enter your password: ");
						String pw = number.next();
						System.out.println("Please enter the amount you would like to withdraw: ");
						int balance = number.nextInt();
						withDrawMoney(connect, accn, pw, balance);
					} catch (InputMismatchException e) {
						System.out.println("\n Input Mismatch! Please enter Numbers\n");

					}

					break;

				case 4:
					System.out.println("Please enter your account number: ");
					try {
						int accn = number.nextInt();
						System.out.println("Please enter your password: ");
						String pw = number.next();
						displayAccountBalance(connect, accn, pw);
					} catch (InputMismatchException e) {
						System.out.println("\n Input Mismatch! Please enter Numbers\n");

					}
					break;

				case 5:
					System.out.println("Please enter your account number: ");
					try {
						int accn = number.nextInt();
						System.out.println("Please enter your password: ");
						String pw = number.next();
						first: {
							System.out.println("Please enter your new password (minimum 6, maximum 10 character): ");
							String newpw = number.next();
							while (newpw.length() < 6 || newpw.length() > 10) {
								System.out.println("Incorrecnt length. \n");
								break first;
							} {
								ChangePassword(connect, accn, pw, newpw);
							}
						}

					} catch (InputMismatchException e) {
						System.out.println("\n Input Mismatch! Please enter Numbers\n");
					}
					break;

				case 0:
					System.out.println("Goodbye!");
					quit = true;
					break;

				default:
					System.out.println("Unknown choice, try again");
				}
			} catch (InputMismatchException e) {
				System.out.println("Input Mismatch! Please enter Numbers\n");

			}
		} while (!quit);

		connect.close();

	}

	public void checkIdPassword(Connection conn, int id, String password) throws SQLException {

		String sql = "SELECT count(*) AS count FROM BankingSystem where account_id = ? AND password = ?";

		ResultSet rst = null;

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			pstmt.setString(2, password);
			rst = pstmt.executeQuery();

			// while(rst.next());

			if (rst.getInt("count") == 0) {
				System.out.println("The Id you entered and password: " + id + " and/or " + password + " are invalid.");

			} else {
				BankingSystem bs1 = new BankingSystem();

				bs1.selectAll(id);

			}
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void selectAll(int id) {

		String sql = "SELECT * FROM BankingSystem where account_id = ?";

		ResultSet rst = null;

		Connection conn = this.ConnectDatabase();
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			rst = pstmt.executeQuery();

			System.out.println(rst.getInt("account_id") + "\t" + rst.getString("name") + "\t"
					+ rst.getString("account_name") + "\t" + rst.getInt("balance") + "\t");

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void checkAccountNumber(int idToSearch, String pwToSearch) throws SQLException {
		boolean exists = false;

		/*
		 * if(idToSearch, pwToSearch == checkIdPassword(idToSearch, pwToSearch)) {
		 * selectAll(idToSearch);
		 * 
		 * exists = true; }
		 */

		if (exists == false) {
			System.out.println(
					"The Id you entered and password: " + idToSearch + " and/or " + pwToSearch + " are invalid.");
		}
	}

	private static void displayAccountBalance(Connection conn, int idToSearch, String pwToSearch) {

		String sql = "SELECT count(*) AS count FROM BankingSystem where account_id = ? AND password = ?";

		ResultSet rst = null;

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, idToSearch);
			pstmt.setString(2, pwToSearch);
			rst = pstmt.executeQuery();

			// while(rst.next());

			if (rst.getInt("count") == 0) {
				System.out.println(
						"The Id you entered and password: " + idToSearch + " and/or " + pwToSearch + " are invalid.");

			} else {
				BankingSystem bs1 = new BankingSystem();

				bs1.selectBalance(conn, idToSearch);

			}
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void selectBalance(Connection conn, int id) {

		String sql = "SELECT name, balance FROM BankingSystem where account_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			ResultSet rst = pstmt.executeQuery();

			System.out
					.println("Your balance Mr./Mrss. " + rst.getString("name") + "is: " + rst.getInt("balance") + ".");

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateDeposit(Connection conn, int balance, int id) {

		String sql = "UPDATE BankingSystem SET balance = balance + ?" + " WHERE account_id = ?";

		try (PreparedStatement prsmt = conn.prepareStatement(sql)) {

			prsmt.setInt(1, balance);
			prsmt.setInt(2, id);
			prsmt.executeUpdate();

			System.out.println("Your balance has been updated. \n");

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void updateWithdraw(Connection conn, int balance, int id) {

		String sql = "UPDATE BankingSystem SET balance = balance - ?" + " WHERE account_id = ?";

		try (PreparedStatement prsmt = conn.prepareStatement(sql)) {

			prsmt.setInt(1, balance);
			prsmt.setInt(2, id);
			prsmt.executeUpdate();

			System.out.println("Your balance has been updated. \n");

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int checkBalance(Connection conn, int id) throws SQLException {

		String sql = "SELECT balance FROM BankingSystem where account_id = ?";
		
		ResultSet rst = null;

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			rst = pstmt.executeQuery();

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rst.getInt("balance");
	}

	private static void withDrawMoney(Connection conn, int idToSearch, String pwToSearch, int balance) {

		String sql = "SELECT count(*) AS count FROM BankingSystem where account_id = ? AND password = ?";

		ResultSet rst = null;

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, idToSearch);
			pstmt.setString(2, pwToSearch);
			rst = pstmt.executeQuery();

			// while(rst.next());

			if (rst.getInt("count") == 0) {
				System.out.println(
						"The Id you entered and password: " + idToSearch + " and/or " + pwToSearch + " are invalid.");

			} else {
			
				boolean possible = false;
				//updateWithdraw(conn, balance, idToSearch);
				
				/*if(balance > checkBalance(conn, idToSearch) ) {
					possible = true;
				updateWithdraw(conn, balance, idToSearch);
				
				}
				else {System.out.println("You don't have sufficient money on your account. Please enter a new amount.");
					
				}*/

			}
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void DepositMoney(Connection conn, int idToSearch, String pwToSearch, int balance) {

		String sql = "SELECT count(*) AS count FROM BankingSystem where account_id = ? AND password = ?";

		ResultSet rst = null;

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, idToSearch);
			pstmt.setString(2, pwToSearch);
			rst = pstmt.executeQuery();

			if (rst.getInt("count") == 0) {
				System.out.println(
						"The Id you entered and password: " + idToSearch + " and/or " + pwToSearch + " are invalid.");

			} else {
				updateDeposit(conn, balance, idToSearch);

			}
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void ChangePassword(Connection conn, int idToSearch, String pwToSearch, String newPassword) {

		String sql = "SELECT count(*) AS count FROM BankingSystem where account_id = ? AND password = ?";

		ResultSet rst = null;

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, idToSearch);
			pstmt.setString(2, pwToSearch);
			rst = pstmt.executeQuery();

			if (rst.getInt("count") == 0) {
				System.out.println(
						"The Id you entered and password: " + idToSearch + " and/or " + pwToSearch + " are invalid.");

			} else {
				if (newPassword.length() >= 6 && newPassword.length() <= 10) {

					updatePassword(conn, newPassword, idToSearch);
				}

				else {
					System.out.println(
							"The password you entered: " + "'" + newPassword + "'" + " doesn't match the criteria.");
				}
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void updatePassword(Connection conn, String password, int id) {

		String sql = "UPDATE BankingSystem SET password = ?" + " WHERE account_id = ?";

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

	private Connection ConnectDatabase() {

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

	public boolean userExists(String name) {

		String sql = "SELECT count(name) AS count FROM BankingSystem where name = ?";

		boolean exists = false;

		Connection conn = this.ConnectDatabase();
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, name);

			ResultSet rst = pstmt.executeQuery();

			if (rst.getInt("count") == 0) {
				exists = false;
			} else {
				exists = true;
			}
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return exists;

	}

	public void insertTable(int account_id, String name, String account_name, int balance, String password) {

		// SQL statement
		String sql = "INSERT INTO BankingSystem(account_id, name, account_name, balance, password) VALUES(?,?,?,?,?)";

		try (Connection conn = this.ConnectDatabase(); PreparedStatement pstm = conn.prepareStatement(sql)) {

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

		System.out.println("Mr./Miss. " + name + " has been inserted into the BankingSystem table. \n");

	}

	private void createAccount(String inputName, int initialBalance, String inputAccountName) {

		// Check if user already exists in the file
		boolean exists = false;

		if (userExists(inputName) == true) {
			System.out.println("ERROR: User already exists\n");
		}

		else {

			Users newAccount = new Users(inputName, initialBalance, inputAccountName);

			System.out.println("New user created with ID: " + newAccount.id + "\t pw: " + newAccount.password + " ("
					+ inputName + ")\n");

			insertTable(newAccount.getId(), newAccount.getName(), newAccount.getAccount_name(), newAccount.getBalance(),
					newAccount.getPassword());

		}

	}
}
