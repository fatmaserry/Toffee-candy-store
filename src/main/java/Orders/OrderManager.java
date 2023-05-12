package Orders;

import Cart.CartItem;
import Products.Voucher;
import User.Customer;
import java.util.ArrayList;
import java.util.Scanner;

import static App.AuthenticationService.*;

public class OrderManager {
    Order order;

    public void createOrder(ArrayList<CartItem> cartItems, float totalPriceOfItems, Customer owner) {
        this.order = new Order(cartItems, totalPriceOfItems, owner);
    }

    public void displayOrderDetails() {
        System.out.println("\n\n-------------------------------------------------");
        System.out.println("Order ID: " + order.getDetails().getOrderID());

        // If customer got a discount
        if (order.getDetails().getFinalPrice() > order.getTotalPriceOfItems()) {
            System.out.println("Total price before discount: " + order.getTotalPriceOfItems());
            System.out.println("Total price after discount: " + order.getDetails().getFinalPrice());
        } else {
            System.out.println("Total price: " + order.getDetails().getFinalPrice());
        }

        // print shipping address
        System.out.println("Shipping Address: " + order.getDetails().getAddress());

        // If status of order changed
        if (order.getDetails().getStatus() != null) {
            System.out.println("Order Status: " + order.getDetails().getStatus());
        }

        // if ordered is confirmed so its date is assigned
        if (order.getDetails().getDate() != null) {
            System.out.println("Created Date: " + order.getDetails().getDate());
        }

        // if customer sets his/her phone number
        if (order.getDetails().getCustomerPhone() != 0) {
            System.out.println("Phone: " + order.getDetails().getCustomerPhone());
        }

        // if payment process is done
        if (order.getPaymentMethod() != null) {
            System.out.println("Payment Method: " + order.getPaymentMethod().getPayMethod());
        }
        System.out.println("-------------------------------------------------\n\n");

    }

    // overload of displayOrderDetails()
    public void displayOrderDetails(Order order) {
        System.out.println("\n\n-------------------------------------------------");
        System.out.println("Order ID: " + order.getDetails().getOrderID());

        // If customer got a discount
        if (order.getDetails().getFinalPrice() > order.getTotalPriceOfItems()) {
            System.out.println("Total price before discount: " + order.getTotalPriceOfItems());
            System.out.println("Total price after discount: " + order.getDetails().getFinalPrice());
        } else {
            System.out.println("Total price: " + order.getDetails().getFinalPrice());
        }

        // print shipping address
        System.out.println("Shipping Address: " + order.getDetails().getAddress());

        // If status of order changed
        if (order.getDetails().getStatus() != null) {
            System.out.println("Order Status: " + order.getDetails().getStatus());
        }

        // if ordered is confirmed so its date is assigned
        if (order.getDetails().getDate() != null) {
            System.out.println("Created Date: " + order.getDetails().getDate());
        }

        // if customer sets his/her phone number
        if (order.getDetails().getCustomerPhone() != 0) {
            System.out.println("Phone: " + order.getDetails().getCustomerPhone());
        }

        // if payment process is done
        if (order.getPaymentMethod() != null) {
            System.out.println("Payment Method: " + order.getPaymentMethod().getPayMethod());
        }
        System.out.println("-------------------------------------------------\n\n");

    }

    public void displayMenu() {
        boolean flag = true;
        while (flag) {
            displayOrderDetails();
            System.out.println("Choose the number of the option you want: ");
            System.out.println("1-Redeem Vouchers");
            System.out.println("2-Redeem Loyalty Points");
            System.out.println("3-Payment");
            System.out.println("4-Exit");
            Scanner in = new Scanner(System.in);
            int option = in.nextInt();
            switch (option) {
                case 1 -> {
                    order.redeemVoucher();
                }
                case 2 -> {
                    order.redeemLoyaltyPoints();
                }
                case 3 -> {
                    if (order.payment()) {
                        // if payment and placeOrder process are done
                        // then clear the shopping cart
                        // and break the while loop (ORDER CONFIRMED)
                        if (order.placeOrder(order)) {
                            order.getOwner().getCart().clearListOfClassCartItem();
                            flag = false;
                        }
                    }
                }
                case 4 -> {
                    if (order.getUsedVouchers() != null) {
                        // return the used vouchers
                        for (Voucher i : order.getUsedVouchers()) {
                            order.getOwner().getVouchers().put(i.getVoucherCode(), i);
                        }
                    }
                    if (order.getUsedLoyaltyPoints() != 0) {
                        // return the used loyalty points
                        order.getOwner().setLoyaltyPoints(order.getOwner().getLoyaltyPoints() +
                                order.getUsedLoyaltyPoints());
                    }
                    // break while loop
                    flag = false;
                }
                default -> {
                }
            }
        }
    }

    public void displayOrderItems(Order ord) {
        for (CartItem x : ord.getItems()) {
            x.getItem().printItem();
        }
    }

    public boolean viewOrders(Customer owner) {
        if (owner.getOrders() == null){
            System.out.println("\n\n No Order History\n Hurry up and buy some sweets!\n\n");
            return false;
        }
        else{
            for (Integer id : owner.getOrders().keySet()) {
                Order x = owner.getOrders().get(id);
                displayOrderItems(x);
                x.isDelivered();
                displayOrderDetails(x);
            }
            return true;
        }
    }

    public void toCancel(Customer owner){
        if (viewOrders(owner)) {
            System.out.println("0: Exit");
            System.out.print("Enter the order id which you want to cancel: ");
            Scanner in = new Scanner(System.in);
            int id = in.nextInt();
            if (owner.getOrders().get(id) == null && id != 0) {
                System.out.println("Invalid Order");
            } else {
                if (owner.getOrders().get(id).canBeCancelled()) {
                    owner.getOrders().get(id).cancelOrder(owner.getOrders().get(id));
                }
            }
        }
    }

}