package User;

import Cart.ShoppingCart;
import Products.Voucher;
import Orders.Order;

import java.util.ArrayList;

public class Customer {
    private String username;
    private String email;
    private String password;
    private String address;
    private int loyaltyPoints;
    private ArrayList<Order> orders;
    private ShoppingCart cart;
    private ArrayList<Voucher> vouchers;
    public Customer() {
        orders = new ArrayList<>();
    }
    public Customer(String name, String email, String password, String address){
        orders = new ArrayList<>();
        this.username = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public void updateProfile() {
    }

    public void addOrder(Order order) {
        orders.add(order);

    }

    public void addVoucher(Voucher voucher) {
        vouchers.add(voucher);
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

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}
