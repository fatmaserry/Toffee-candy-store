package App;

import Orders.Order;
import Products.Catalog;
import User.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;


public class AuthenticationService {
    private Catalog catalog;
    private final HashMap<String, Customer> customers;
    private final OTPConfirmationEmail emailSender = new OTPConfirmationEmail();
    private ArrayList<Order> orders;

    public AuthenticationService() {
        catalog = new Catalog();
        customers = new HashMap<>();
        orders = new ArrayList<>();
    }

    public Boolean verifyLogin(String email, String password) {
        if (customers.get(email) == null)
            return false;
        return Objects.equals(customers.get(email).getPassword(), password);
    }

    public Boolean verifyRegister(Customer customer) {
        if (!customers.containsKey(customer.getEmail())) {

            String otp = emailSender.OTPGenerator();
            if (emailSender.sendOTP(customer.getUsername(), customer.getEmail(), otp,"Register")) {
                System.out.println("To confirm registeration, Please check your email.\n" +
                        "Enter the OTP here: ");
                Scanner in = new Scanner(System.in);
                String entered_otp = in.nextLine();
                if (!entered_otp.equals(otp)) {
                    System.out.println("Wrong OTP! Please Try Again.");
                    return false;
                } else {
                    customers.put(customer.getEmail(), customer);
                    return true;
                }
            }
        }
        return false;
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

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public static OTPConfirmationEmail getEmailSender() {
        return emailSender;
    }
}
