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
    protected static OTPConfirmationEmail emailSender = new OTPConfirmationEmail();

    public AuthenticationService() {
        catalog = new Catalog();
        customers = new HashMap<>();
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
                System.out.print("\n\nTo Confirm Registeration, Please check your email.\n");
                System.out.print("Enter the OTP here: ");
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

    public static OTPConfirmationEmail getEmailSender() {
        return emailSender;
    }
}
