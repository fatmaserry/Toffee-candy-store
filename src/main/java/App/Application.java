package App;

import User.Customer;
import User.SessionManager;

import java.util.Scanner;

public class Application {
    private AuthenticationService authenticationService;
    protected SessionManager currentSession;

    public Application(){
        authenticationService = new AuthenticationService();
        currentSession = new SessionManager();
    }

    public Boolean login(String email, String password) {
        if(authenticationService.verifyLogin(email, password)){
            currentSession.setCurrentCustomer(authenticationService.getCustomers().get(email));
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean register(String name, String email, String password, String address) {
        Customer newCustomer = new Customer(name, email, password, address);
        return authenticationService.verifyRegister(newCustomer);
    }

    public Boolean resetPassword() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Your Email: ");
        String email = in.nextLine();

        String otp = AuthenticationService.emailSender.OTPGenerator();
        if (AuthenticationService.emailSender.sendOTP(
                currentSession.getCurrentCustomer().getUsername(),
                currentSession.getCurrentCustomer().getEmail(), otp,"ResetPass")) {
            System.out.println("To reset password, Please check your email.\n" +
                    "Enter the OTP here: ");
            String entered_otp = in.nextLine();
            if (!entered_otp.equals(otp)) {
                System.out.println("Wrong OTP! Please Try Again.");
                return false;
            } else {
                System.out.println("Enter Your New Password: ");
                String password = in.nextLine();
                currentSession.getCurrentCustomer().setPassword(password);
                return true;
            }
        }
        return false;
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
