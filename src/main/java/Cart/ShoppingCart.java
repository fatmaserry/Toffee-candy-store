package Cart;
import Products.*;
import Cart.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ShoppingCart{
    private int quantity;
    private float overallPrice;

    //to store class of cart items that the user adds to cart
    private ArrayList<CartItem> listOfClassCartItem= new ArrayList<>();



    public ShoppingCart() {}

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

        for(int i=0; i<listOfItems.size();i++){
            if(id==listOfItems.get(i).getItemId()){
                // if entered id is valid then add item to cart item list
                CartItem cartItem=new CartItem(listOfItems.get(i));
                listOfClassCartItem.add(cartItem);
                check=true;
            }
        }

        if (!check){
            System.out.println("Invalid item id\n");
        }else{
            System.out.println("Item added successfully\n");
        }

    }

    public void removeItemFromCart() {
        if (listOfClassCartItem.size() == 0) {
            System.out.println("Your cart is empty.\n");
        } else {
            System.out.println("Enter item id: ");
            Scanner in = new Scanner(System.in);
            int id = in.nextInt();
            boolean check = false;
            for (int i = 0; i < listOfClassCartItem.size(); i++) {
                if (id == listOfClassCartItem.get(i).getItem().getItemId()) {
                    // if entered id is valid then add item to cart item list
                    listOfClassCartItem.remove(i);
                    check = true;
                }
            }

            if (!check) {
                System.out.println("Invalid id or the item is not in cart.");
            } else {
                System.out.println("item removed successfully\n");
            }
        }
    }

    public void printCartDetails(){
        for (int i=0;i<listOfClassCartItem.size();i++){
            listOfClassCartItem.get(i).getItem().printItem();
        }
    }
}