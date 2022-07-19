import java.util.UUID;
import java.util.ArrayList;

public class Customer {

    private String memberID;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private ArrayList<Accounts> accounts;

    public Customer(String userName, String password, String firstName, String lastName, String memberID) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName; 
        this.memberID = setMemberID(memberID);   
        this.accounts = new ArrayList<Accounts>();
    }

    public String setMemberID(String memberID) {
        if (memberID == null) {
            String uniqueKey = UUID.randomUUID().toString();
            return uniqueKey;
        }
        return memberID;
    }

    public String getMemberID() {
        return this.memberID;
    }

    public void updateUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return this.userName;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }

    public void updateFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return this.firstName;
    }

    public void updateLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return this.lastName;
    }

    public void addAccount(Accounts account) {
        this.accounts.add(account);
    }

    public ArrayList getAccounts() {
        return this.accounts;
    }

    public String viewAccounts() {
        String userAccounts = "";
        if (!this.accounts.isEmpty()) {
            userAccounts += "Your current accounts:\n";
            for (int i = 0; i < accounts.size(); i++) {
                userAccounts += accounts.get(i) + "\n";            
            }
        } else {
            userAccounts += "You do not currently have any checking or savings accounts.";
        }
        return userAccounts;
    }

    public String toString() { //overriding the toString() method  
        return "Account information:\nMember ID - " + getMemberID() + "\nUsername - " + 
            getUserName() + "\nPassword - " + getPassword() + "\nFirst Name - " + getFirstName() + 
            "\nLast Name - " + getLastName();  
    }  
}