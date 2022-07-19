public class SavingsAccount extends Accounts {

    SavingsAccount(String accountID, double balance, Customer customer) {
        super((accountID + "-S"), balance, customer);
    }

    public String getAccountID() {
        return super.getAccountID();
    }

    public String getBalance() {
        return super.getBalance();
    }

    public String deposit(double amount) {
        String response = "Your current savings account balance is: $" + this.getBalance(); 
        this.balance = this.balance + amount;
        response += "\nYour savings account balance after deposit is: $" + this.getBalance();
        Accounts.addTransaction(" - Savings deposit: $" + amount);
        return response;
    }

    public String withdrawal(double amount) {
        String status;
        if(this.balance >= (amount + 5.00)) {
            status = "Your current savings account balance is: $" + this.getBalance(); 
            this.balance = balance - amount;
            Accounts.addTransaction(" - Savings withdrawal: $" + amount);
            status += "\nYour savings account balance after withdrawal is: $" + this.getBalance();
        } else {
            Accounts.addTransaction(" - Savings withdrawal: Insufficient funds for $" + amount + 
                " from $" + this.getBalance());
            status = "Insufficient funds. $5 is required to maintain a savings account.\n" + super.toString();
        }
        return status;
    }

    public String transferFunds(double amount, CheckingAccount accountTo) {
        String status;
        if(this.balance >= (amount + 5.00)) {
            status = "Your current savings account balance is $" + this.getBalance() +
                "\nYour current checking account balance is $" + accountTo.getBalance();
            this.balance = balance - amount;
            accountTo.deposit(amount);
            Accounts.addTransaction(" - Transfer from savings to checking: $" + amount);
            status += "\nYour savings account balance after transfer is $" + this.getBalance() +
                "\nYour checking account balance after transfer is $" + accountTo.getBalance();
        } else {
            Accounts.addTransaction(" - Transfer from savings to checking: Insufficient funds for $" + 
                amount + " from $" + this.getBalance());
            status = "Insufficient funds in your savings account. $5 is required to maintain a savings " +
                "account.\n" + super.toString();
        }
        return status;
    }

    public String toString() { //overriding the toString() method  
        return "Savings AccountID: " + super.getAccountID() +
            "\nBalance: $" + this.getBalance();
    }  
}