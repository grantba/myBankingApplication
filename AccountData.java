import java.util.ArrayList; 

public class AccountData {
    
    private ArrayList<String> accounts;

    AccountData() {
        this.accounts = new ArrayList<String>();
    }

    public void setAccountData(String account) {
        this.accounts.add(account);
    }

    public ArrayList getAccounts() {
        return this.accounts;
    }

    public String findAccount(String memberID) {
        String accountData;
        for(int i = 0; i < this.accounts.size(); i++) {
            accountData = accounts.get(i);
            if (accountData.contains(memberID)) {
                return accountData;
            }
        }
        return null;
    }

    public void addNewAccount(FileHandler file, String account) {
        file.writeAccount(account);
    }

    public void updateAccountData(FileHandler file, String memberID, String accountData) {
        String customerAccount;
        String currentCustomerAccount = accountData;
        for(int i = 0; i < this.accounts.size(); i++) {
            customerAccount = accounts.get(i);
            if (customerAccount.contains(memberID)) {
                accounts.set(i, currentCustomerAccount);
            }
        }
    }

    public void updateAccountsFile(FileHandler file, String memberID, String accountData) {
        String customerAccount;
        for(int i = 0; i < this.accounts.size(); i++) {
            customerAccount = accounts.get(i);
            if (customerAccount.contains(memberID)) {
                accounts.set(i, accountData);
                file.writeAccount(accountData);
            } else {
                file.writeAccount(customerAccount);
            }
        }
    }

    public void removeAccount(FileHandler file, String memberID) {
        String customerAccount;
        for(int i = 0; i < this.accounts.size(); i++) {
            customerAccount = accounts.get(i);
            if (customerAccount.contains(memberID)) {
                accounts.remove(i);
                i--;
            } else if (!customerAccount.isEmpty()) {
                file.writeAccount(customerAccount);
            }
        }
    }
}
