package Products;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Catalog {
    private HashMap<String, ArrayList<Item>> items;

    public Catalog() {
        items = new HashMap<>();
        try{
            File myItems = new File("C:\\Users\\user\\.vscode\\SoftwareEngineering1-ToffeeStore\\src\\main\\java\\items.txt");
            Scanner reader = new Scanner(myItems);
            int cnt = 1;
            String category = "", name = "",description = "", brand = "", type = "";
            float price = 0.0F, discount = 0.0F;
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                switch (cnt % 8){
                    case 0:
                        addItem(category, name, description, price, brand, type, discount);
                    case 1:
                        category = data;
                        break;
                    case 2:
                        name = data;
                        break;
                    case 3:
                        description = data;
                        break;
                    case 4:
                        brand = data;
                        break;
                    case 5:
                        type = data;
                        break;
                    case 6:
                        discount = Float.parseFloat(data);
                        break;
                    case 7:
                        price = Float.parseFloat(data);
                        break;
                }
                cnt++;
            }
        }
        catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
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

    public void addItem(String category, String name, String description, float price, String brand, String type, float discount) {
        ItemType itemType;
        if(Objects.equals(type, "Loose")){
            itemType = ItemType.Loose;
        }
        else if(Objects.equals(type, "Sealed")){
            itemType = ItemType.Sealed;
        }
        else{
            itemType = ItemType.Voucher;
        }
        Item newItem;
        if(ItemType.Voucher == itemType)
            newItem = new Voucher(category, name, price, description, brand, discount, itemType, price);
        else
            newItem = new Item(category, name, price, description, brand, discount, itemType);

        if(items.containsKey(category))
            items.get(category).add(newItem);
        else {
            items.put(category, new ArrayList<>());
            items.get(category).add(newItem);
        }
    }

    public HashMap<String, ArrayList<Item>> getItems() {
        return items;
    }
}
