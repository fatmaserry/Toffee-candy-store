package Orders;
import App.*;
import Cart.CartItem;
import Products.*;
import User.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Order {
    private OrderDetails details = new OrderDetails(); // For Shipping Process
    private float totalPriceOfItems; // overallPrice stands for price before discounts

    private ArrayList<Voucher> usedVouchers;
    private int usedLoyaltyPoints;

    private Payment paymentMethod;

    private Customer owner;
    private ArrayList<CartItem> items;

    Order(ArrayList<CartItem> items, float totalPriceOfItems){
        this.items = items;
        this.totalPriceOfItems = totalPriceOfItems;
        this.owner = SessionManager.getCurrentCustomer();
        this.details.setFinalPrice(totalPriceOfItems);
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

    public void paymentMethod(){
        System.out.println("Choose the method of payment: \n" +
                "1: Cash on delivery\n 2: E-wallet");

        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice){
            case 1:
                System.out.println("Enter your phone number: ");
                int phone = in.nextInt();
                paymentMethod = new CashOnDelivery(phone, details.getFinalPrice());
                break;
            case 2:
                System.out.println("Enter your credit number: ");
                int creditNum = in.nextInt();
                System.out.println("Enter the CVV: ");
                int cvv = in.nextInt();
                paymentMethod = new Ewallet(cvv,creditNum,details.getFinalPrice());
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }

    }
}
