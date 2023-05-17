package App;

import Products.Catalog;
import User.Customer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;


public class AuthenticationService {
    private Catalog catalog;
    private final HashMap<String, Customer> customers;
    protected static OTPConfirmationEmail emailSender = new OTPConfirmationEmail();

    /**
     * This constructor creates catalog and hashmap then it's import the users that have logged in the system before
     */
    public AuthenticationService() {
        catalog = new Catalog();
        customers = new HashMap<>();
        try {
            File users = new File("E:\\Desktop\\SoftwareEngineering1-ToffeeStore\\src\\main\\java\\users.txt");
            Scanner reader = new Scanner(users);
            int cnt = 1;
            String username = "", email = "", password = "", address = "";
            int points = 0;
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                switch (cnt % 6){
                    case 0:
                        customers.put(email, new Customer(username, email, password, address, points));
                    case 1:
                        username = data;
                        break;
                    case 2:
                        email = data;
                        break;
                    case 3:
                        password = data;
                        break;
                    case 4:
                        address = data;
                        break;
                    case 5:
                        points = Integer.parseInt(data);
                        break;
                }
                cnt++;
            }
        }
        catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    /**
     * This method returns true if there is email is already registered before and the password match the user's password, otherwise false
     *
     * @param email the user's email
     * @param password the user's password
     * @return Boolean
     */
    public Boolean verifyLogin(String email, String password) {
        if (customers.get(email) == null){
            return false;
        }
        return Objects.equals(customers.get(email).getPassword(), password);
    }
    /**
     * This method returns true if the customer doesn't registered to the system before and the customer entered a valid OTP
     *
     * @param customer the customer object that contains the customer info
     * @return Boolean
     */
    public Boolean verifyRegister(Customer customer) {
        if (!customers.containsKey(customer.getEmail())) {
            String otp = emailSender.OTPGenerator();
            if (emailSender.sendOTP(customer.getUsername(), customer.getEmail(), otp,"Register")) {
                System.out.print("\n\nTo Confirm Registeration, Please check your email.\n");
                System.out.print("Enter the OTP here: ");
                Scanner in = new Scanner(System.in);
                String entered_otp = in.nextLine();
                if (!entered_otp.equals(otp)) {
                    System.out.println("Wrong OTP! Please Try Again.");
                    return false;
                } else {
                    try {
                        FileWriter myWriter = new FileWriter("E:\\Desktop\\SoftwareEngineering1-ToffeeStore\\src\\main\\java\\users.txt", true);
                        myWriter.write("\n");
                        myWriter.write(customer.getUsername());
                        myWriter.write("\n");
                        myWriter.write(customer.getEmail());
                        myWriter.write("\n");
                        myWriter.write(customer.getPassword());
                        myWriter.write("\n");
                        myWriter.write(customer.getAddress());
                        myWriter.write("\n");
                        myWriter.write(String.valueOf(customer.getLoyaltyPoints()));
                        myWriter.write("\n.");
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                    customers.put(customer.getEmail(), customer);
                    return true;
                }
            }
        }
        return false;
    }


    public void updateFile() {
        try {
            FileWriter myWriter = new FileWriter("E:\\Desktop\\SoftwareEngineering1-ToffeeStore\\src\\main\\java\\users.txt");
            for (String email : customers.keySet()) {
                myWriter.write(customers.get(email).getUsername());
                myWriter.write("\n");
                myWriter.write(customers.get(email).getEmail());
                myWriter.write("\n");
                myWriter.write(customers.get(email).getPassword());
                myWriter.write("\n");
                myWriter.write(customers.get(email).getAddress());
                myWriter.write("\n");
                myWriter.write(String.valueOf(customers.get(email).getLoyaltyPoints()));
                myWriter.write("\n.\n");
            }
            myWriter.close();
        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public HashMap<String, Customer> getCustomers() {
        return customers;
    }

    public static OTPConfirmationEmail getEmailSender() {
        return emailSender;
    }
}
