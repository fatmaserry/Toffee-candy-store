package Cart;

import Products.*;
public class CartItem {
    private int quantity;
    private float totalPrice;
    private Item item;
    public CartItem(Item item, int q) {
           this.item=item;
           quantity=q;
           totalPrice=item.getUnitPrice()*quantity;  //automatically set total price  based on the entered quantity
}
    public float getTotalPrice(){
        return totalPrice;
    }
    public void setTotalPrice(float p){  //enter the product price per unit
        totalPrice=p*quantity;     //calculate the overall price of the items
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int q){
        quantity=q;
    }

    public void setItem(Item it){    //to set item of CartItem
        this.item=it;
    }
    public Item getItem(){     //to return item of CartItem
        return this.item;
    }

    public void printItem(){
        System.out.println("---------------------------------------------------------------------------");
        System.out.println(String.format("| item id: %-63s", item.getItemId()) + "|");
        System.out.println(String.format("| name: %-66s", item.getName()) + "|");
        int cnt = 0;
        for(String i: item.getDescription().split("\n")){
            if(cnt == 0)
                System.out.println(String.format("| description: %-59s", i) + "|");
            else
                System.out.println(String.format("| %-72s", i) + "|");
            cnt++;
        }
        System.out.println(String.format("| price: %-65s", item.getUnitPrice()) + "|");
        System.out.println(String.format("| quantity: %-62s", getQuantity()) + "|");
        System.out.println(String.format("| total price: %-59s", getTotalPrice()) + "|");
        System.out.println("---------------------------------------------------------------------------");
    }

}
