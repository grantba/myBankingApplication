import java.util.Scanner;
import java.io.*;

public class FileHandler {

    String file;

    public FileHandler(String file) {
        this.file = file;
    }

    public void readCustomers(CustomerData customers) {
        try {
            FileReader reader = new FileReader(this.file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                customers.setCustomerData(line);
            }
            bufferedReader.close();
        } catch (IOException error) {
            System.out.println(error);
        }
    }

    public void readAccounts(AccountData accounts) {
        try {
            FileReader reader = new FileReader(this.file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                accounts.setAccountData(line);
            }
            bufferedReader.close();
        } catch (IOException error) {
            System.out.println(error);
        }
    }

    public void clearFile() {
        try {
            FileWriter writer = new FileWriter(this.file);
            writer.flush();
            writer.close();
        } catch (IOException error) {
            System.out.println(error);
        }
    }

    public void writeCustomer(String customerData) {
        try {
            FileWriter writer = new FileWriter(this.file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(customerData);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException error) {
            System.out.println(error);
        }
    }

    public void writeAccount(String accountData) {
        try {
            FileWriter writer = new FileWriter(this.file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(accountData);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException error) {
            System.out.println(error);
        }
    }
}
