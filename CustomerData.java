import java.util.ArrayList; 

public class CustomerData {
    
    private ArrayList<String> customers;

    CustomerData() {
        this.customers = new ArrayList<String>();
    }

    public void setCustomerData(String customer) {
        this.customers.add(customer);
    }

    public ArrayList getCustomers() {
        return this.customers;
    }

    public String findCustomer(String username, String password) {
        String customer;
        for(int i = 0; i < this.customers.size(); i++) {
            customer = customers.get(i);
            if (customer.contains(username + "|" + password)) {
                return customer;
            }
        }
        return null;
    }

    public void addNewCustomer(FileHandler handleCustomersFile, String customer) {
        handleCustomersFile.writeCustomer(customer);
    }

    public void updateCustomerData(String customerData, String memberID) {
        String customer;
        for(int i = 0; i < this.customers.size(); i++) {
            customer = customers.get(i);
            if (customer.contains(memberID)) {
                customers.set(i, customerData);
            }
        }
    } 

    public void updateCustomersFile(FileHandler handleCustomersFile, String customerData, String memberID) {
        String customer;
        for(int i = 0; i < this.customers.size(); i++) {
            customer = customers.get(i);
            if (customer.contains(memberID)) {
                customers.set(i, customerData);
                handleCustomersFile.writeCustomer(customerData);
            } else {
                handleCustomersFile.writeCustomer(customer);
            }
        }
    }

    public void removeCustomer(FileHandler handleCustomersFile, String memberID) {
        String customer;
        for(int i = 0; i < this.customers.size(); i++) {
            customer = customers.get(i);
            if (customer.contains(memberID)) {
                customers.remove(i);
                i--;
            } else {
                handleCustomersFile.writeCustomer(customer);
            }
        }
    }
}
