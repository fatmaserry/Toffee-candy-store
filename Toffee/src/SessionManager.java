public class SessionManager {
    private Customer currentCustomer;


    public SessionManager() {
        currentCustomer = null;
    }
    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }
}
