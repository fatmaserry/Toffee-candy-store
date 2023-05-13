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
    private Customer customer;

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

    /**
     * This is the constructor of the class that takes parameter user from class Customer
     * and sets it to its private attribute customer.
     *
     * it also sets quantity and overallPrice by default to zero
     * it create listOFClassCartItem by setting it to its default constructor ArrayList<>()
     *
     * @param user input of  type Item Customer that sets private attribute customer to its value
     */
    public ShoppingCart(Customer user) {
        this.listOfClassCartItem = new ArrayList<>();
        this.quantity = 0;
        this.overallPrice=0;
        this.customer = user;
    }

    /**
     * This method takes a Hashmap as a parameter that  contains all items info
     * Then we move items from Hashmap to ArrayList
     * Then it asks user to add id of item to add
     * Then asks the user to enter the quantity of item he/she wants
     * Then it checks if the id is in the ArrayList of items
     * if true, then it creates CartItem object and add item and quantity to its constructor
     * Then it adds the CartItem object to the private ArrayList listOfClassCartItem, then done, item is addes
     * if false, then it prints "Invalid Item ID"
     *
     * @param catalogItems is HashMap that contains all items info
     */
    public void addItemToCart( HashMap<String, ArrayList<Item>> catalogItems ) {

        // to store all items from map in it
        ArrayList<Item> listOfItems = new ArrayList<>();

        // adding items from map to array list
        for (String s : catalogItems.keySet()) {
            listOfItems.addAll(catalogItems.get(s));
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
                        System.out.println("Invalid quantity, Try again.\n(maximum amount you can add is 50 per item)\n");
                        quantity = in.nextInt();
                    }
                    // if entered id is valid then add item to cart item list
                    CartItem cartItem = new CartItem(listOfItems.get(i),quantity);
                    customer.getCart().getListOfClassCartItem().add(cartItem);

                    // add to overall price the total price in cartItem
                    customer.getCart().setOverallPrice(customer.getCart().getOverallPrice() + cartItem.getTotalPrice());

                    // Increase the quantity
                    customer.getCart().setQuantity(customer.getCart().getQuantity()+1);
                }
            }
            if (!check){
                System.out.println("Invalid item id\n");
            }else{
                System.out.println("Item added successfully\n");
            }
        }
    }

    /**
     * This is method checks of the shopping cart is empty
     * if true, then it prints "You cart is empty."
     * if false, then it asks user to enter item ID he/she wants to remove
     * then it searches for item id in listOfClassCartItem
     * if id is there then it remove CartItem of the id from lisOfClassCartItem
     * if  not there is prints "Invalid Item ID"
     *
     */

    public void removeItemFromCart() {
        if (customer.getCart().getQuantity() == 0) {
            System.out.println("\n\nYour cart is empty");
            System.out.println("\nBe sure to fill your cart with something you like");
        } else {
            System.out.println("Enter item id: ");
            Scanner in = new Scanner(System.in);
            int id = in.nextInt();
            boolean check = false;
            for (int i = 0; i < customer.getCart().getListOfClassCartItem().size(); i++) {

                if (id == customer.getCart().getListOfClassCartItem().get(i).getItem().getItemId()) {

                    // firstly decrease from overall price the unit price
                    customer.getCart().overallPrice -= customer.getCart().getListOfClassCartItem().get(i).getTotalPrice();

                    // if entered id is valid then remove cart item from cart item list
                    customer.getCart().getListOfClassCartItem().remove(i);

                    // decrease the quantity of shopping cart
                    customer.getCart().setQuantity(customer.getCart().getQuantity()-1);
                    check = true;
                }
            }
            //if entered ID is invalid
            if (!check) {
                System.out.println("\nInvalid id or the item is not in cart.");
            } else {
                System.out.println("\nitem removed successfully");
            }
        }
    }

    /**
     * This method checks if cart is empty
     * if true, it prints "Your cart is empty:
     * if false, it prints all details of CarItem in listOfClassCartItem
     */
    //print all added items in cart
    public void printCartDetails(){
        System.out.println("\n  -------CART------");
        //if cart is empty
        if ( customer.getCart().getQuantity() == 0 ){
            System.out.println("  Your cart is empty");
            System.out.println("Be sure to fill your cart with something you like\n\n");
        }
        else {
            for (int i = 0; i < customer.getCart().getListOfClassCartItem().size(); i++) {
                customer.getCart().getListOfClassCartItem().get(i).printItem();
            }
        }
    }

    /**
     * This method takes the user to the next step of making an order after adding items to cart
     */
    //checkout and moving to the next process of making an order
    public void checkout(){
       OrderManager manager = new OrderManager();
       manager.createOrder(customer.getCart().getListOfClassCartItem(), customer.getCart().getOverallPrice(), customer);
       manager.displayMenu();
    }

    public ArrayList<CartItem> getListOfClassCartItem(){
        return listOfClassCartItem;
    }

    /**
     * This method empties the listOfClassCartItem attribute from CartItem objects in it.
     */
    public void clearListOfClassCartItem(){
        listOfClassCartItem.clear();
        quantity = 0;
    }
}