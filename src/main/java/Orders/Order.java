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

    public Order(ArrayList<CartItem> items, float totalPriceOfItems, Customer user) {
        this.items = items;
        this.totalPriceOfItems = totalPriceOfItems;
        this.owner = user;
        this.paymentMethod = null;
        this.usedVouchers = null;
        this.usedLoyaltyPoints = 0;
        details = new OrderDetails(user,totalPriceOfItems);
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

    public void redeemVoucher() {
        HashMap<Integer, Voucher> curr_vouchers = owner.getVouchers();

        // If customer has no vouchers
        if (curr_vouchers == null) {
            System.out.println("You have not any vouchers!");
            return;
        }
        System.out.println("      --------YOUR VOUCHERS---------");
        for (Integer s : curr_vouchers.keySet()) {
            curr_vouchers.get(s).printItem();
            System.out.println("Voucher Code: " + curr_vouchers.get(s).getVoucherCode());
            System.out.println("--------------------------------------------------------");
        }

        System.out.println("Enter code of voucher: ");
        Scanner in = new Scanner(System.in);
        int codeOfVoucher = in.nextInt();

        // Enter a wrong code
        for (int s : curr_vouchers.keySet()) {
            if (curr_vouchers.get(s).getVoucherCode() != codeOfVoucher) {
                System.out.println("Wrong Code. Try Again");
                return;
            }
        }

        // Enter a correct code
        Voucher used_voucher = curr_vouchers.get(codeOfVoucher);

        // If codeOfVoucher exists
        if (used_voucher != null) {
            // Add the used voucher
            usedVouchers.add(used_voucher);

            // Delete used voucher from user
            owner.getVouchers().remove(codeOfVoucher);

            // Update the final price after applying the discount of Voucher
            details.setFinalPrice(
                    details.getFinalPrice() - used_voucher.getDiscount());

            System.out.println("Done! you got a discount");
        }
    }

    public void redeemLoyaltyPoints() {

        // If customer hasn't loyalty points
        if (owner.getLoyaltyPoints() == 0) {
            System.out.println("You have not loyalty points to use!");
            return;
        }
        System.out.println("Your loyalty points: " + owner.getLoyaltyPoints());
        System.out.println("Enter the : " + owner.getLoyaltyPoints());

        Scanner in = new Scanner(System.in);
        int numOfPoints = in.nextInt();

        // Check if there are enough loyalty points
        if (numOfPoints <= owner.getLoyaltyPoints()) {
            // Add the used loyalty points
            usedLoyaltyPoints += numOfPoints;

            // Update the owner loyalty points
            owner.setLoyaltyPoints(owner.getLoyaltyPoints() - numOfPoints);

            // Update the final price after applying the discount of points
            // As one point = one pound
            details.setFinalPrice(details.getFinalPrice() - numOfPoints);
        }
        else
        {
            System.out.println("No enough loyalty points");
        }
    }

    public boolean payment() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your phone number: ");
        String phone = in.nextLine();

        // regex to check validation of phone number
        String regex = "^01?[0125][0-9]{8}$";
        if (!phone.matches(regex)) {
            System.out.println("Phone number is invalid. Try again");
            return false;
        }

        // Confirm payment with email address
        String otp = AuthenticationService.getEmailSender().OTPGenerator();
        if (AuthenticationService.getEmailSender().sendOTP(
                owner.getUsername(), owner.getEmail(), otp, "ConfirmPhone")) {
            System.out.println("To complete the process, Please check your email.");
            System.out.print("Enter the OTP here: ");
            String entered_otp = in.nextLine();

            // if customer enter a wrong otp
            if (!entered_otp.equals(otp)) {
                System.out.println("Wrong OTP! Please Try Again.");
                return false;

            }
            else {
                // set the phone number
                details.setCustomerPhone(Integer.parseInt(phone));

                // create a cash on delivery object
                paymentMethod = new CashOnDelivery(Integer.parseInt(phone), details.getFinalPrice());

                // change the status of order to be in process
                details.setStatus(orderStatus.In_Process);
                System.out.println("Confirmed! your order is in process.");
                return true;
            }
        }
        System.out.println("There is a problem, Please try again");
        return false;
    }

    public boolean placeOrder(Order order){
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
                    "\nWaiting for your opinion of our products.");

            return true;
        }else{
            System.out.println("Invalid option. Try again");
            return false;
        }
    }

    public void displayOrderDetails(Order order){
        System.out.println("\n\n-------------------------------------------------");
        System.out.println("Order ID: " + order.details.getOrderID());

        // If customer got a discount
        if ( order.details.getFinalPrice() > order.totalPriceOfItems){
            System.out.println("Total price before discount: " + order.totalPriceOfItems);
            System.out.println("Total price after discount: " + order.details.getFinalPrice());
        }
        else {
            System.out.println("Total price: " + order.details.getFinalPrice());
        }

        // print shipping address
        System.out.println("Shipping Address: " + order.details.getAddress());

        // If status of order changed
        if(order.details.getStatus() != null){
            System.out.println("Order Status: " + order.details.getStatus());
        }

        // if ordered is confirmed so its date is assigned
        if(order.details.getDate() != null) {
            System.out.println("Created Date: " + order.details.getDate());
        }

        // if customer sets his/her phone number
        if (order.details.getCustomerPhone() != 0) {
            System.out.println("Phone: " + order.details.getCustomerPhone());
        }

        // if payment process is done
        if (order.paymentMethod != null){
            System.out.println("Payment Method: " + paymentMethod.getPayMethod());
        }
        System.out.println("-------------------------------------------------\n\n");

    }
    public void changeAddress(){
        System.out.println("Enter your new address: ");
        Scanner in = new Scanner(System.in);
        String address = in.nextLine();
        details.setAddress(address);
        System.out.println("The address has been set successfully");
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
            System.out.println("You can't cancel this order." +
                    "\nIt's been 24 hours since you ordered it");
        }
    }

    public void displayMenu(Order pending_order){
        boolean flag = true;
        while (flag){
            displayOrderDetails(pending_order);
            System.out.println("Choose the number of the option you want: ");
            System.out.println("1-Redeem Vouchers");
            System.out.println("2-Redeem Loyalty Points");
            System.out.println("3-Payment");
            System.out.println("4-Cancel order");
            Scanner in = new Scanner(System.in);
            int option = in.nextInt();
            switch(option){
                case 1:
                    pending_order.redeemVoucher();
                    break;
                case 2:
                    pending_order.redeemLoyaltyPoints();
                    break;
                case 3:
                    if (pending_order.payment()) {
                        // if payment and placeOrder process are done
                        // then clear the shopping cart
                        // and break the while loop (ORDER CONFIRMED)
                        if (pending_order.placeOrder(pending_order)) {
                            owner.getCart().clearListOfClassCartItem();
                            flag = false;
                        }
                    }
                    break;
                case 4:
                    if( usedVouchers != null ){
                        // return the used vouchers
                        for (Voucher i : usedVouchers){
                            owner.getVouchers().put(i.getVoucherCode(),i);
                        }
                    }
                    if ( usedLoyaltyPoints != 0){
                        // return the used loyalty points
                        owner.setLoyaltyPoints(owner.getLoyaltyPoints() + usedLoyaltyPoints);
                    }
                    // break while loop
                    flag = false;
                    break;
                default:
                    break;
            }
        }
    }

//        public void reorder(Order order) {
//
//    }
}
