public class UserInputHandler {
    
    FileHandler handleCustomersFile;
    FileHandler handleCheckingAccountsFile;
    FileHandler handleSavingsAccountsFile;

    CustomerData customers;
    AccountData checkingAccounts;
    AccountData savingsAccounts;

    UserInputHandler(String customersFile, String checkingAccountsFile, String savingsAccountsFile) {
        handleCustomersFile = new FileHandler(customersFile);
        handleCheckingAccountsFile = new FileHandler(checkingAccountsFile);
        handleSavingsAccountsFile = new FileHandler(savingsAccountsFile);
        customers = new CustomerData();
        checkingAccounts = new AccountData();
        savingsAccounts = new AccountData();
        setFileData();
    }

    public void setFileData() {
        handleCustomersFile.readCustomers(customers);
        handleCheckingAccountsFile.readAccounts(checkingAccounts);
        handleSavingsAccountsFile.readAccounts(savingsAccounts);
    }

    public String createNewCustomer(Customer customer, String userName, String password, String firstName, String lastName, String memberID) {
        String customerData =  userName + "|" + password + "|" + firstName + "|" + lastName + "|" + memberID;
        customers.addNewCustomer(handleCustomersFile, customerData);
        customers.setCustomerData(customerData);
        return "Your new member account has been created.\n" + customer + "\nWelcome " + firstName + "! What would you like to do next?";
    }

    public String login(String userName, String password) {
        String customerSearch = customers.findCustomer(userName, password);
        return customerSearch;
    }

    public String getCheckingAccount(String memberID) {
        String checkingAccountSearch = checkingAccounts.findAccount(memberID);
        return checkingAccountSearch;
    }

    public String getSavingsAccount(String memberID) {
        String savingsAccountSearch = savingsAccounts.findAccount(memberID);
        return savingsAccountSearch;
    }

    public String viewAccounts(Customer customer) {
        String accounts = customer.viewAccounts();
        return accounts;
    }

    public String processNewCheckingAccount(CheckingAccount checkingAccount) {
        String checkingAccountData = checkingAccount.getAccountID() + "|" + checkingAccount.getBalance();
        checkingAccounts.addNewAccount(handleCheckingAccountsFile, checkingAccountData);
        checkingAccounts.setAccountData(checkingAccountData);
        return "Your new checking account has been created.\n" + checkingAccount;
    }

    public String processNewSavingsAccount(SavingsAccount savingsAccount) {
        String savingsAccountData = savingsAccount.getAccountID() + "|" + savingsAccount.getBalance();
        savingsAccounts.addNewAccount(handleSavingsAccountsFile, savingsAccountData);
        savingsAccounts.setAccountData(savingsAccountData);
        return "Your new savings account has been created.\n" + savingsAccount;
    }

    public String getAccountBalance(String account, CheckingAccount checkingAccount, SavingsAccount savingsAccount) {
        String balance;
        if (account.equals("checking") && checkingAccount != null) {
            balance = "Your current checking account balance is: $" + checkingAccount.getBalance() + "\n"; 
        } else if (account.equals("savings") && savingsAccount != null) {
            balance = "Your current savings account balance is: $" + savingsAccount.getBalance() + "\n"; 
        } else if (savingsAccount == null && checkingAccount == null) {
            balance = "You do not currently have a checking or savings account. Please try again.\n";
        } else if (checkingAccount == null || savingsAccount == null) {
            balance = "You do not currently have a " + account + " account. Please try again.\n";
        } else {                        
            balance = account + " is an invalid response. Please try again.\n";
        }  
        return balance;
    }

    public String processDeposit(String account, double amount, CheckingAccount checkingAccount, SavingsAccount savingsAccount, String memberID) {
        String response;
        if (account.equals("checking") && checkingAccount != null) {
            response = checkingAccount.deposit(amount);
            String checkingAccountData = checkingAccount.getAccountID() + "|" + checkingAccount.getBalance();
            checkingAccounts.updateAccountData(handleCheckingAccountsFile, memberID, checkingAccountData);
        } else if (account.equals("savings") && savingsAccount != null) {
            response = savingsAccount.deposit(amount);
            String savingsAccountData = savingsAccount.getAccountID() + "|" + savingsAccount.getBalance();
            savingsAccounts.updateAccountData(handleSavingsAccountsFile, memberID, savingsAccountData);
        } else {
            response = "You do not currently have a " + account + " account. Please try again.";
        }
        return response;
    }

