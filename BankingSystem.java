import javax.swing.plaf.nimbus.State;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Manage connection to database and perform SQL statements.
 */
public class BankingSystem {
	// Connection properties
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	
	// JDBC Objects
	private static Connection con;
	private static Statement stmt=null;
	private static ResultSet rs;

	/**
	 * Initialize database connection given properties file.
	 * @param filename name of properties file
	 */
	public static void init(String filename) {
		try {
			Properties props = new Properties();						// Create a new Properties object
			FileInputStream input = new FileInputStream(filename);	// Create a new FileInputStream object using our filename parameter
			props.load(input);										// Load the file contents into the Properties object
			driver = props.getProperty("jdbc.driver");				// Load the driver
			url = props.getProperty("jdbc.url");						// Load the url
			username = props.getProperty("jdbc.username");			// Load the username
			password = props.getProperty("jdbc.password");			// Load the password
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test database connection.
	 */
	public static void testConnection() {
		System.out.println(":: TEST - CONNECTING TO DATABASE");
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			con.close();
			System.out.println(":: TEST - SUCCESSFULLY CONNECTED TO DATABASE");
			} catch (Exception e) {
				System.out.println(":: TEST - FAILED CONNECTED TO DATABASE");
				e.printStackTrace();
			}
	  }

	/**
	 * Create a new customer.
	 * @param name customer name
	 * @param gender customer gender
	 * @param age customer age
	 * @param pin customer pin
	 */
	public static void newCustomer(String name, String gender, String age, String pin) 
	{
		//DONE
		System.out.println(":: CREATE NEW CUSTOMER - RUNNING");
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			//Check for duplication
			String duplicatedCheck = "SELECT * FROM p1.customer" +
					" WHERE name = '" + name + "' AND gender = '" + gender + "' AND age =" + age + " AND pin = " + pin;
			rs = stmt.executeQuery(duplicatedCheck);
			System.out.println(duplicatedCheck);
			if(!rs.next()){
				String sql = " INSERT INTO p1.customer(name, gender, age, pin) VALUES(?,?,?,?)" ;
				PreparedStatement prestmt = con.prepareStatement(sql);
				prestmt.setString(1, name);
				prestmt.setString(2, gender);
				prestmt.setInt(3, Integer.parseInt(age));
				prestmt.setInt(4, Integer.parseInt(pin));
				prestmt.executeUpdate();
				prestmt.close();
				//Query id from customer
				sql = "SELECT id FROM p1.customer " +
						"WHERE name = '" + name + "' AND gender = '" + gender + "' AND age =" + age + " AND pin = " + pin;
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					System.out.println("Customer id: " +rs.getString("id") );    //print customer id
				}
				con.commit();
			}
			System.out.println("DONE CREATE");
			rs.close();

		}catch(SQLException sqle1){
			//Handle errors for JDBC
			sqle1.printStackTrace();
			//System.out.println(sqle1.toString());
			// If there is an error then rollback the changes.
			try{
				if(con!=null){
					con.rollback();
				}
			}catch(SQLException sqle2){
				sqle2.printStackTrace();
			}
		}catch(Exception e){
			//Handle addition exceptions
			System.out.println("e newCustomercl");
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null){
					stmt.close();
				}
			}catch(SQLException sqle3){
				sqle3.printStackTrace();
			}
			try{
				if(con!=null){
					con.close();
				}
			}catch(SQLException sqle4){
				sqle4.printStackTrace();
			}
		}
		System.out.println(":: CREATE NEW CUSTOMER - SUCCESS");
	}

	/**
	 * Open a new account.
	 * @param id customer id
	 * @param type type of account
	 * @param amount initial deposit amount
	 */
	public static void openAccount(String id, String type, String amount) 
	{
		//DONE
		System.out.println(":: OPEN ACCOUNT - RUNNING");
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			String duplicatedCheck = "SELECT * FROM p1.account" +
					" WHERE id = " +id+ " AND type = '" + type+"'" ;
			System.out.println(duplicatedCheck);
			rs = stmt.executeQuery(duplicatedCheck);
			if(!rs.next()){
				String sql = " INSERT INTO p1.account(id,balance,type,status) VALUES(?,?,?,?)" ;

				PreparedStatement prestmt = con.prepareStatement(sql);
				prestmt.setInt(1, Integer.parseInt(id));
				prestmt.setInt(2, Integer.parseInt(amount));
				prestmt.setString(3, type);
				prestmt.setString(4, "A");
				prestmt.executeUpdate();
				prestmt.close();

				sql = "SELECT number FROM p1.account"
						+" WHERE id = "+id+" AND type = '"+type+"'";
				System.out.println(sql);
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					System.out.println(rs.getInt("number"));	//print customer id
				}
				con.commit();
			}
			rs.close();
        }catch(SQLException sqle1){
			//Handle errors for JDBC
			sqle1.printStackTrace();
			// If there is an error then rollback the changes.
			try{
				if(con!=null){
					con.rollback();
				}
			}catch(SQLException sqle2){
				sqle2.printStackTrace();
			}
		}catch(Exception e){
			//Handle addition exceptions
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null){
					stmt.close();
				}
			}catch(SQLException sqle3){
				sqle3.printStackTrace();
			}
			try{
				if(con!=null){
					con.close();
				}
			}catch(SQLException sqle4){
				sqle4.printStackTrace();
			}
		}
		System.out.println(":: OPEN ACCOUNT - SUCCESS");
	}

	/**
	 * Close an account.
	 * @param accNum account number
	 */
	public static void closeAccount(String accNum) 
	{
		//DONE
		System.out.println(":: CLOSE ACCOUNT - RUNNING");
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            String sql = "UPDATE p1.account SET status = 'I', balance ="+0+" WHERE number = "+accNum;
			stmt = con.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            con.commit();
            System.out.println("Closed");
        }catch(SQLException sqle1){
			//Handle errors for JDBC
			sqle1.printStackTrace();
			// If there is an error then rollback the changes.
			try{
				if(con!=null){
					con.rollback();
				}
			}catch(SQLException sqle2){
				sqle2.printStackTrace();
			}
		}catch(Exception e){
			//Handle addition exceptions
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null){
					stmt.close();
				}
			}catch(SQLException sqle3){
				sqle3.printStackTrace();
			}
			try{
				if(con!=null){
					con.close();
				}
			}catch(SQLException sqle4){
				sqle4.printStackTrace();
			}
		}
		System.out.println(":: CLOSE ACCOUNT - SUCCESS");
	}

	/**
	 * Deposit into an account.
	 * @param accNum account number
	 * @param amount deposit amount
	 */
	public static void deposit(String accNum, String amount) 
	{
		//DONE
		System.out.println(":: DEPOSIT - RUNNING");
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            //Query current amount
			String sql = "SELECT balance FROM p1.account WHERE number ="+accNum;
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			int currentValue = 0;
			while(rs.next()){
				currentValue = rs.getInt("balance");
			}
			rs.close();
			//Deposit
			currentValue += Integer.parseInt(amount);
           	sql = "UPDATE p1.account SET balance = ? WHERE number = ?";
           	PreparedStatement prestmt = con.prepareStatement(sql);
           	prestmt.setInt(1, currentValue);
           	prestmt.setInt(2, Integer.parseInt(accNum));
			prestmt.executeUpdate();
			prestmt.close();
			System.out.println("Deposited");
            con.commit();

        }catch(SQLException sqle1){
			//Handle errors for JDBC
			sqle1.printStackTrace();
			// If there is an error then rollback the changes.
			try{
				if(con!=null){
					con.rollback();
				}
			}catch(SQLException sqle2){
				sqle2.printStackTrace();
			}
		}catch(Exception e){
			//Handle addition exceptions
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null){
					stmt.close();
				}
			}catch(SQLException sqle3){
				sqle3.printStackTrace();
			}
			try{
				if(con!=null){
					con.close();
				}
			}catch(SQLException sqle4){
				sqle4.printStackTrace();
			}
		}
		System.out.println(":: DEPOSIT - SUCCESS");
	}

	/**
	 * Withdraw from an account.
	 * @param accNum account number
	 * @param amount withdraw amount
	 */
	public static void withdraw(String accNum, String amount) 
	{
		System.out.println(":: WITHDRAW - RUNNING");
				/* insert your code here */
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			String sql = "SELECT balance FROM p1.account WHERE number ="+accNum;
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			int currentValue = Integer.parseInt(rs.toString()) - Integer.parseInt(amount);
			sql = "UPDATE p1.account SET balance ="+currentValue+" WHERE number ="+accNum;
			stmt = con.createStatement();
			stmt.executeQuery(sql);
			stmt.close();
			con.commit();
		}catch(SQLException sqle1){
			//Handle errors for JDBC
			sqle1.printStackTrace();
			// If there is an error then rollback the changes.
			try{
				if(con!=null){
					con.rollback();
				}
			}catch(SQLException sqle2){
				sqle2.printStackTrace();
			}
		}catch(Exception e){
			//Handle addition exceptions
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null){
					stmt.close();
				}
			}catch(SQLException sqle3){
				sqle3.printStackTrace();
			}
			try{
				if(con!=null){
					con.close();
				}
			}catch(SQLException sqle4){
				sqle4.printStackTrace();
			}
		}
		System.out.println(":: WITHDRAW - SUCCESS");
	}

	/**
	 * Transfer amount from source account to destination account. 
	 * @param srcAccNum source account number
	 * @param destAccNum destination account number
	 * @param amount transfer amount
	 */
	public static void transfer(String srcAccNum, String destAccNum, String amount) 
	{
		System.out.println(":: TRANSFER - RUNNING");
				/* insert your code here */
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			//Retrieve sourceAccNum balance
			String sql = "SELECT balance FROM p1.account WHERE number ="+srcAccNum;
			stmt = con.createStatement();
			int sourceBalance = Integer.parseInt(stmt.executeQuery(sql).toString());
			//Retrieve destAccNum balance
			sql = "SELECT balance FROM p1.account WHERE number ="+destAccNum;
			stmt = con.createStatement();
			int destBalance = Integer.parseInt(stmt.executeQuery(sql).toString());
			//Update sourceAccNum. Balance reduces by amount
			sql = "UPDATE p1.account SET balance ="+(sourceBalance - Integer.parseInt(amount))
					+" WHERE number ="+srcAccNum;
			stmt = con.createStatement();
			stmt.executeQuery(sql);
			//Update destAccNum. Balance increases by amount
			sql = "UPDATE p1.account SET balance ="+(destBalance + Integer.parseInt(amount))
					+" WHERE number ="+destAccNum;
			stmt = con.createStatement();
			stmt.executeQuery(sql);

			con.commit();
			stmt.close();

		}catch(SQLException sqle1){
			//Handle errors for JDBC
			sqle1.printStackTrace();
			// If there is an error then rollback the changes.
			try{
				if(con!=null){
					con.rollback();
				}
			}catch(SQLException sqle2){
				sqle2.printStackTrace();
			}
		}catch(Exception e){
			//Handle addition exceptions
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null){
					stmt.close();
				}
			}catch(SQLException sqle3){
				sqle3.printStackTrace();
			}
			try{
				if(con!=null){
					con.close();
				}
			}catch(SQLException sqle4){
				sqle4.printStackTrace();
			}
		}
		System.out.println(":: TRANSFER - SUCCESS");
	}

	/**
	 * Display account summary.
	 * @param cusID customer ID
	 */
	public static void accountSummary(String cusID) 
	{
		System.out.println(":: ACCOUNT SUMMARY - RUNNING");
				/* insert your code here */
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			String sql1 = "SELECT SUM(p1.account.balance) FROM p1.account WHERE p1.account.id ="+cusID;
			String sql = "SELECT p1.account.number, p1.account.balance "+sql1+" AS TOTAL "+
					"FROM p1.account WHERE p1.account.id ="+cusID;
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.printf("%s\t\t\t%s\n","Number", "Balance");
			String total = null;
			String number = null;
			String balance = null;
			while(rs.next()){
				number = String.valueOf(rs.getInt("number"));
				balance = String.valueOf(rs.getInt("balance"));
				total =String.valueOf(rs.getInt("total"));
				System.out.printf("%s\t\t\t%s\n",number, balance);
			}
			System.out.printf("%s\t\t\t:%s\n","Total",total);
			con.commit();
			stmt.close();

		}catch(SQLException sqle1){
			//Handle errors for JDBC
			sqle1.printStackTrace();
			// If there is an error then rollback the changes.
			try{
				if(con!=null){
					con.rollback();
				}
			}catch(SQLException sqle2){
				sqle2.printStackTrace();
			}
		}catch(Exception e){
			//Handle addition exceptions
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null){
					stmt.close();
				}
			}catch(SQLException sqle3){
				sqle3.printStackTrace();
			}
			try{
				if(con!=null){
					con.close();
				}
			}catch(SQLException sqle4){
				sqle4.printStackTrace();
			}
		}
		System.out.println(":: ACCOUNT SUMMARY - SUCCESS");
	}

	/**
	 * Display Report A - Customer Information with Total Balance in Decreasing Order.
	 */
	public static void reportA() 
	{
		System.out.println(":: REPORT A - RUNNING");
				/* insert your code here */
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            //Query the total balance of each customer
            String sql1 = "SELECT SUM(p1.account.balance) from p1.account " +
					"WHERE p1.account.id = p1.customer.id GROUP BY p1.account.id";
            //Query id, name,gender, age, total balance of each customer in descending order
            String sql = "select p1.customer.id, p1.customer.name, p1.customer.gender, p1.customer.age , " +
					sql1+" AS total FROM p1.customer ORDER BY total DESC";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.printf("%s\t%s\t%s\t%s\t%s\n","ID", "Name", "Gender", "Age", "Total");
            while(rs.next()){
                String id = String.valueOf(rs.getInt("id"));
                String name = rs.getString("name");
				String gender = rs.getString("gender");
				String age = String.valueOf(rs.getInt("age"));
				String total = String.valueOf(rs.getInt("total"));
				System.out.printf("%s\t%s\t%s\t%s\t%s\n",id, name, gender, age, total);
            }
            con.commit();
            stmt.close();
        }catch(SQLException sqle1){
            //Handle errors for JDBC
            sqle1.printStackTrace();
            // If there is an error then rollback the changes.
            try{
                if(con!=null){
                    con.rollback();
                }
            }catch(SQLException sqle2){
                sqle2.printStackTrace();
            }
        }catch(Exception e){
            //Handle addition exceptions
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null){
                    stmt.close();
                }
            }catch(SQLException sqle3){
                sqle3.printStackTrace();
            }
            try{
                if(con!=null){
                    con.close();
                }
            }catch(SQLException sqle4){
                sqle4.printStackTrace();
            }
        }
		System.out.println(":: REPORT A - SUCCESS");
	}

	/**
	 * Display Report B - Customer Information with Total Balance in Decreasing Order.
	 * @param min minimum age
	 * @param max maximum age
	 */
	public static void reportB(String min, String max) 
	{
		System.out.println(":: REPORT B - RUNNING");
				/* insert your code here */
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			String sql = "SELECT AVG(balance) AS average FROM p1.account " +
					"JOIN p1.customer ON p1.account.id = p1.customer.id WHERE p1.customer.age BETWEEN "+min+" AND "+max;
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.println("Average");
			System.out.println(rs.getInt("average"));
			con.commit();
			stmt.close();
		}catch(SQLException sqle1){
			//Handle errors for JDBC
			sqle1.printStackTrace();
			// If there is an error then rollback the changes.
			try{
				if(con!=null){
					con.rollback();
				}
			}catch(SQLException sqle2){
				sqle2.printStackTrace();
			}
		}catch(Exception e){
			//Handle addition exceptions
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null){
					stmt.close();
				}
			}catch(SQLException sqle3){
				sqle3.printStackTrace();
			}
			try{
				if(con!=null){
					con.close();
				}
			}catch(SQLException sqle4){
				sqle4.printStackTrace();
			}
		}
		System.out.println(":: REPORT B - SUCCESS");
	}
}
