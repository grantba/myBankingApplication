import java.util.Scanner;
import java.io.*;

public class RunApplication {

    public void run() throws Exception {

        ASCIIArtGenerator artGen = new ASCIIArtGenerator();
        System.out.println();
        artGen.printTextArt("THE BANK APP", ASCIIArtGenerator.ART_SIZE_SMALL);
        System.out.println();
         
        InputStreamReader input = new InputStreamReader(System.in);    
        BufferedReader buffer = new BufferedReader(input); 
        Scanner scanner = new Scanner(System.in); 
        int selection = 0;
        String response;
        String account;
        double amount;

        String customersFile = "C:\\Users\\grant\\Development\\Code\\Java Practice\\CustomersDB.txt";
        String checkingAccountsFile = "C:\\Users\\grant\\Development\\Code\\Java Practice\\CheckingAccountsDB.txt";
        String savingsAccountsFile = "C:\\Users\\grant\\Development\\Code\\Java Practice\\SavingsAccountsDB.txt";

        MenuHandler userMenu = new MenuHandler();
        UserInputHandler userInput = new UserInputHandler(customersFile, checkingAccountsFile, savingsAccountsFile);

        Customer customer = null;
        CheckingAccount checkingAccount = null;
        SavingsAccount savingsAccount= null;
        String userName = "";
        String password = "";
        String firstName = "";
        String lastName = "";
        String memberID = "";

        System.out.println(userMenu.displayMenu(0));

        try {
            selection = scanner.nextInt();

            // Initial menu - 1. sign up or 2. login
            if (selection == (int)selection && selection == 1) {
                System.out.println("\nGreat. Let's get started.");
                System.out.println("Please enter a username: ");
                response = buffer.readLine();
                userName = response;
                System.out.println("Please enter a password: ");
                response = buffer.readLine();
                password = response;
                System.out.println("Please enter your first name: ");
                response = buffer.readLine();
                firstName = response;
                System.out.println("Please enter your last name: ");
                response = buffer.readLine();
                lastName = response;
                customer = new Customer(userName, password, firstName, lastName, null);
                memberID = customer.getMemberID();
                String newCustomer = userInput.createNewCustomer(customer, userName, password, firstName, lastName, memberID);
                System.out.println(newCustomer);
                selection = 0;
            } else if (selection == (int)selection && selection == 2) {
                System.out.println("\nPlease enter your username: ");
                response = buffer.readLine();
                userName = response;
                System.out.println("You entered " + response + " for your username.");
                System.out.println("Please enter your password: ");
                response = buffer.readLine();
                password = response;
                System.out.println("You entered " + response + " for your password.");
                String customerSearch = userInput.login(userName, password);
                if (customerSearch == null) {
                    System.out.println("Sorry, we were unable to validate your account information.");
                    selection = 12; 
                } else {
                    String[] customerArray = customerSearch.split("\\|");
                    firstName = customerArray[2];
                    lastName = customerArray[3];
                    memberID = customerArray[4];
                    customer = new Customer(userName, password, firstName, lastName, memberID);
                    String checkingAccountSearch = userInput.getCheckingAccount(memberID);
                    if (checkingAccountSearch != null) {
                        String[] checkingArray = checkingAccountSearch.split("\\|");
                        Double checkingBalance = Double.parseDouble(checkingArray[1]);
                        checkingAccount = new CheckingAccount(memberID, checkingBalance, customer);
                    }
                    String savingsAccountSearch = userInput.getSavingsAccount(memberID);
                    if (savingsAccountSearch != null) {
                        String[] savingsArray = savingsAccountSearch.split("\\|");
                        Double savingsBalance = Double.parseDouble(savingsArray[1]);
                        savingsAccount = new SavingsAccount(memberID, savingsBalance, customer);
                    }
                    System.out.println("\nWelcome " + firstName + "! What would you like to do next?"); 
                    selection = 0;
                }
            } else {
                System.out.println("Sorry, " + selection + " is an invalid menu selection.");
                selection = 12;
            }

            while (selection == (int)selection && selection < 12 && selection >= 0) {

                switch (selection) {
                case 0: // taken to main menu once user has been logged in
                    System.out.println(userMenu.displayMenu(1));
                    selection = scanner.nextInt();
                    break;
                case 1: // view accounts
                    System.out.print(userInput.viewAccounts(customer));
                    selection = 0;
                    break;
                case 2: // open checking account
                    if (checkingAccount == null) {
                        checkingAccount = new CheckingAccount(memberID, 0.0, customer);
                        System.out.println(userInput.processNewCheckingAccount(checkingAccount));
                    } else {
                        System.out.println("You already have a checking account. Please make another selection.");
                    }
                    selection = 0;
                    break;
                case 3: // open savings account
                    if (savingsAccount == null) {
                        System.out.println("A minimum of $5.00 is required to open a savings account and you must " +
                            "maintain at least a $5.00 balance to keep this account open.");
                        System.out.print("Please enter the amount you would like to deposit to open your new " +
                            "savings account (such as 5.00 or 15.53): $");
                        response = buffer.readLine();
                        amount = Double.parseDouble(response);
                        if (amount >= 5.00) {
                            savingsAccount = new SavingsAccount(memberID, amount, customer);
                            System.out.println(userInput.processNewSavingsAccount(savingsAccount)); 
                        } else {
                            System.out.println(amount + " is an invalid amount to open a savings account. Please try again.");
                        }
                    } else {
                        System.out.println("You already have a savings account. Please make another selection.");
                    }
                    selection = 0;
                    break;
                case 4: // view account balance
                    System.out.print("Which account balance would you like to view, checking or savings: ");
                    account = buffer.readLine().toLowerCase();
                    System.out.print(userInput.getAccountBalance(account, checkingAccount, savingsAccount));
                    selection = 0;
                    break;
                case 5: // deposit
                    System.out.print("Which account you would like to make a deposit into, checking or savings: ");
                    account = buffer.readLine().toLowerCase();
                    if (account.equals("checking") || account.equals("savings")) {
                        if (checkingAccount == null && savingsAccount == null) {
                            System.out.println("You do not currently have a checking or savings account. Please try again.");       
                        } else if (account.equals("checking") && checkingAccount != null || account.equals("savings") && savingsAccount != null) {
                            System.out.print("Please enter the amount you would like to deposit (such as 5.00 or 15.53): $");
                            response = buffer.readLine();
                            amount = Double.parseDouble(response);
                            System.out.println(userInput.processDeposit(account, amount, checkingAccount, savingsAccount, memberID));   
                        } else {
                            System.out.println("You do not currently have a " + account + " account. Please try again.");
                        }
                    } else {
                        System.out.println(account + " is an invalid response. Please try again.");
                    }
                    selection = 0;
                    break;
                case 6: // withdrawal     
                    System.out.print("Which account you would like to make a withdrawal from, checking or savings: ");
                    account = buffer.readLine().toLowerCase();
                    if (account.equals("checking") || account.equals("savings")) {
                        if (checkingAccount == null && savingsAccount == null) {
                            System.out.println("You do not currently have a checking or savings account. Please try again."); 
                        } else if (account.equals("checking") && checkingAccount != null || account.equals("savings") && savingsAccount != null) {
                            System.out.print("Please enter the amount you would like to withdrawal (such as 5.00 or 15.53): $");
                            response = buffer.readLine();
                            amount = Double.parseDouble(response);
                            System.out.println(userInput.processWithdrawal(account, amount, checkingAccount, savingsAccount, memberID));   
                        } else {
                            System.out.println("You do not currently have a " + account + " account. Please try again.");
                        }
                    } else {
                        System.out.println(account + " is an invalid response. Please try again.");
                    }                
                    selection = 0;
                    break;
                case 7: // transfer
                    System.out.println(userMenu.displayMenu(2));
                    selection = scanner.nextInt();
                    if (checkingAccount != null && savingsAccount != null) {
                        System.out.print("Please enter the amount you would like to transfer (such as 5.00 or 15.53): $");
                        response = buffer.readLine();
                        amount = Double.parseDouble(response);
                        System.out.println(userInput.processTransfer(selection, amount, checkingAccount, savingsAccount, memberID));  
                    } else if (savingsAccount == null) {
                        System.out.println("You do not currently have a savings account. Please try again.\n");
                    } else if (checkingAccount == null) {
                        System.out.println("You do not currently have a checking account. Please try again.\n");
                    } else {
                        System.out.println("You do not currently have a checking or savings account. Please try again.\n");
                    }
                    selection = 0;
                    break;
                case 8: // view recent transactions
                    System.out.println(userInput.viewTransactions());              
                    selection = 0;
                    break;
                case 9: // update account information
                    System.out.println(userMenu.displayMenu(3));
                    selection = scanner.nextInt();
                    if (selection == (int)selection && selection == 1) {
                        System.out.println("Please enter your updated username.");
                        response = buffer.readLine();
                        userName = response;
                    } else if (selection == (int)selection && selection == 2) {
                        System.out.println("Please enter your updated password.");
                        response = buffer.readLine();
                        password = response;
                    } else if (selection == (int)selection && selection == 3) {
                        System.out.println("Please enter your updated first name.");
                        response = buffer.readLine();
                        firstName = response;
                    } else if (selection == (int)selection && selection == 4) {
                        System.out.println("Please enter your udpated last name.");
                        response = buffer.readLine();
                        lastName = response;
                    } else if (selection == (int)selection && selection == 5) {
                        selection = 0;
                        break;
                    } else {
                        System.out.println(selection + " was an invalid response. Please try again.\n");
                        selection = 0;
                        break;
                    }   
                    String updatedCustomerData =  userName + "|" + password + "|" + firstName + "|" + lastName + "|" + memberID;
                    System.out.println(userInput.updateUserInformation(updatedCustomerData, memberID));
                    selection = 0;
                    break;
                case 10: // close account
                    System.out.println(userMenu.displayMenu(4));
                    response = buffer.readLine();
                    selection = Integer.parseInt(response);
                    if (selection == (int)selection && selection == 1) {
                        System.out.println(userInput.closeUserAccount(firstName, memberID));
                        selection = 12;
                    } else if (selection == (int)selection && selection == 2) {
                        System.out.println("Great, what would you like to do next?");
                        selection = 0;
                    } else {
                        System.out.println("Sorry, " + selection + " is an invalid response. Please try again.\n");
                        selection = 0;
                    }        
                    break;
                case 11: // log out
                    System.out.println(userInput.logout(userName, password, firstName, lastName, memberID, checkingAccount, savingsAccount));
                    selection = 12;
                    break;
                }
            }
        } catch(Exception error) {
            // proceed without showing an error and abruptly shutting down the program
            System.out.println("Sorry, we encountered an error while processing that request.");
        }

        buffer.close();
        input.close();
        scanner.close();
    }

    public static void main(String[] args) throws Exception {
        RunApplication bankingInstance = new RunApplication();
        bankingInstance.run();
    }
}