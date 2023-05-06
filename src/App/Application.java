package App;

import User.Customer;

import java.util.Scanner;

public class Application {
    private AuthenticationService authenticationService;
    private SessionManager currentUser;

    public Application(){
        authenticationService = new AuthenticationService();
        currentUser = new SessionManager();
    }

    public Boolean login(String email, String password) {
        if(authenticationService.verifyLogin(email, password)){
            currentUser.setCurrentCustomer(authenticationService.getCustomers().get(email));
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
        System.out.println("Enter Your New Password: ");
        String password = in.nextLine();
        currentUser.getCurrentCustomer().setPassword(password);
        return false;
    }

    public Boolean confirmOTP(int otp){
        // not completed yet
        return true;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public SessionManager getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(SessionManager currentUser) {
        this.currentUser = currentUser;
    }
}
