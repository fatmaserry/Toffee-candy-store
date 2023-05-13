package User;

import Cart.ShoppingCart;
import Products.Voucher;
import Orders.Order;
import java.util.HashMap;

public class Customer {
    private String username;
    private String email;
    private String password;
    private String address;
    private float loyaltyPoints;
    private HashMap<Integer,Order> orders;
    private ShoppingCart cart;

    // HashMap<code,Voucher>
    private HashMap<Integer,Voucher> vouchers;
    /**
     * This constructor sets the attributes of the customer object
     *
     * @param name the customer's name
     * @param email the customer's email
     * @param password the customer's password
     * @param address the customer's address
     * @param points the customer's loyalty points
     */
    public Customer(String name, String email, String password, String address, float points){
        this.orders = new HashMap<>();
        this.username = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.loyaltyPoints = points;
        this.cart = new ShoppingCart(this);
    }

    public void addOrder(int id,Order order) {
        orders.put(id,order);
    }

    public void addVoucher(int code,Voucher voucher) {
        vouchers.put(code,voucher);
    }

    public HashMap<Integer,Voucher> getVouchers(){
        return vouchers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(float loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public HashMap<Integer, Order> getOrders() {
        return orders;
    }

    public void setOrders(HashMap<Integer,Order> orders) {
        this.orders = orders;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}
