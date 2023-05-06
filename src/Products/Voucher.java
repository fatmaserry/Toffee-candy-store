package Products;

public class Voucher extends Item {
    private static int code = 2023;
    private int voucherCode;
    private float value;
    public Voucher(String category, String name, float unitPrice, String description, String brand, float discount, ItemType type, float value) {
        super(category, name, unitPrice, description, brand, discount, type);
        code++;
        voucherCode = code;
        this.value = value;
    }

    public int getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(int voucherCode) {
        this.voucherCode = voucherCode;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void printItem(){
        System.out.println("---------------------------------------------------------------------------");
        System.out.println(String.format("| name: %-66s", super.getName()) + "|");
        int cnt = 0;
        for(String i: super.getDescription().split("\n")){
            if(cnt == 0)
                System.out.println(String.format("| description: %-59s", i) + "|");
            else
                System.out.println(String.format("| %-72s", i) + "|");
            cnt++;
        }
        System.out.println(String.format("| price: %-65s", getUnitPrice()) + "|");
        System.out.println(String.format("| item id: %-63s", super.getItemId()) + "|");
        System.out.println(String.format("| value: %-65s", value) + "|");
        System.out.println("---------------------------------------------------------------------------");
    }
}
