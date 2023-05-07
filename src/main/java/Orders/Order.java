package Orders;
import App.*;
import Cart.CartItem;
import Products.*;
import User.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Order {
    private OrderDetails details; // For Shipping Process
    private float totalPriceOfItems; // overallPrice stands for price before discounts

    private ArrayList<Voucher> usedVouchers;
    private int usedLoyaltyPoints;

    private Payment paymentMethod;

    private Customer owner;
    private ArrayList<CartItem> items;

    Order(ArrayList<CartItem> items, float totalPriceOfItems, Customer user){
        this.items = items;
        this.totalPriceOfItems = totalPriceOfItems;
        this.owner = user;
        this.details.setFinalPrice(totalPriceOfItems);
        details = new OrderDetails(user.getUsername());
    }
    public float getTotalPriceOfItems() {
        return totalPriceOfItems;
    }
    public void setTotalPriceOfItems(float totalPriceOfItems) {
        this.totalPriceOfItems = totalPriceOfItems;
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

    public Boolean redeemVoucher(String codeOfVoucher) {
        HashMap<String, Voucher> curr_vouchers = owner.getVouchers();
        Voucher used_voucher = curr_vouchers.get(codeOfVoucher);

        // If codeOfVoucher exists
        if (used_voucher != null){
            // Add the used voucher
            usedVouchers.add(used_voucher);

            // Update the final price after applying the discount of Voucher
            details.setFinalPrice(
                    details.getFinalPrice() - used_voucher.getDiscount() );

            return true;
        }
        else return false;
    }

    public Boolean redeemLoyaltyPoints(int numOfPoints){

        // Check if there are enough loyalty points
        if (numOfPoints <= owner.getLoyaltyPoints()){
            // Add the used loyalty points
            usedLoyaltyPoints+= numOfPoints;

            // Update the owner loyalty points
            owner.setLoyaltyPoints(owner.getLoyaltyPoints() - numOfPoints);

            // Update the final price after applying the discount of points
            details.setFinalPrice(details.getFinalPrice() - numOfPoints);

            return true;
        }
        else return false;
    }

    public void payment() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your phone number: ");
        int phone = in.nextInt();

        System.out.println("OTP is sent to your email.");
        System.out.println("Enter the OTP: ");
        int OTP = in.nextInt();

        paymentMethod = new CashOnDelivery(phone, details.getFinalPrice());
    }



}
