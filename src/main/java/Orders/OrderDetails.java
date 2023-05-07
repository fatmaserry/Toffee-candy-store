package Orders;

import java.util.Date;

public class OrderDetails {
    private float finalPrice; // finalPrice stands for the price after discount (if there is)
    private static int id = 2345; // Initial value for the id, Increase with every order
    private final int orderID;
    private final String customerName;
    private final Date date;
    private orderStatus status;
    private String Address;
    private int customerPhone;

    OrderDetails(String name){
        this.customerName = name;
        this.orderID = id++;
        this.date= new Date();
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
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(int customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getOrderID() {
        return orderID;
    }
}
