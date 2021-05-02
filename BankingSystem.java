

import javax.swing.plaf.nimbus.State;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Manage connection to database and perform SQL statements.
 */
public class BankingSystem {
    // Connection properties
    private static String driver = "com.ibm.db2.jcc.DB2Driver";
    private static String url = "jdbc:db2://192.168.86.61:50000/P1";
    private static String username = "db2inst1";
    private static String password = "030698";


//	jdbc.driver="com.ibm.db2.jcc.DB2Driver"
//	jdbc.url=jdbc:db2://172.31.96.1:50000/P1
//	jdbc.username=db2inst1
//	jdbc.password=030698


    // JDBC Objects
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    /**
     * Initialize database connection given properties file.
     *
     * @param filename name of properties file
     */
    public static void init(String filename) {
        try {
            Properties props = new Properties();                        // Create a new Properties object
            FileInputStream input = new FileInputStream(filename);    // Create a new FileInputStream object using our filename parameter
            props.load(input);                                        // Load the file contents into the Properties object
            driver = props.getProperty("jdbc.driver");                // Load the driver

            url = props.getProperty("jdbc.url");                        // Load the url

            username = props.getProperty("jdbc.username");            // Load the username

            password = props.getProperty("jdbc.password");            // Load the password
            System.out.println(driver);
            System.out.println(url);
            System.out.println(username);
            System.out.println(password);
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
     *
     * @param name   customer name
     * @param gender customer gender
     * @param age    customer age
     * @param pin    customer pin
     */
    public static void newCustomer(String name, String gender, String age, String pin) {
        System.out.println(":: CREATE NEW CUSTOMER - RUNNING");
        newCustomerHelper(name,gender, age, pin);
    }

    /**
     * Open a new account.
     *
     * @param id     customer id
     * @param type   type of account
     * @param amount initial deposit amount
     */
    public static void openAccount(String id, String type, String amount) {
        //DONE
        System.out.println(":: OPEN ACCOUNT - RUNNING");
        int accountNumber = openAccountHelper(id, type, amount);
        System.out.println(":: ACCOUNT NUMBER: "+accountNumber);
    }

    /**
     * Close an account.
     *
     * @param accNum account number
     */
    public static void closeAccount(String accNum) {
        //DONE
        System.out.println(":: CLOSE ACCOUNT - RUNNING");
        try {
            int accNumValue = Integer.parseInt(accNum);
            con = DriverManager.getConnection(url, username, password);
            //Update account into inactive and 0 out the balance
            String sql = "UPDATE p1.account SET status = 'I', balance =" + 0 + " WHERE number = " + accNumValue;
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            //Clean up
            stmt.close();
            con.close();
            System.out.println(":: CLOSE ACCOUNT - SUCCESS");
        } catch (Exception e) {
            //Handle invalid input
            if (e.getMessage().contains(accNum)) {
                System.out.println(":: CLOSE ACCOUNT - ERROR - INVALID ACCOUNT NUMBER");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Deposit into an account.
     *
     * @param accNum account number
     * @param amount deposit amount
     */
    public static void deposit(String accNum, String amount) {
        //DONE
        System.out.println(":: DEPOSIT - RUNNING");
        try {
            int amountValue = Integer.parseInt(amount);
            int accNumValue = Integer.parseInt(accNum);
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            //Query current amount
            String sql = "SELECT balance FROM p1.account WHERE number =" + accNumValue;
            rs = stmt.executeQuery(sql);
            int currentValue = 0;
            while (rs.next()) {
                currentValue = rs.getInt("balance");
            }
            //Deposit
            currentValue += amountValue;
            System.out.println(currentValue);
            sql = "UPDATE p1.account SET balance = " + currentValue + " WHERE number = " + accNumValue;
            stmt.execute(sql);
            //Clean up
            stmt.close();
            rs.close();
            con.close();
            System.out.println(":: DEPOSIT - SUCCESS");
        } catch (Exception e) {
            //Handle invalid input
            if (e.getMessage().contains(accNum)) {
                System.out.println(":: DEPOSIT - ERROR - INVALID ACCOUNT NUMBER");
            } else if (e.getMessage().contains(amount)) {
                System.out.println(":: DEPOSIT - ERROR - INVALID AMOUNT");
            }
        }
    }

    /**
     * Withdraw from an account.
     *
     * @param accNum account number
     * @param amount withdraw amount
     */
    public static void withdraw(String accNum, String amount) {
        //DONE
        System.out.println(":: WITHDRAW - RUNNING");
        try {
            int amountValue = Integer.parseInt(amount);
            int accNumValue = Integer.parseInt(accNum);
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            //Get current balance
            String sql = "SELECT balance FROM p1.account WHERE number = " + accNumValue;
            rs = stmt.executeQuery(sql);
            int currentValue = 0;
            while (rs.next()) {
                currentValue = rs.getInt("balance");
                break;
            }
            currentValue -= amountValue;
            if (currentValue < 0) {
                System.out.println(":: WITHDRAW - ERROR - NOT ENOUGH FUND");
            } else {
                sql = "UPDATE p1.account SET balance =" + currentValue + " WHERE number =" + accNumValue;
                stmt.execute(sql);
                System.out.println(":: WITHDRAW - SUCCESS");
            }
            //Clean up
            stmt.close();
            rs.close();
            con.close();
        } catch (Exception e) {
            //Handle addition exceptions
            if (e.getMessage().contains(accNum)) {
                System.out.println(":: WITHDRAW - ERROR - INVALID ACCOUNT NUMBER");
            }
            if (e.getMessage().contains(amount)) {
                System.out.println(":: WITHDRAW - ERROR - INVALID AMOUNT");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Transfer amount from source account to destination account.
     *
     * @param srcAccNum  source account number
     * @param destAccNum destination account number
     * @param amount     transfer amount
     */
    public static void transfer(String srcAccNum, String destAccNum, String amount) {
        //DONE
        System.out.println(":: TRANSFER - RUNNING");
        try {
            int srcAcc = Integer.parseInt(srcAccNum);
            int destAcc = Integer.parseInt(destAccNum);
            int amountValue = Integer.parseInt(amount);
            int sourceBalance = 0;
            int destBalance = 0;
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            //Retrieve sourceAccNum balance
            String sql = "SELECT balance FROM p1.account WHERE number =" + srcAcc;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                sourceBalance = rs.getInt("balance");
                break;
            }
            sourceBalance -= amountValue;
            if (sourceBalance < 0) {
                System.out.println(":: TRANSFER - ERROR - NOT ENOUGH FUND");
            } else {
                //Retrieve destAccNum balance
                sql = "SELECT balance FROM p1.account WHERE number =" + destAcc;
                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    destBalance = rs.getInt("balance");
                    break;
                }
                destBalance += amountValue;
                //Update sourceAccNum. Balance reduces by amount
                sql = "UPDATE p1.account SET balance =" + sourceBalance
                        + " WHERE number =" + srcAcc;
                stmt.execute(sql);
                //Update destAccNum. Balance increases by amount
                sql = "UPDATE p1.account SET balance =" + destBalance
                        + " WHERE number =" + destAcc;
                stmt.execute(sql);
                System.out.println(":: TRANSFER - SUCCESS");
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            //Handle addition exceptions
            if (e.getMessage().contains(srcAccNum)) {
                System.out.println(":: TRANSFER - ERROR - INVALID SOURCE ACCOUNT NUMBER");
            }
            if (e.getMessage().contains(destAccNum)) {
                System.out.println(":: TRANSFER - ERROR - INVALID DESTINATION ACCOUNT NUMBER");
            }
            if (e.getMessage().contains(amount)) {
                System.out.println(":: TRANSFER - ERROR - INVALID AMOUNT");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Display account summary.
     *
     * @param cusID customer ID
     */
    public static void accountSummary(String cusID) {
        System.out.println(":: ACCOUNT SUMMARY - RUNNING");
        /* insert your code here */
        try {
            int idValue = Integer.parseInt(cusID);
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            //Query account number, balance that's active
            String sql = "SELECT p1.account.number, p1.account.balance " +
                    "FROM p1.account WHERE p1.account.status = 'A' AND p1.account.id = " + idValue;
            rs = stmt.executeQuery(sql);
            System.out.printf("%s\t\t\t%s\n", "Number", "Balance");

            int total = 0;
            while (rs.next()) {
                int number = rs.getInt(1);
                int balance = rs.getInt(2);
                total += balance;
                System.out.printf("   %s\t\t\t%s\n", number, balance);
            }
            System.out.printf("   %s\t\t\t:%s\n", "Total", total);

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            //Handle addition exceptions
            e.printStackTrace();
            if (e.getMessage().contains(cusID)) {
                System.out.println(":: SUMMARY - ERROR - INVALID CUSTOMER ID");
            } else {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(":: ACCOUNT SUMMARY - SUCCESS");
    }

    /**
     * Display Report A - Customer Information with Total Balance in Decreasing Order.
     */
    public static void reportA() {
        System.out.println(":: REPORT A - RUNNING");
        try {
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            //Query the total balance of each customer
            String sql = "select c.id, name, age, gender, totalOfBalances from p1.customer as c " +
                    "join (select id, sum(balance) as totalOfBalances from p1.account group by id) " +
                    "as a on a.id = c.id ORDER BY totalOfBalances DESC";

            rs = stmt.executeQuery(sql);
            System.out.printf("%s\t%s\t%s\t%s\t%s\n", "ID", "Name", "Gender", "Age", "Total");
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String age = String.valueOf(rs.getInt("age"));
                String total = String.valueOf(rs.getInt("totalOfBalances"));
                System.out.printf("%s\t%s\t%s\t%s\t%s\n", id, name, gender, age, total);
            }
            rs.close();
            stmt.close();
            con.close();
            System.out.println(":: REPORT A - SUCCESS");
        } catch (SQLException sqle1) {
            //Handle errors for JDBC
            sqle1.printStackTrace();
        }
    }

    /**
     * Display Report B - Customer Information with Total Balance in Decreasing Order.
     *
     * @param min minimum age
     * @param max maximum age
     */
    public static void reportB(String min, String max) {
        System.out.println(":: REPORT B - RUNNING");
        try {
            int minValue = Integer.parseInt(min);
            int maxValue = Integer.parseInt(max);
            con = DriverManager.getConnection(url, username, password);
            String sql = "SELECT avg(total) as average FROM ((SELECT id, sum(balance) AS total FROM p1.account GROUP BY id)"
                    + "AS a JOIN (SELECT id, age FROM p1.customer WHERE age BETWEEN " + minValue + " AND " + maxValue +
                    " ) AS c ON a.id = c.id)";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println("Average\n----------");
            while (rs.next()) {
                System.out.println(rs.getInt("average"));
                break;
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            //Handle addition exceptions
            if (e.getMessage().contains(min)) {
                System.out.println(":: REPORT B - ERROR - INVALID MIN BOUND");
            }
            if (e.getMessage().contains(max)) {
                System.out.println(":: REPORT B - ERROR - INVALID MAX BOUND");
            } else {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(":: REPORT B - SUCCESS");
    }


    /*******************************HELPER METHODS*******************************/

    public static void createCustomerDB() {
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            String sql = "create table IF NOT EXISTS p1.customer " +
                    "(id integer not null generated by default as identity (start WITH 100, increment by 1, no cache)," +
                    " name    varchar(15) not null," +
                    " gender  char        not null check (gender = 'M' or gender = 'F')," +
                    " age     integer     not null check (age >= 0)," +
                    " pin     integer     not null check (pin >= 0)," +
                    " constraint pk_id primary key (id) )";
            stmt.execute(sql);
            stmt.close();
            con.close();
            System.out.println("Done creating customer DB");
        } catch (Exception e) {
            System.out.println("In exception");
            System.out.println(e.getMessage());
        }
    }

    public static void createAccountDB() {
        try {

            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            String sql = "create table IF NOT EXISTS p1.account ( " +
                    "number  integer     not null generated by default as identity (start WITH 1000, increment by 1, no cache)," +
                    " id      integer     not null," +
                    " balance integer     not null check (balance >= 0)," +
                    " type    char        not null check (type = 'C' or type = 'S')," +
                    " status  char        not null check (status = 'A' or status = 'I')," +
                    "  constraint pk_number primary key (number)," +
                    " constraint fk_id foreign key (id) references p1.customer(id) on delete cascade)";
            stmt.execute(sql);
            stmt.close();
            con.close();
            System.out.println("Done creating account DB");
        } catch (Exception e) {
            System.out.println("In exception");
            System.out.println(e.getMessage());
        }
    }

    public static String newCustomerHelper(String name, String gender, String age, String pin){
        String cusID = "";
        try {
            if(notfoundCustomer(name,gender,age, pin)){
                int pinValue = Integer.parseInt(pin);
                int ageValue = Integer.parseInt(age);
                con = DriverManager.getConnection(url, username, password);
                stmt = con.createStatement();
                //Insert new customer
                String sql = "INSERT INTO p1.customer (name, gender, age, pin) " + "VALUES ('" + name + "', '" + gender
                        + "', " + ageValue + ", " + pinValue + ")";
                System.out.println(sql);
                stmt.execute(sql);
                //Query id from customer
                sql = "SELECT id FROM p1.customer WHERE name = '" + name + "' " +
                        "AND gender = '" + gender + "' AND age =" + ageValue + " AND pin = " + pinValue;
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    cusID = rs.getString("id");
                    System.out.println(":: Customer id: " + cusID);
                    break;
                }
                //Clean up
                rs.close();
                stmt.close();
                con.close();
                System.out.println(":: CUSTOMER_ID: "+cusID);
            }else{
                System.out.println(":: CUSTOMER  EXISTS");
            }
        } catch (Exception e) {
            //Handle invalid input
            if (e.getMessage().contains(name)) {
                System.err.println(":: CREATE NEW CUSTOMER - ERROR - INVALID NAME");
            }
            if (e.getMessage().contains(gender)) {
                System.err.println(":: CREATE NEW CUSTOMER - ERROR - INVALID GENDER");
            }
            if (e.getMessage().contains(age)) {
                System.err.println(":: CREATE NEW CUSTOMER - ERROR - INVALID AGE");
            }
            if (e.getMessage().contains(pin)) {
                System.err.println(":: CREATE NEW CUSTOMER - ERROR - INVALID PIN");
            } else {
                System.err.println(e.getMessage());
            }
        }
        return cusID;
    }

    //Check for duplication based on name, gender, age
    public static boolean notfoundCustomer(String name, String gender, String age, String pin) {
        boolean notfound = true;
        try {
            int pinValue = Integer.parseInt(pin);
            int ageValue = Integer.parseInt(age);
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            String sql = "SELECT * FROM p1.customer " +
                    "WHERE name= '" + name + "' AND gender= '" + gender + "' AND age= " + ageValue + " AND pin= " + pinValue;
            //System.out.println(sql);
            rs = stmt.executeQuery(sql);
            notfound = !rs.next();
            rs.close();
            stmt.close();
            con.close();
            return notfound;
        } catch (Exception e) {
            //Handle invalid input
            if (e.getMessage().contains(name)) {
                System.err.println(":: INVALID INPUT - ERROR - INVALID NAME");
            }
            if (e.getMessage().contains(gender)) {
                System.err.println(":: INVALID INPUT - ERROR - INVALID GENDER");
            }
            if (e.getMessage().contains(age)) {
                System.err.println(":: INVALID INPUT - ERROR - INVALID AGE");
            }
            if (e.getMessage().contains(pin)) {
                System.err.println(":: INVALID INPUT - ERROR - INVALID PIN");
            } else {
                System.err.println(e.getMessage());
            }
        }
        return notfound;
    }

    //Check if customer and pin are in the system
    public static boolean notfoundCustomer(String cusId, String pin) {
        boolean notfound = true;
        try {
            int pinValue = Integer.parseInt(pin);
            int idValue = Integer.parseInt(cusId);
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            String sql = "SELECT * FROM p1.customer WHERE id= " + idValue + " AND pin= " + pinValue;
            //System.out.println(sql);
            rs = stmt.executeQuery(sql);
            notfound = !rs.next();
            if(!notfound){
                System.out.println("Login Successfully!");
            }else{
                System.out.println("Invalid ID or PIN! Please try again");
            }
            rs.close();
            stmt.close();
            con.close();
            return notfound;
        } catch (Exception e) {
            //Handle invalid input
            if (e.getMessage().contains(cusId)) {
                System.out.println(":: INVALID INPUT - ERROR - INVALID ID");
            }
            if (e.getMessage().contains(pin)) {
                System.out.println(":: INVALID INPUT - ERROR - INVALID PIN");
            } else {
                System.out.println(e.getMessage());
            }
        }
        return notfound;
    }

    public static boolean notfoundCustomer(String cusId) {
        boolean notfound = true;
        try {
            int idValue = Integer.parseInt(cusId);
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            String sql = "SELECT * FROM p1.customer WHERE id= " + idValue ;
            rs = stmt.executeQuery(sql);
            notfound = !rs.next();
            rs.close();
            stmt.close();
            con.close();
            return notfound;
        } catch (Exception e) {
            //Handle invalid input
            if (e.getMessage().contains(cusId)) {
                System.out.println(":: INVALID INPUT - ERROR - INVALID ID");
            }
            else {
                System.out.println(e.getMessage());
            }
        }
        return notfound;
    }

    public static int openAccountHelper(String id, String type, String amount) {
        int accountNumber = 0;
        try {
            int amountValue = Integer.parseInt(amount);
            int idValue = Integer.parseInt(id);
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            //Insert account
            String sql = "INSERT INTO p1.account (id, balance, type, status) " +
                    "VALUES (" + idValue + ", " + amountValue + ", '" + type + "', 'A')";
            stmt.execute(sql);
            //Query account number
            sql = "SELECT number FROM p1.account"
                    + " WHERE id = " + idValue + " AND type = '" + type + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                accountNumber = rs.getInt("number");    //print customer id
                break;
            }
            //Clean up
            rs.close();
            stmt.close();
            con.close();
            System.out.println(":: OPEN ACCOUNT - SUCCESS");
            return accountNumber;
        } catch (Exception e) {
            //Handle invalid input
            if (e.getMessage().contains(id)) {
                System.out.println(":: OPEN ACCOUNT - ERROR - INVALID ID");
            }
            if (e.getMessage().contains(amount)) {
                System.out.println(":: OPEN ACCOUNT - ERROR - INVALID AMOUNT");
            }
            if (e.getMessage().contains(type)) {
                System.out.println(":: OPEN ACCOUNT - ERROR - INVALID TYPE");
            } else {
                System.out.println(e.getMessage());
            }
        }
        return accountNumber;
    }

    //Helper for deposit, transfer, withdraw
    public static boolean notfoundAccount(String accNum, String cusID){
        boolean notfound = true;
        try {
            int accNumValue = Integer.parseInt(accNum);
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            String sql = "SELECT * FROM p1.account WHERE number= " + accNumValue+" AND id ="+ cusID ;
            rs = stmt.executeQuery(sql);
            notfound = !rs.next();
            rs.close();
            stmt.close();
            con.close();
            return notfound;
        } catch (Exception e) {
            //Handle invalid input
            if (e.getMessage().contains(accNum)) {
                System.out.println(":: INVALID INPUT - ERROR - INVALID NUMBER");
            } else {
                System.out.println(e.getMessage());
            }
        }
        return notfound;
    }

    //Helper for deposit, transfer, withdraw
    public static boolean notfoundAccount(String accNum){
        boolean notfound = true;
        try {
            int accNumValue = Integer.parseInt(accNum);
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            String sql = "SELECT * FROM p1.account WHERE number= " + accNumValue ;
            rs = stmt.executeQuery(sql);
            notfound = !rs.next();
            rs.close();
            stmt.close();
            con.close();
            return notfound;
        } catch (Exception e) {
            //Handle invalid input
            if (e.getMessage().contains(accNum)) {
                System.out.println(":: INVALID INPUT - ERROR - INVALID NUMBER");
            } else {
                System.out.println(e.getMessage());
            }
        }
        return notfound;
    }
}
