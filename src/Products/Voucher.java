package Products;

public class Voucher extends Item {
    private String code;

    public Voucher(String category, String name, float unitPrice, String description, String brand, float discount, ItemType type, String Code) {
        super(category, name, unitPrice, description, brand, discount, type);
        this.code = Code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
