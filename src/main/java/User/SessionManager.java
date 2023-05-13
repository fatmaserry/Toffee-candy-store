package User;

public class SessionManager {
    private Customer currentCustomer;
    /**
     * This constructor sets the current customer to null which means that there is no logged-in user at this moment
     */
    public SessionManager() {
        currentCustomer = null;
    }
    /**
     * This method return the current customer who is logged-in the system at this moment
     * @return the current user of the system
     */
    public Customer getCurrentCustomer() {
        return currentCustomer;
    }
    /**
     * This method sets the current user to another user
     *
     * @param currentCustomer the current customer that just logged-in the system
     */
    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }
}
