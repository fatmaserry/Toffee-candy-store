package App;

//import com.Cart.*;

import User.Customer;


public class SessionManager {
    private static Customer currentCustomer;


    public SessionManager() {
        currentCustomer = null;
    }
    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }
}
