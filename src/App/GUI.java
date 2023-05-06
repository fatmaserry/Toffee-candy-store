package App;

import java.util.Scanner;

public class GUI {
    private Application app;
    public GUI(){
        app = new Application();
        showMenu();
    }

    public void showMenu() {
        while(true){
            if(app.getCurrentUser().getCurrentCustomer() == null){
                guestMenu();
            }
            else{
                System.out.println("Welcome " + app.getCurrentUser().getCurrentCustomer().getUsername());
                loginCustomerMenu();
            }
        }
    }

    void loginCustomerMenu(){
        System.out.println("Choose the number of the option you want: ");
        System.out.println("1-Log out");
        System.out.println("2-Reset Password");
        System.out.println("3-Products.Catalog");
        System.out.println("4-View Cart");
        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        switch (option){
            case 1:
                app.getCurrentUser().setCurrentCustomer(null);
                break;
            case 2:
                app.resetPassword();
                break;
            case 3:
                app.getAuthenticationService().getCatalog().filterByCategory();
                break;
            case 4:
                // need the cart to be implemented
                break;
            default:
                break;
        }
    }

    void guestMenu(){
        System.out.println("Choose the number of the option you want: ");
        System.out.println("1-Login");
        System.out.println("2-Register");
        System.out.println("3-Reset Password");
        System.out.println("4-Products.Catalog");
        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        switch (option){
            case 1:
                if(loginInfoForm()){
                    System.out.println("Welcome to our store");
                }
                else{
                    System.out.println("Login failed");
                }
                break;
            case 2:
                if(registerInfoForm()){
                    System.out.println("You have registered new account");
                }
                else{
                    System.out.println("Registration failed");
                }
                break;
            case 3:
                app.resetPassword();
                break;
            case 4:
                app.getAuthenticationService().getCatalog().filterByCategory();
                break;
            default:
                break;
        }
    }

    public Boolean loginInfoForm() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Your Email: ");
        String email = in.nextLine();
        System.out.println("Enter Your Password: ");
        String password = in.nextLine();
        return app.login(email, password);
    }

    public Boolean registerInfoForm() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Your Name: ");
        String name = in.nextLine();
        System.out.println("Enter Your Email: ");
        String email = in.nextLine();
        System.out.println("Enter Your Password: ");
        String password = in.nextLine();
        System.out.println("Enter Your Address: ");
        String address = in.nextLine();
        return app.register(email, password, password, address);
    }
}
