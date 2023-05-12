package Orders;
import App.*;
import Cart.CartItem;
import Products.*;
import User.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.time.Duration;
import java.time.LocalDateTime;


public class Order {
    private OrderDetails details;
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
        this.details = new OrderDetails(user,totalPriceOfItems);
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

    public ArrayList<Voucher> getUsedVouchers() {
        return usedVouchers;
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

        System.out.print("Enter code of voucher: ");
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
            System.out.print("\n\nTo Complete the Process, Please check your email.\n\n");
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

                System.out.println("\n\nConfirmed! your order is in process.\n\n");
                return true;
            }
        }
        System.out.println("There is a problem, Please try again");
        return false;
    }

    // PlaceOrder function to choose the shipping address,
    // save order in owner OrderList,
    // place order to be out to delivered status
    public boolean placeOrder(Order order){
        String new_address;

        System.out.println("To confirm the order, Please choose the shipping address: \n" +
                "1. Your Address: " + order.details.getAddress() + "\n"+
                "2. New Address");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();

        // Enter shipping address
        if( choice == 2 ){
            System.out.println("Enter the address: ");
            new_address = in.nextLine();

            // set address of order
            order.details.setAddress(new_address);
        }
        if (choice == 2 || choice == 1 ){

            // change status of order
            order.details.setStatus(orderStatus.Out_to_delivery);

            // set created date
            order.details.setDate(LocalDateTime.now());

            // add order to customer list
            owner.addOrder(order.details.getOrderID(),order);

            // increase the owner loyalty points as one pound = one point
            owner.setLoyaltyPoints((int) order.details.getFinalPrice());

            System.out.println("\n\nThank you, the delivery address has been set!" +
                    "\nWaiting for your opinion of our products.\n\n");

            return true;
        }
        else{
            System.out.println("\n\nInvalid option. Try again\n\n");
            return false;
        }
    }


    // check if order can be cancelled after ordered it
    public boolean canBeCancelled() {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(details.getDate(), now);
        long hoursElapsed = duration.toHours();
        return hoursElapsed < 24;
    }

    // cancelOrder to cancel the order
    public void cancelOrder(Order order) {
        if(order.canBeCancelled()){
            order.details.setStatus(orderStatus.Cancelled);

            // return the used vouchers
            if (order.getUsedVouchers() != null) {
                for (Voucher i : order.getUsedVouchers()) {
                    order.getOwner().getVouchers().put(i.getVoucherCode(), i);
                }
            }
            // return the used loyalty points
            if (order.getUsedLoyaltyPoints() != 0) {

                order.getOwner().setLoyaltyPoints(order.getOwner().getLoyaltyPoints() +
                        order.getUsedLoyaltyPoints());
            }

            // return gained loyalty points
            order.getOwner().setLoyaltyPoints((int) (order.getOwner().getLoyaltyPoints() -
                                order.getDetails().getFinalPrice()));

            System.out.println("\n\nOrder has been cancelled\n\n");
        }
        else{
            System.out.println(
                    "You can't cancel this order." +
                    "\nIt has been 24 hours since you ordered it");
        }
    }

    public void isDelivered(){
        if (!this.canBeCancelled()) {
            this.details.setStatus(orderStatus.Closed);

            // loop to check on vouchers and add to owner vouchers
            for (CartItem x : this.items) {
                if (x.getItem().getType() == ItemType.Voucher)
                    this.owner.addVoucher(x.getItem().getItemId(), (Voucher) x.getItem());
            }
        }
    }

}
