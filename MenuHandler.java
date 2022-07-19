public class MenuHandler {

    protected int customerType;

    MenuHandler() {
        this.customerType = 0;
    }

    public int getCustomerType(int type) {
        if (type != this.customerType) {
            updateCustomerType(type);
        }
        return this.customerType;
    }

    public void updateCustomerType(int type) {
        this.customerType = type;
    }

    public String displayMenu(int type) {
        int currentCustomerType = getCustomerType(type);
        if (currentCustomerType == 0) {
            return initialMenu();
        }
        if (currentCustomerType == 2) {
            return accountTransferMenu();
        }
        if (currentCustomerType == 3) {
            return updateUserInfoMenu();
        }
        if (currentCustomerType == 4) {
            return closeAccountMenu();
        }
        return currentCustomerMenu();
    }

    public String initialMenu() {
        return "\nPlease make a selection from the menu:\n" +
            "1. Open a new member account\n2. Log in to your current member account\n";
    }

    public String currentCustomerMenu() {
        return "\n0. Main menu\n1. View accounts\n2. Open a checking account\n" +
            "3. Open a savings account\n4. Account balance\n5. Deposit\n6. Withdrawal\n" +
            "7. Transfer\n8. Print recent transactions\n9. Update account information\n" +
            "10. Close account\n11. Log out\nPress any other key to end the program\n";
    }

    public String accountTransferMenu() {
        return "Please make a selection:\n1. Transfer from checking to savings\n" +
            "2. Transfer from savings to checking";
    }

    public String updateUserInfoMenu() {
        return "What information would you like to update?\n1. Username" +
            "2. Password\n3. First Name\n4. Last Name\n5. Cancel";
    }

    public String closeAccountMenu() {
        return "Are you sure you want to close your account?\n1. Yes\n2. No";
    }
}