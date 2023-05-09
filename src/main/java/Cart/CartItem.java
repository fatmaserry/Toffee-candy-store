package Cart;

import Products.*;
public class CartItem {
    private int quantity;
    private float totalPrice;
    private Item item;
    public CartItem(Item item) {
           this.item=item;
}
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
        totalPrice=p*quantity;     //calculate the overall price of the items
    }
    public void setItem(Item it){    //to set item of CartItem
        this.item=it;
    }
    public Item getItem(){     //to return item of CartItem
        return this.item;
    }

}
