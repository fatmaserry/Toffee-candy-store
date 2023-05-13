package App;

import User.Customer;
import User.SessionManager;
import java.util.Scanner;

public class Application {
    private AuthenticationService authenticationService;
    protected SessionManager currentSession;

    /**
     * This method returns the sum of two numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the sum of a and b
     * @example
     * <pre>{@code
     * int result = sum(1, 2);
     * System.out.println(result); // prints 3
     * }</pre>
     */
    public Application(){
        authenticationService = new AuthenticationService();
        currentSession = new SessionManager();
    }
    /**
     * This method returns true if the email is valid and the email's password is correct, otherwise false
     *
     * @param email the user's email
     * @param password the user's password
     * @return Boolean
     */
    public Boolean login(String email, String password) {
        if(authenticationService.verifyLogin(email, password)){
            currentSession.setCurrentCustomer(authenticationService.getCustomers().get(email));
            return true;
        }
        else{
            return false;
        }
    }
    /**
     * This method returns true if the user information are valid and the account created successfully
     *
     * @param name the user's name
     * @param email the user's email
     * @param password the user's password
     * @param address the user's address
     * @return Boolean
     */
    public Boolean register(String name, String email, String password, String address) {
        Customer newCustomer = new Customer(name, email, password, address, 0);
        return authenticationService.verifyRegister(newCustomer);
    }
    /**
     * This method gives the ability to the user to change his password if he forgot it or for other security reasons
     */
    public void resetPassword() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Your Email: ");
        String email = in.nextLine();

        String otp = AuthenticationService.emailSender.OTPGenerator();
        if (AuthenticationService.emailSender.sendOTP(
                currentSession.getCurrentCustomer().getUsername(),email, otp,"ResetPass")) {
            System.out.print("\n\nTo reset password, Please check your email.\n");
            System.out.print("Enter the OTP here: ");
            String entered_otp = in.nextLine();
            if (!entered_otp.equals(otp)) {
                System.out.println("Wrong OTP! Please Try Again.");
            } else {
                System.out.println("Enter Your New Password: ");
                String password = in.nextLine();
                currentSession.getCurrentCustomer().setPassword(password);
                System.out.println("Password has changed successfully");
            }
        }
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    public SessionManager getCurrentSession() {
       return currentSession;
    }
    public void setCurrentSession(SessionManager currentSession) {
        this.currentSession = currentSession;
    }

}
