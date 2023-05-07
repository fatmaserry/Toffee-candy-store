package Products;

public class Item {
    private static int id = 0;
    private int itemId;
    private String category;
    private String name;
    private float unitPrice;
    private String description;
    private String brand;
    private float discount;
    private ItemType type;

    public Item(String category, String name, float unitPrice, String description, String brand, float discount, ItemType type) {
        id++;
        itemId = id;
        this.category = category;
        this.name = name;
        this.unitPrice = unitPrice;
        this.description = description;
        this.brand = brand;
        this.discount = discount;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUnitPrice() {
        return unitPrice - (unitPrice * discount);
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Item.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void printItem(){
        System.out.println("---------------------------------------------------------------------------");
        System.out.println(String.format("| name: %-66s", name) + "|");
        int cnt = 0;
        for(String i: description.split("\n")){
            if(cnt == 0)
                System.out.println(String.format("| description: %-59s", i) + "|");
            else
                System.out.println(String.format("| %-72s", i) + "|");
            cnt++;
        }
        System.out.println(String.format("| price: %-65s", getUnitPrice()) + "|");
        System.out.println(String.format("| item id: %-63s", itemId) + "|");
        System.out.println("---------------------------------------------------------------------------");
    }
}
