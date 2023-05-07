package Orders;
import App.*;
import Cart.CartItem;
import Products.*;
import User.Customer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;


public class Order {
    private OrderDetails details; // For Shipping Process
    private float totalPriceOfItems; // overallPrice stands for price before discounts
    private Payment paymentMethod;
    private ArrayList<Voucher> usedVouchers;
    private int usedLoyaltyPoints;
    private Customer owner;
    private ArrayList<CartItem> items;

    Order(ArrayList<CartItem> items, float totalPriceOfItems, Customer user) {
        this.items = items;
        this.totalPriceOfItems = totalPriceOfItems;
        this.owner = user;
        details = new OrderDetails(user);
        details.setFinalPrice(totalPriceOfItems);
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

    public Payment getPaymentMethod() {
        return paymentMethod;
    }

    public int getUsedLoyaltyPoints() {
        return usedLoyaltyPoints;
    }

    public Boolean redeemVoucher(String codeOfVoucher) {
        HashMap<String, Voucher> curr_vouchers = owner.getVouchers();
        Voucher used_voucher = curr_vouchers.get(codeOfVoucher);

        // If codeOfVoucher exists
        if (used_voucher != null) {
            // Add the used voucher
            usedVouchers.add(used_voucher);

            // Update the final price after applying the discount of Voucher
            details.setFinalPrice(
                    details.getFinalPrice() - used_voucher.getDiscount());

            return true;
        }
        else{
            return false;
        }
    }

    public Boolean redeemLoyaltyPoints(int numOfPoints) {

        // Check if there are enough loyalty points
        if (numOfPoints <= owner.getLoyaltyPoints()) {
            // Add the used loyalty points
            usedLoyaltyPoints += numOfPoints;

            // Update the owner loyalty points
            owner.setLoyaltyPoints(owner.getLoyaltyPoints() - numOfPoints);

            // Update the final price after applying the discount of points
            details.setFinalPrice(details.getFinalPrice() - numOfPoints);

            return true;
        }
        else
        {
            return false;
        }
    }

    public void payment() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your phone number: ");
        int phone = in.nextInt();

        String otp = AuthenticationService.getEmailSender().OTPGenerator();
        if (AuthenticationService.getEmailSender().sendOTP(
                owner.getUsername(), owner.getEmail(), otp, "ConfirmPhone")) {
            System.out.println("To complete the process, Please check your email.");
            System.out.print("Enter the OTP here: ");
            String entered_otp = in.nextLine();
            if (!entered_otp.equals(otp)) {
                System.out.println("Wrong OTP! Please Try Again.");
                payment();
            } else {
                details.setCustomerPhone(phone);
                paymentMethod = new CashOnDelivery(phone, details.getFinalPrice());
                System.out.println("Total price: " + details.getFinalPrice());
                System.out.println("Payment Method: " + paymentMethod.getPayMethod());
                details.setStatus(orderStatus.In_Process);
                System.out.println("Confirmed! your order is in process.");
            }
        }
    }

    public void placeOrder(Order order){
        System.out.println("To confirm the order, Please choose the shipping address: \n" +
                "1. Your Address: " + order.details.getAddress() + "\n"+
                "2. New Address");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        if( choice == 2 ){
            System.out.println("Enter the address: ");
            String new_address = in.nextLine();
            order.details.setAddress(new_address);
            order.details.setStatus(orderStatus.Out_to_delivery);
            order.details.setDate(new Date());
            owner.addOrder(order);
            System.out.println("Thank you, the delivery address has been set!" +
                    " Waiting for your opinion of our products.");
        }
    }

    public void displayOrderDetails(Order order){
        System.out.println("Order ID: " + order.details.getOrderID());
        System.out.println("Total price: " + order.details.getFinalPrice());
        System.out.println("Shipping Address: " + order.details.getAddress());
        if(order.details.getStatus() != null){
            System.out.println("Order Status: " + order.details.getStatus());
        }
        if(order.details.getStatus() != orderStatus.In_Process) {
            System.out.println("Created Date: " + order.details.getDate());
        }
        if (order.details.getCustomerPhone() != 0) {
            System.out.println("Date: " + order.details.getDate());
        }
    }
    public void changeAddress(String address) {
        details.setAddress(address);
    }
    public void reorder(Order order) {

    }
    public void cancelOrder(Order order) {
        Date current_date = new Date();
        int different = current_date.getHours() - order.details.getDate().getHours();
        if (different < 0) different*=-1;
        if(different <= 24){
            order.details.setStatus(orderStatus.Cancelled);
            System.out.println("Order is cancelled.");
        }
        else{
            System.out.println("Couldn't cancel this order.");
        }
    }

    public void displayMenu(){

    }

}
