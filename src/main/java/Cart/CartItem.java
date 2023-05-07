package Cart;

import Products.*;
public class CartItem {
    private int quantity;
    private float totalPrice;


    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int q){
        quantity=q;
    }
    public float getTotalPrice(){
        return totalPrice;
    }
    public void setTotalPrice(float p){  //enter the product price per unit
        totalPrice=p*quantity;          //calculate the overall price of the items
    }

}
