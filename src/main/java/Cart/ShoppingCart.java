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


    public ShoppingCart() {


        cartItem = new CartItem();
        catalog = new Catalog();


    }

    public int getQunatity(){
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

        HashMap<String, ArrayList<Item>> itemMap = catalog.getItems();  //to get the map of items from catalog
        ArrayList<Item> listOfItems = new ArrayList<>();     //to store all items from map in it

        for (String s : itemMap.keySet()) {   //adding items from map to array list
            for (Item i : itemMap.get(s)) {
                listOfItems.add(i);
            }
        }



        System.out.println("Enter item id\n");
        Scanner in = new Scanner(System.in);
        int id = in.nextInt();
        boolean check=false;
        for(int i=0;i<listOfItems.size();i++) {  //checking for entered id in the items
            if(id==listOfItems.get(i).getItemId()){
                listOfCartItems.add(listOfItems.get(i));   //if valid id then add item to cart item list
                check=true;
            }
        }
        if (check==false){
            System.out.println("Invalid item id\n");
        }

    }
    public void removeItemFromCart(){
        System.out.println("Enter item id\n");
        Scanner in = new Scanner(System.in);
        int id = in.nextInt();

        boolean check=false;
        for(int i=0; i<listOfCartItems.size();i++){
            if(id==listOfCartItems.get(i).getItemId()){  //if entered id equals id in cart item
                listOfCartItems.remove(i);               //then remove cart item
                check=true;
            }
        }
        if(check==false){
            System.out.println("Invalid id or the item in not in cart\n");
        }
    }

    public void printCartDetails(){
        for(int i=0;i<listOfCartItems.size();i++) {
            listOfCartItems.get(i).printItem();
        }
    }
}