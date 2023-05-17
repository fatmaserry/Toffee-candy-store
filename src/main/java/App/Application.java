package App;

import User.Customer;
import User.SessionManager;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {
    private AuthenticationService authenticationService;
    protected SessionManager currentSession;

    /**
     * This constructor creates authentication service and session manager
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
     * This method gives the ability to the user to change his password
     * if he forgot it or for other security reasons
     */
    public void resetPassword() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Your Email: ");
        String email = in.nextLine();

        String otp = AuthenticationService.emailSender.OTPGenerator();
        if (AuthenticationService.emailSender.sendOTP(
                currentSession.getCurrentCustomer().getUsername(), email, otp, "ResetPass")) {
            System.out.print("\n\nTo reset password, Please check your email.\n");
            System.out.print("Enter the OTP here: ");
            String entered_otp = in.nextLine();
            if (!entered_otp.equals(otp)) {
                System.out.println("Wrong OTP! Please Try Again.");
            } else {
                boolean flag = true;
                while (flag) {
                    System.out.println("Enter Your New Password or 0 to exit: ");
                    String password = in.nextLine();
                    if (password.equals("0")) {
                        break;
                    }
                    Pattern r = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-z]).{8,}$");
                    Matcher m = r.matcher(password);
                    if (m.find()) {
                        currentSession.getCurrentCustomer().setPassword(password);
                        authenticationService.updateFile();
                        System.out.println("Password has changed successfully");
                        break;
                    }
                    else {
                        while (!m.find()) {
                            System.out.println("The password should consist of at least 8 characters\nand should contain at least one digit, one small character, and one capital character");
                            System.out.println("Enter Your New Password or 0 to exit: ");
                            password = in.nextLine();
                            m = r.matcher(password);
                            if (password.equals("0")) {
                                flag = false;
                                break;
                            } else if (m.find()) {
                                currentSession.getCurrentCustomer().setPassword(password);
                                authenticationService.updateFile();
                                System.out.println("Password has changed successfully");
                                flag = false;
                                break;
                            }
                        }
                    }
                }
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
