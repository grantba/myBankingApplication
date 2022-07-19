public class CheckingAccount extends Accounts {

    CheckingAccount(String accountID, double balance, Customer customer) {
        super((accountID + "-C"), balance, customer);
    }
     
    public String getAccountID() {
        return super.getAccountID();
    }

    public String getBalance() {
        return super.getBalance();
    }

    public String deposit(double amount) {
        String response = "Your current checking account balance is: $" + this.getBalance(); 
        this.balance = this.balance + amount;
        response += "\nYour checking account balance after deposit is:$" + this.getBalance();
        Accounts.addTransaction(" - Checking deposit: $" + amount);
        return response;
    }

    public String withdrawal(double amount) {
        String status;
        if(this.balance >= amount) {
            status = "Your current checking account balance is: $" + this.getBalance(); 
            this.balance = balance - amount;
            Accounts.addTransaction(" - Checking withdrawal: $" + amount);
            status += "\nYour checking account balance after withdrawal is: $" + this.getBalance();
        } else {
            Accounts.addTransaction(" - Checking withdrawal: Insufficient funds for $" + amount + 
                " from $" + this.getBalance());
            status = "Insufficient funds.\n" + super.toString();
        }
        return status;
    }

    public String transferFunds(double amount, SavingsAccount accountTo) {
        String status;
        if(this.balance >= amount) {
            status = "Your current checking account balance is $" + this.getBalance() +
                "\nYour current savings account balance is $" + accountTo.getBalance();
            this.balance = balance - amount;
            accountTo.deposit(amount);
            Accounts.addTransaction(" - Transfer from checking to savings: $" + amount);
            status += "\nYour checking account balance after transfer is $" + this.getBalance() +
                "\nYour savings account balance after transfer is $" + accountTo.getBalance();
        } else {
            Accounts.addTransaction(" - Transfer from checking to savings: Insufficient funds for $" + 
                amount + " from $" + this.getBalance());
            status = "Insufficient funds currently in your checking account.\n" + super.toString();
        }
        return status;
    }

    public String toString() { //overriding the toString() method  
        return "Checking AccountID: " + super.getAccountID() +
            "\nBalance: $" + this.getBalance();    
        }  
}