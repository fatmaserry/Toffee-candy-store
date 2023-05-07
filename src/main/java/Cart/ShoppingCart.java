package Cart;
import Products.*;
import Cart.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ShoppingCart{
    private int quantity;
    private float overallPrice;
    private CartItem cartItem;



    private ArrayList<Item> listOfCartItems = new ArrayList<>(); //to store added items by user in it


    public ShoppingCart() {
        cartItem = new CartItem();
    }

    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int q){
        quantity=q;
    }

    public float getOverallPrice(){
        return overallPrice;
    }

    public void setOverallPrice(float p){
        overallPrice=p;
    }

    public void addItemToCart( HashMap<String, ArrayList<Item>> catalogItems) {

        // to get the map of items from catalog
        HashMap<String, ArrayList<Item>> itemMap = catalogItems;

        // to store all items from map in it
        ArrayList<Item> listOfItems = new ArrayList<>();

        // adding items from map to array list
        for (String s : itemMap.keySet()) {
            listOfItems.addAll(itemMap.get(s));
        }

        System.out.println("Enter item id: ");
        Scanner in = new Scanner(System.in);
        int id = in.nextInt();
        boolean check = false;

        // checking for entered id in the items
        for (Item item : listOfItems) {
            if (id == item.getItemId()) {
                // if entered id is valid then add item to cart item list
                listOfCartItems.add(item);
                check = true;

            }
        }
        if (!check){
            System.out.println("Invalid item id\n");
        }else{
            System.out.println("Item added successfully\n");
        }

    }

    public void removeItemFromCart(){
        System.out.println("Enter item id: ");
        Scanner in = new Scanner(System.in);
        int id = in.nextInt();
        boolean check = false;
        for(int i=0; i<listOfCartItems.size();i++){
            if(id==listOfCartItems.get(i).getItemId()){  //if entered id equals id in cart item
                listOfCartItems.remove(i);               //then remove cart item
                check=true;
            }
        }
        if(!check){
            System.out.println("Invalid id or the item is not in cart.");
        }else{
            System.out.println("item removed successfully\n");
        }
    }

    public void printCartDetails(){
        for (Item listOfCartItem : listOfCartItems) {
            listOfCartItem.printItem();
        }
    }
}