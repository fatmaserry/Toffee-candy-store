package Orders;
import App.AuthenticationService;
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
    private Customer customer;
    private ArrayList<CartItem> items;

    public Order(ArrayList<CartItem> items, float totalPriceOfItems, Customer user) {
        this.items = items;
        this.totalPriceOfItems = totalPriceOfItems;
        this.customer = user;
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

    public Customer getCustomer() {
        return customer;
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

    /**
     * this method asks the customer if he wants to use his vouchers
     * to have a discount over the total price of order
     */
    public void redeemVoucher() {
        HashMap<Integer, Voucher> curr_vouchers = customer.getVouchers();

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
            customer.getVouchers().remove(codeOfVoucher);

            // Update the final price after applying the discount of Voucher
            details.setFinalPrice(
                    details.getFinalPrice() - used_voucher.getDiscount());

            System.out.println("Done! you got a discount");
        }
    }

    /**
     * this method asks the customer if he wants to use his loyalty points
     * to have a discount over the total price of order
     */
    public void redeemLoyaltyPoints() {

        // If customer hasn't loyalty points
        if (customer.getLoyaltyPoints() == 0) {
            System.out.println("You have not loyalty points to use!");
            return;
        }
        System.out.println("Your loyalty points: " + customer.getLoyaltyPoints());
        System.out.println("Enter the amount : ");

        Scanner in = new Scanner(System.in);
        int numOfPoints = in.nextInt();

        // Check if there are enough loyalty points
        if (numOfPoints <= customer.getLoyaltyPoints()) {
            // Add the used loyalty points
            usedLoyaltyPoints += numOfPoints;

            // Update the owner loyalty points
            customer.setLoyaltyPoints(customer.getLoyaltyPoints() - numOfPoints);

            // Update the final price after applying the discount of points
            // As one point = one pound
            details.setFinalPrice(details.getFinalPrice() - numOfPoints);
        }
        else
        {
            System.out.println("No enough loyalty points");
        }
    }

    /**
     * @return boolean
     * this method sets the payment details after confirming a valid
     * phone number of the customer
     * return true if the payment process is done and false if it isn't
     */
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
                customer.getUsername(), customer.getEmail(), otp, "ConfirmPhone")) {
            System.out.print("\n\nTo Complete the Process, Please check your email.\n\n");
            System.out.print("Enter the OTP here: ");
            String entered_otp = in.nextLine();

            // if customer enter a wrong otp
            if (!entered_otp.equals(otp)) {
                System.out.println("Wrong OTP! Please Try Again.");
                return false;

            } else {
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


    /**
     * @return boolean
     * this method takes the Order object as parameter
     * to confirm it by choosing the shipping address
     * and place order to be out to delivered status
     * return true if user chooses his default address
     * or enter a new address
     */
    public boolean placeOrder() {
        String new_address;

        System.out.println("To confirm the order, Please choose the shipping address: \n" +
                "1. Your Address: " + details.getAddress() + "\n" +
                "2. New Address");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();

        if (choice != 1 && choice != 2) {
            System.out.println("\n\nInvalid option. Try again\n\n");
            return false;
        }
        // Enter shipping address
        if (choice == 2) {
            System.out.println("Enter the address: ");
            in.nextLine();
            new_address = in.nextLine();
            // set address of order
            details.setAddress(new_address);
        }
        // change status of order
        details.setStatus(orderStatus.Out_to_delivery);

        // set created date
        details.setDate(LocalDateTime.now());

        // add order to orders customer list
        customer.addOrder(details.getOrderID(), this);

        // increase the customer loyalty points
        customer.setLoyaltyPoints((int) details.getFinalPrice() / 10 );

        // for testing only, adding vouchers after place order to customer
//        for (CartItem x : this.items) {
//            if (x.getItem().getType() == ItemType.Voucher)
//                customer.addVoucher(x.getItem().getItemId(), (Voucher) x.getItem());
//        }

        System.out.println("\n\nThank you, the delivery address has been set!" +
                "\nWaiting for your opinion of our products.\n\n");
        return true;
    }


    /**
     * @return boolean
     * this method checks if order can be cancelled after user ordered it
     */
    public boolean canBeCancelled() {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(details.getDate(), now);
        long hoursElapsed = duration.toHours();
        return hoursElapsed < 24;
    }

    /**
     * this method cancels the order if it is valid
     */
    public void cancelOrder() {
        if(this.canBeCancelled()){
            this.details.setStatus(orderStatus.Cancelled);

            // return the used vouchers
            if (this.getUsedVouchers() != null) {
                for (Voucher i : this.getUsedVouchers()) {
                    this.getCustomer().getVouchers().put(i.getVoucherCode(), i);
                }
            }
            // return the used loyalty points
            if (this.getUsedLoyaltyPoints() != 0) {

                this.getCustomer().setLoyaltyPoints(this.getCustomer().getLoyaltyPoints() +
                        this.getUsedLoyaltyPoints());
            }

            // return gained loyalty points
            this.getCustomer().setLoyaltyPoints((int) (this.getCustomer().getLoyaltyPoints() -
                                this.getDetails().getFinalPrice()));

            System.out.println("\n\nOrder has been cancelled\n\n");
        }
        else{
            System.out.println(
                    "You can't cancel this order." +
                    "\nIt has been 24 hours since you ordered it");
        }
    }

    /**
     * this method checks if the order can not be cancelled ,
     * it changes the status of order to "Closed" as it has been delivered to customer
     */
    public void isDelivered(){
        if (!this.canBeCancelled()) {
            this.details.setStatus(orderStatus.Closed);

            // loop to check on vouchers and add to owner vouchers
            for (CartItem x : this.items) {
                if (x.getItem().getType() == ItemType.Voucher)
                    this.customer.addVoucher(x.getItem().getItemId(), (Voucher) x.getItem());
            }
        }
    }

}
