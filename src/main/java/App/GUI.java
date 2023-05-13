package App;

import Orders.OrderManager;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUI {
    protected Application app;

    public GUI(){
        app = new Application();
        showMenu();
    }
    /**
     * This method show the option menu for the user
     */
    public void showMenu() {
        while(true) {
            if(app.currentSession.getCurrentCustomer() == null) {
                guestMenu();
            }
            else {
                System.out.println("Welcome " + app.currentSession.getCurrentCustomer().getUsername());
                loginCustomerMenu();
            }
        }
    }
    /**
     * This method prints the menu for the logged-in user
     */
    void loginCustomerMenu() {
        System.out.println("Choose the number of the option you want: ");
        System.out.println("1-Log out");
        System.out.println("2-Reset Password");
        System.out.println("3-Products");
        System.out.println("4-View Cart");
        System.out.println("5-View Order History");
        System.out.println("6-Exit");
        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        switch (option) {
            case 1:
                // logout as set registered user equal null
                app.currentSession.setCurrentCustomer(null);
                break;
            case 2:
                // reset registered user password
                app.resetPassword();
                break;
            case 3:
                //print catalog items by category
                app.getAuthenticationService().getCatalog().filterByCategory();
                //add item to shopping cart
                app.currentSession.getCurrentCustomer().getCart()
                        .addItemToCart(app.getAuthenticationService().getCatalog().getItems());
                break;
            case 4:
                //print shopping cart details
                app.currentSession.getCurrentCustomer().getCart().printCartDetails();

                //if shopping cart is not empty
                if (app.currentSession.getCurrentCustomer().getCart().getQuantity() != 0) {
                    System.out.println("Choose the number of the option you want: ");
                    System.out.println("1-Checkout");
                    System.out.println("2-Remove item from cart");
                    System.out.println("3-Back to menu");
                    int opt = in.nextInt();
                    switch (opt) {
                        case 1:
                            app.getCurrentSession().getCurrentCustomer().getCart().checkout();
                            break;
                        case 2:
                            app.getCurrentSession().getCurrentCustomer().getCart().removeItemFromCart(); //remove item form cart
                            app.getCurrentSession().getCurrentCustomer().getCart().printCartDetails(); //print cart details after item is removed
                            break;
                        default:
                            break;
                    }
                }
                break;
            case 5:
                OrderManager x = new OrderManager();
                x.toCancel(app.getCurrentSession().getCurrentCustomer());
                break;
            default:
                System.exit(1);
        }
    }
    /**
     * This method prints the menu for the guest user
     */
    void guestMenu() {
        System.out.println("Choose the number of the option you want: ");
        System.out.println("1-Login");
        System.out.println("2-Register");
        System.out.println("3-Reset Password");
        System.out.println("4-Catalog");
        System.out.println("5-Exit");
        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        switch (option) {
            case 1:
                if (loginInfoForm()) {
                    System.out.println("Welcome to our store");
                } else {
                    System.out.println("Login failed");
                }
                break;
            case 2:
                if (registerInfoForm()) {
                    System.out.println("You have registered new account");
                } else {
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
                System.exit(1);
        }
    }
    /**
     * This method takes the inputs of the login process from the user
     * @return true if the information is valid and false otherwise
     */
    public Boolean loginInfoForm() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Your Email: ");
        String email = in.nextLine();
        System.out.println("Enter Your Password: ");
        String password = in.nextLine();
        return app.login(email, password);
    }
    /**
     * This method takes the inputs of the registration process from the user
     * @return true if the information is valid and false otherwise
     */
    public Boolean registerInfoForm() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Your Name: ");

        String name = in.nextLine();
        System.out.println("Enter Your Email: ");
        String email = in.nextLine();
        Pattern r = Pattern.compile("^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$");
        Matcher m = r.matcher(email);
        while(!m.find()){
            System.out.println("Enter a Valid Email or 0 to exit: ");
            email = in.nextLine();
            m = r.matcher(email);
            if(email.equals("0")) {
                return false;
            }
        }
        System.out.println("Enter Your Password: ");
        String password = in.nextLine();
        r = Pattern.compile("^(?=.\\d)(?=.[a-z])(?=.*[A-z]).{8,}$");
        m = r.matcher(password);
        while(!m.find()){
            System.out.println("The password should consist of at least 8 characters\nand should contain at least one digit, one small character, and one capital character");
            System.out.println("Enter Your Password or 0 to exit: ");
            password = in.nextLine();
            m = r.matcher(password);
            if(password.equals("0")) {
                return false;
            }
        }
        System.out.println("Enter Your Address: ");
        String address = in.nextLine();
        return app.register(name, email, password, address);
    }
}
