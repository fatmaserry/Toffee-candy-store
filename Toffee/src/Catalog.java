import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Catalog {
    private HashMap<String, ArrayList<Item>> items;

    public Catalog() {
        items = new HashMap<>();
    }

    public void displayAllItems() {
        System.out.println("All items:");
        for(String s : items.keySet()){
            for(Item i : items.get(s)){
                i.printItem();
            }
        }
    }

    public void filterByCategory() {
        for(String s : items.keySet()){
            System.out.println(s);
            for(Item i : items.get(s)){
                i.printItem();
            }
        }
    }

    public void addItem() {
        Scanner in = new Scanner(System.in);
        String category = in.nextLine();
        String name = in.nextLine();
        String description = in.nextLine();
        float price = in.nextFloat();
        String brand = in.nextLine();
        String type = in.nextLine();
        float discount = in.nextFloat();
        ItemType itemType;
        if(Objects.equals(type, "Loose")){
            itemType = ItemType.Loose;
        }
        else{
            itemType = ItemType.Sealed;
        }
        Item newItem = new Item(category, name, price, description, brand, discount, itemType);
        if(items.containsKey(category))
            items.get(category).add(newItem);
        else {
            items.put(category, new ArrayList<>());
            items.get(category).add(newItem);
        }
    }

    public void removeItem() {
        // not completed yet
    }
    public HashMap<String, ArrayList<Item>> getItems() {
        return items;
    }
}
