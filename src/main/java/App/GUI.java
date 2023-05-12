package App;

import Orders.OrderManager;
import java.util.Scanner;

public class GUI {
    protected Application app;

    public GUI(){
        app = new Application();
        showMenu();
    }

    public void showMenu() {
        while(true){
            if(app.currentSession.getCurrentCustomer() == null){
                guestMenu();
            }
            else{
                System.out.println("Welcome " + app.currentSession.getCurrentCustomer().getUsername());
                loginCustomerMenu();
            }
        }
    }

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
        return app.register(name, email, password, address);
    }
}
