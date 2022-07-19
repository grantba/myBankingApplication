import java.text.SimpleDateFormat;  
import java.util.Date; 
import java.util.ArrayList; 
import java.text.DecimalFormat;

abstract class Accounts {

    protected String accountID;   
    protected double balance;
    public static ArrayList<String> transactions = new ArrayList<String>();

    Accounts(String accountID, double balance, Customer customer) {
        this.accountID = accountID;
        this.balance = balance;
        customer.addAccount(this);
    }
     
    public String getAccountID() {
        return accountID;
    }

    public String getBalance() {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(this.balance).toString();
    }

    public abstract String deposit();

    public abstract String withdrawal();

    public abstract String transferFunds();
    
    public String toString() { //overriding the toString() method  
        return "Your current balance is: $" + this.getBalance(); 
    }  

    public static void addTransaction(String transaction) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
        Date date = new Date();  
        String timestamp = formatter.format(date) + transaction;  
        transactions.add(timestamp);
    }

    public static String printTransactions() {
        String customersTransactions;
        if (!transactions.isEmpty()) {
            customersTransactions = "Your recent transactions:\n";
            for (int i = 0; i < transactions.size(); i++) {
                customersTransactions += transactions.get(i) + "\n";            
            }
        } else {
            customersTransactions = "You do not have any recent transactions.";
        }
        return customersTransactions;
    }
}