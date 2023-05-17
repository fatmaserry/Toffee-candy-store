package Orders;

import Cart.CartItem;
import Products.Voucher;
import User.Customer;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderManager {
    Order order; // pending order

    /**
     * @param owner registered customer
     * @param cartItems items of shopping cart
     * @param totalPriceOfItems
     * this method create a new order and set order as pending one
     */
    public void createOrder(ArrayList<CartItem> cartItems, float totalPriceOfItems, Customer owner) {
        this.order = new Order(cartItems, totalPriceOfItems, owner);
    }

    /**
     * this method displays the details of pending order
     */
    public void displayOrderDetails() {
        displayOrderItems();
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

    /**
     * @param order
     * this method is an overload of displayOrderDetails method
     * using it to display the details of a given order
     */
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

    /**
     * this method shows a menu to customer during payment process
     */
    public boolean displayMenu() {
        int flag = 0;
        while (flag == 0) {
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
                        if (order.placeOrder()) {
                            order.getCustomer().getCart().clearListOfClassCartItem();
                            flag = 1;
                        }
                    }
                }
                case 4 -> {
                    if (order.getUsedVouchers() != null) {
                        // return the used vouchers
                        for (Voucher i : order.getUsedVouchers()) {
                            order.getCustomer().getVouchers().put(i.getVoucherCode(), i);
                        }
                    }
                    if (order.getUsedLoyaltyPoints() != 0) {
                        // return the used loyalty points
                        order.getCustomer().setLoyaltyPoints(order.getCustomer().getLoyaltyPoints() + order.getUsedLoyaltyPoints());
                    }
                    // break while loop
                    flag = 2;
                }
                default -> {
                }
            }
        }
        return flag == 1 ;
    }

    /**
     * this method displays the items of pending order
     */
    public void displayOrderItems() {
        for (CartItem x : order.getItems()) {
            x.printItem();
        }
    }
    /**
     * @param order Order object
     * this method displays the items of a given order
     */
    public void displayOrderItems(Order order) {
        for (CartItem x : order.getItems()) {
            x.printItem();
        }
    }

    /**
     * @param owner registered customer
     * this method displays orders history of the owner
     */
    public boolean viewOrders(Customer owner) {
        if (owner.getOrders().isEmpty()){
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

    /**
     * @param currentCustomer registered customer
     * this method asks the customer about id of order
     * that he wants to cancel then checks if it is valid
     * if it is valid, it cancels the order
     */
    public void toCancel(Customer currentCustomer){
        if (viewOrders(currentCustomer)) {
            System.out.println("0: Exit");
            System.out.print("Enter the order id which you want to cancel: ");
            Scanner in = new Scanner(System.in);
            int id = in.nextInt();
            if (currentCustomer.getOrders().get(id) == null && id != 0) {
                System.out.println("Invalid Order");
            } else if (id == 0){
                return;
            }else{
                if (currentCustomer.getOrders().get(id).canBeCancelled()) {
                    currentCustomer.getOrders().get(id).cancelOrder();
                }
            }
        }
    }

}