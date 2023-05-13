package Orders;

import java.util.Scanner;

public class CashOnDelivery extends Payment{
    private int phoneNumber = 0;


    /**
     * @param phone
     * @param price
     * Parametrized constructor for payment
     * initialize price as total price of order,
     * phone number of customer and payment method
     */
    CashOnDelivery(int phone, float price){
        super();
        setPrice(price);
        setPayMethod("CashOnDelivery");
        this.phoneNumber = phone;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
