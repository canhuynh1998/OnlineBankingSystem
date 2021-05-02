import java.util.*;

public class cmd {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        //BankingSystem.init(args[0]);
        BankingSystem.testConnection();
        BankingSystem.createCustomerDB();
        BankingSystem.createAccountDB();
        //openAccount("138");
        menu(1);
        int option = in.nextInt();
        if (option == 1) {
            System.out.println("New customer screen");
            newCustomer();
        } else if (option == 2) {
            System.out.println("Customer login screen");
            customerLogin();
        } else {
            if (option == 3) {
                System.out.println("Thank you for using this application!");
            }
            System.exit(1);
        }
    }

    private static void menu(int screen) {
        if (screen == 1) {
            System.out.println("Welcome to the Self Services Banking System! ");
            System.out.println("\t1. New Customer");
            System.out.println("\t2. Customer Login");
            System.out.println("\t3. Exit");
        } else if (screen == 2) {
            System.out.println("Customer Main Menu");
            System.out.println("\t1. Open Account");
            System.out.println("\t2. Close Account");
            System.out.println("\t3. Deposit");
            System.out.println("\t4. Withdraw");
            System.out.println("\t5. Transfer");
            System.out.println("\t6. Account Summary");
            System.out.println("\t7. Exit");
            System.out.println(" What is your option?");
        } else if (screen == 3) {
            System.out.println("Administrator Main Menu");
            System.out.println("\t1. Account Summary");
            System.out.println("\t2. Report A :: Customer Information with Total Balance in Decreasing Order");
            System.out.println("\t3. Report B :: Find the Average Total Balance Between Age Groups");
            System.out.println("\t4. Exit");
        }
    }

    private static void newCustomer() {
        in.reset();
        in.nextLine();
        System.out.print("\tEnter your name: ");
        String name = in.nextLine();
        System.out.print("\tEnter your gender: ");
        String gender = in.nextLine();
        System.out.print("\tEnter your age: ");
        String age = in.nextLine();
        System.out.print("\tEnter your pin: ");
        String pin = in.nextLine();

        if (BankingSystem.notfoundCustomer(name, gender.toUpperCase(), age, pin)) {
            String cusID = BankingSystem.newCustomerHelper(name, gender.toUpperCase(), age, pin);
            customerMenu(cusID);
        } else {
            System.out.print("Do you want to re-try (1) or login (2)?");
            String option = in.next();
            if (option.equals("1")) {
                newCustomer();
            } else if (option.equals("2")) {
                customerLogin();
            } else {
                System.out.println("Didn't recognize the command!");
                System.exit(1);
            }
        }
    }

    private static void customerLogin() {
        String cusID = "";
        String pin = "";
        System.out.print("LOGIN(Y/N): ");
        String option = in.nextLine();
        if(option.equalsIgnoreCase("n")){
            System.exit(1);
        }
        in.nextLine();
        do {
            System.out.print("\tEnter your customerID: ");
            cusID = in.nextLine();
            System.out.print("\tEnter your PIN: ");
            pin = in.nextLine();
            if (cusID.equals("0") && pin.equals("0")) {
                summaryMenu("", 0);
                System.out.println("Summary menu");
                break;
            }
        } while (BankingSystem.notfoundCustomer(cusID, pin));
        customerMenu(cusID);
    }

    private static void customerMenu(String cusID) {
        while (true) {
            menu(2);
            int option = in.nextInt();
            if (option == 1) {
                openAccount(cusID);
            } else if (option == 2) {
                closeAccount(cusID);
            } else if (option == 3) {
                deposit(cusID);
            } else if (option == 4) {
                withdraw(cusID);
            } else if (option == 5) {
               transfer(cusID);
            } else if (option == 6) {
                summaryMenu(cusID, 1);
            } else if (option == 7) {
                customerLogin();
                break;
            } else {
                System.out.println("Didn't recoginize the command");
            }
        }
    }

    private static void summaryMenu(String cusID, int returnScreen) {
        while (true) {
            menu(3);
            System.out.print("Choose your option: ");
            int option = in.nextInt();
            in.nextLine();
            if (option == 1) {
                if (cusID.equals("")) {
                    do {
                        System.out.print("\tEnter your customerID: ");
                        cusID = in.nextLine();
                    } while (BankingSystem.notfoundCustomer(cusID));
                }
                BankingSystem.accountSummary(cusID);
                break;
            } else if (option == 2) {
                BankingSystem.reportA();
                break;
            } else if (option == 3) {
                int min = in.nextInt();
                int max = in.nextInt();
                BankingSystem.reportB(String.valueOf(min), String.valueOf(max));
                break;
            } else if (option == 4) {
                if (returnScreen == 0) {
                    customerLogin();
                } else if (returnScreen == 1) {
                    customerMenu(cusID);
                }
                break;
            } else {
                System.out.println("Didn't recoginize the command");
            }
        }
    }

    private static void openAccount(String cusID) {
        int number = 0;
        String option = "";
        in.nextLine();
        do {
            System.out.print("\tAccount type(S/C): ");
            String type = in.nextLine();
            System.out.print("\tAmount of money: ");
            int amount = in.nextInt();
            in.nextLine();
            number = BankingSystem.openAccountHelper(cusID, type, String.valueOf(amount));
            if (number == 0) {
                System.out.print("Do you want to retry?(Y/N)");
                option = in.nextLine();
            }
        } while (number == 0 && option.equalsIgnoreCase("y"));
    }
    
    private static void closeAccount(String cusID ){
        String option = "";
        in.nextLine();
        do {
            System.out.print("\tAccount number: ");
            String account = in.nextLine();
            if (!BankingSystem.notfoundAccount(account, cusID)) {
                BankingSystem.closeAccount(account);
                break;
            } else {
                System.out.println("Cannot find this account");
                System.out.print("Do you want to retry?(Y/N)");
                option = in.nextLine();
            }
        } while (option.equalsIgnoreCase("y"));
    }

    private static void deposit(String cusID) {
        String option = "";
        in.nextLine();
        do {
            System.out.print("\tAccount number: ");
            String account = in.nextLine();
            if (!BankingSystem.notfoundAccount(account, cusID)) {
                System.out.print("\tAmount of money: ");
                int number = in.nextInt();
                BankingSystem.deposit(account, String.valueOf(number));
                break;
            } else {
                System.out.println("Cannot find this account");
                System.out.print("Do you want to retry?(Y/N)");
                option = in.nextLine();
            }
        } while (option.equalsIgnoreCase("y"));
    }

    private static void withdraw(String cusID) {
        String option = "";
        in.nextLine();
        do {
            System.out.print("\tAccount number: ");
            String account = in.nextLine();
            if (!BankingSystem.notfoundAccount(account, cusID)) {
                System.out.print("\tAmount of money: ");
                int number = in.nextInt();
                BankingSystem.withdraw(account, String.valueOf(number));
                break;
            } else {
                System.out.println("Cannot find this account");
                System.out.print("Do you want to retry?(Y/N)");
                option = in.nextLine();
            }
        } while (option.equalsIgnoreCase("y"));
    }

    private static void transfer(String cusID){
        //NEED 2 helper methods to get accountNumber of source and destination
        String option = "";
        in.nextLine();
        do {
            System.out.print("\tAccount number: ");
            String sentAccount = in.nextLine();
            System.out.print("\tSend to account: ");
            String receiveAccount = in.nextLine();
            if (!BankingSystem.notfoundAccount(sentAccount, cusID) && !BankingSystem.notfoundAccount(receiveAccount)) {
                System.out.print("\tAmount of money: ");
                int number = in.nextInt();
                BankingSystem.transfer(sentAccount, receiveAccount, String.valueOf(number));
                break;
            } else {
                System.out.println("Wrong input");
                System.out.print("Do you want to retry?(Y/N)");
                option = in.nextLine();
            }
        } while (option.equalsIgnoreCase("y"));
    }

}
