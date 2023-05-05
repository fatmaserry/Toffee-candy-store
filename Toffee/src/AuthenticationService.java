import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AuthenticationService {
    private Catalog catalog;
    private HashMap<String, Customer> customers;

    private ArrayList<Order> orders;

    public AuthenticationService() {
        catalog = new Catalog();
        customers = new HashMap<>();
        orders = new ArrayList<>();
    }

    public Boolean verifyLogin(String email, String  password) {
        return Objects.equals(customers.get(email).getPassword(), password);
    }

    public Boolean verifyRegister(Customer customer) {
        if(customers.containsKey(customer.getEmail())){
            return false;
        }
        else{
            customers.put(customer.getEmail(), customer);
            return true;
        }
    }

    // no need for function addUser in authentication service
    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public HashMap<String, Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
