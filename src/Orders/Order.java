package Orders;
import App.SessionManager;
import Cart.CartItem;
import Products.*;
import User.Customer;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    private String Address;
    private float overallPrice;
    private OrderDetails details;


    private final Customer owner = SessionManager.getCurrentCustomer();
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
    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }

    public void redeemVoucher(String codeOfVoucher){
        HashMap<String,Voucher> curr_vouchers =
                SessionManager.getCurrentCustomer().getVouchers();

        Voucher used_voucher = curr_vouchers.get(codeOfVoucher);




    }


}
