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
    private Catalog catalog;



    private ArrayList<Item> listOfCartItems = new ArrayList<>(); //to store added items by user in it


    ShoppingCart() {
        cartItem = new CartItem();
        catalog = new Catalog();
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

    public void addItemToCart() {

        // to get the map of items from catalog
        HashMap<String, ArrayList<Item>> itemMap = catalog.getItems();

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
        }

    }

    public void removeItemFromCart(){
        System.out.println("Enter item id: ");
        Scanner in = new Scanner(System.in);
        int id = in.nextInt();
        boolean check = false;
        for (Item listOfCartItem : listOfCartItems) {
            // if entered id equals id in cart item
            if(id == listOfCartItem.getItemId()) {
                // remove cart item
                listOfCartItems.remove(listOfCartItem);
                check = true;
            }
        }
        if(!check){
            System.out.println("Invalid id or the item is not in cart.");
        }
    }

    public void printCartDetails(){
        for (Item listOfCartItem : listOfCartItems) {
            listOfCartItem.printItem();
        }
    }
}