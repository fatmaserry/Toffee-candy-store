package Cart;
import Orders.OrderManager;
import Products.*;
import User.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ShoppingCart{
    private int quantity;
    private float overallPrice;
    private Customer owner;

    // to store class of cart items that the user adds to cart
    private ArrayList<CartItem> listOfClassCartItem;

    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int q){
        quantity = q ;
    }
    public float getOverallPrice(){
        return overallPrice;
    }
    public void setOverallPrice(float p){
        overallPrice = p;
    }

    public ShoppingCart(Customer user) {
        this.listOfClassCartItem = new ArrayList<CartItem>();
        this.quantity = 0;
        this.overallPrice=0;
        this.owner = user;
    }
    public void addItemToCart( HashMap<String, ArrayList<Item>> catalogItems ) {

        // to get the map of items from catalog
        HashMap<String, ArrayList<Item>> itemMap = catalogItems;

        // to store all items from map in it
        ArrayList<Item> listOfItems = new ArrayList<>();

        // adding items from map to array list
        for (String s : itemMap.keySet()) {
            listOfItems.addAll(itemMap.get(s));
        }

        System.out.println("Choose the number of the option you want: ");
        System.out.println("1-Add to cart");
        System.out.println("2-Back to menu");
        Scanner in = new Scanner(System.in);
        int option = in.nextInt();

        if ( option == 1 ) {
            System.out.println("Enter item id: ");
            int id = in.nextInt();

            boolean check = false;

            for (int i = 0; i < listOfItems.size(); i++) {
                if (id == listOfItems.get(i).getItemId()) {
                    check = true;

                    ItemType Loose=ItemType.Loose;  //sold by weight
                    ItemType Sealed=ItemType.Sealed; //sold by item

                    if(listOfItems.get(i).getType()==Loose){
                        System.out.println("Enter how many kilos do you want to buy: ");
                    }else if(listOfItems.get(i).getType()==Sealed){
                        System.out.println("Enter how many items do you want to buy: ");
                    }else{
                        System.out.println("Enter how many vouchers do you want to buy: ");
                    }
                    int quantity = in.nextInt();
                    //checking if entered quantity is valid
                    while (quantity<=0 || quantity>50){
                        System.out.println("Invalid quantity, Try again .\n");
                        quantity = in.nextInt();
                    }
                    // if entered id is valid then add item to cart item list
                    CartItem cartItem = new CartItem(listOfItems.get(i),quantity);
                    owner.getCart().getListOfClassCartItem().add(cartItem);

                    // add to overall price the total price in cartItem
                    owner.getCart().setOverallPrice(owner.getCart().getOverallPrice() + cartItem.getTotalPrice());

                    // Increase the quantity
                    owner.getCart().setQuantity(owner.getCart().getQuantity()+1);
                }
            }
            if (!check){
                System.out.println("Invalid item id\n");
            }else{
                System.out.println("Item added successfully\n");
            }
        }
    }

    public void removeItemFromCart() {
        if (owner.getCart().getQuantity() == 0) {
            System.out.println("Your cart is empty");
            System.out.println("Be sure to fill your cart with something you like");
        } else {
            System.out.println("Enter item id: ");
            Scanner in = new Scanner(System.in);
            int id = in.nextInt();
            boolean check = false;
            for (int i = 0; i < owner.getCart().getListOfClassCartItem().size(); i++) {

                if (id == owner.getCart().getListOfClassCartItem().get(i).getItem().getItemId()) {

                    // firstly decrease from overall price the unit price
                    owner.getCart().overallPrice -= owner.getCart().getListOfClassCartItem().get(i).getTotalPrice();

                    // if entered id is valid then remove cart item from cart item list
                    owner.getCart().getListOfClassCartItem().remove(i);

                    // decrease the quantity of shopping cart
                    owner.getCart().setQuantity(owner.getCart().getQuantity()-1);
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

    //This function needs to be modified
    // We need to print the overall price per item in Item cart
    //We need to print the quantity of each item in cart
    //Then we need to print at last the quantity and the total price of the whole shopping cart
    public void printCartDetails(){
        System.out.println("\n  -------CART------");
        if ( owner.getCart().getQuantity() == 0 ){
            System.out.println("  Your cart is empty");
            System.out.println("Be sure to fill your cart with something you like\n\n");
        }
        else {
            for (int i = 0; i < owner.getCart().getListOfClassCartItem().size(); i++) {
                owner.getCart().getListOfClassCartItem().get(i).getItem().printItem();
            }
        }
    }

    public void checkout(){
       OrderManager manager = new OrderManager();
       manager.createOrder(owner.getCart().getListOfClassCartItem(), owner.getCart().getOverallPrice(),owner);
       manager.displayMenu();
    }

    public ArrayList<CartItem> getListOfClassCartItem(){
        return listOfClassCartItem;
    }

    public void clearListOfClassCartItem(){
        listOfClassCartItem.clear();
        quantity = 0;
    }
}