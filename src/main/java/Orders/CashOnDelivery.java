package Orders;

import java.util.Scanner;

public class CashOnDelivery extends Payment{
    private int phoneNumber = 0;

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
