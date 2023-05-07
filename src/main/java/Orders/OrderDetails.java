package Orders;

import User.Customer;

import java.util.Date;

public class OrderDetails {
    private float finalPrice; // finalPrice stands for the price after discount (if there is)
    private static int id = 2345; // Initial value for the id, Increase with every order
    private final int orderID;
    private final String customerName;
    private Date date;
    private orderStatus status = null;
    private String address;
    private int customerPhone = 0;

    OrderDetails(Customer user){
        this.customerName = user.getUsername();
        this.orderID = id++;
        this.address = user.getAddress();
    }

    public int getOrderID() {
        return orderID;
    }

    public float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(float finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public orderStatus getStatus() {
        return status;
    }

    public void setStatus(orderStatus status) {
        this.status = status;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(int customerPhone) {
        this.customerPhone = customerPhone;
    }

}
