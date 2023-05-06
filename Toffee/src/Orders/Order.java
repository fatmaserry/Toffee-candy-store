package Orders;
import App.SessionManager;
import Cart.CartItem;
import Products.*;
import User.Customer;

import java.util.ArrayList;

public class Order {
    private String Address;
    private float overallPrice;
    private OrderDetails details;

    private Customer owner = SessionManager.getCurrentCustomer();
    private ArrayList<CartItem> items;

    Order(ArrayList<CartItem> items, float priceBeforePayment){
        this.items = items;
        this.overallPrice = priceBeforePayment;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getOverallPrice() {
        return overallPrice;
    }

    public void setOverallPrice(float overallPrice) {
        this.overallPrice = overallPrice;
    }

    public OrderDetails getDetails() {
        return details;
    }

    public void setDetails(OrderDetails details) {
        this.details = details;
    }

    public Customer getOwner() {
        return owner;
    }


    public void redeemVoucher(){

    }
}