    public String processWithdrawal(String account, double amount, CheckingAccount checkingAccount, SavingsAccount savingsAccount, String memberID) {
        String response;
        if (account.equals("checking") && checkingAccount != null) {
            response = checkingAccount.withdrawal(amount);
            String checkingAccountData = checkingAccount.getAccountID() + "|" + checkingAccount.getBalance();
            checkingAccounts.updateAccountData(handleCheckingAccountsFile, memberID, checkingAccountData);
        } else if (account.equals("savings") && savingsAccount != null) {
            response = savingsAccount.withdrawal(amount);
            String savingsAccountData = savingsAccount.getAccountID() + "|" + savingsAccount.getBalance();
            savingsAccounts.updateAccountData(handleSavingsAccountsFile, memberID, savingsAccountData);
        } else {
            response = "You do not currently have a " + account + " account. Please try again.";
        }
        return response;
    }

    public String processTransfer(int selection, double amount, CheckingAccount checkingAccount, SavingsAccount savingsAccount, String memberID) {
        String response;
        if (selection == (int)selection && selection == 1) {
            String status = checkingAccount.transferFunds(amount, savingsAccount);
            response = status;
            String checkingAccountData = checkingAccount.getAccountID() + "|" + checkingAccount.getBalance();
            checkingAccounts.updateAccountData(handleCheckingAccountsFile, memberID, checkingAccountData);
        } else if (selection == (int)selection && selection == 2) {
            String status = savingsAccount.transferFunds(amount, checkingAccount);
            response = status;
            String savingsAccountData = savingsAccount.getAccountID() + "|" + savingsAccount.getBalance();
            savingsAccounts.updateAccountData(handleSavingsAccountsFile, memberID, savingsAccountData);
        } else if (selection == (int)selection && selection != 1 && selection != 2) {
            response = "Sorry, " + selection + " is an invalid menu selection.";
        } else {
            response = amount + " is an invalid transfer amount. Please try again.";
        }
        return response;
    }

    public String viewTransactions() {
        String transactions = Accounts.printTransactions();  
        return transactions;
    }

    public String updateUserInformation(String updatedCustomerData, String memberID) {
        customers.updateCustomerData(updatedCustomerData, memberID);  
        return "Your account information has been updated successfully.";
    }

    public String closeUserAccount(String firstName, String memberID) {
        handleCustomersFile.clearFile();
        customers.removeCustomer(handleCustomersFile, memberID);
        handleCheckingAccountsFile.clearFile();
        checkingAccounts.removeAccount(handleCheckingAccountsFile, memberID);  
        handleSavingsAccountsFile.clearFile();
        savingsAccounts.removeAccount(handleSavingsAccountsFile, memberID);  
        return "You have successfully closed your account. We're sorry to see you go " + firstName + "!";
    }

    public String logout(String userName, String password, String firstName, String lastName, String memberID, 
        CheckingAccount checkingAccount, SavingsAccount savingsAccount) {
        handleCustomersFile.clearFile();
        String customerData =  userName + "|" + password + "|" + firstName + "|" + lastName + "|" + memberID;
        customers.updateCustomersFile(handleCustomersFile, customerData, memberID);
        if (checkingAccount != null) {
            handleCheckingAccountsFile.clearFile();
            String checkingAccountData = checkingAccount.getAccountID() + "|" + checkingAccount.getBalance();
            checkingAccounts.updateAccountsFile(handleCheckingAccountsFile, memberID, checkingAccountData);
        }
        if (savingsAccount != null) {
            handleSavingsAccountsFile.clearFile();
            String savingsAccountData = savingsAccount.getAccountID() + "|" + savingsAccount.getBalance();
            savingsAccounts.updateAccountsFile(handleSavingsAccountsFile, memberID, savingsAccountData);  
        }
        return "You have successfully logged out. Thank you for visiting today " + firstName + "! We hope to see you again soon!";
    }
}
