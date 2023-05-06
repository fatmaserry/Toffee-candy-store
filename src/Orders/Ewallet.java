package Orders;

public class Ewallet extends Payment {

    private int CVV;
    private int creditNum;

    Ewallet(int cvv,int creditNum, float price){
        super();
        setPrice(price);
        setPayMethod("Ewallet");
        this.CVV = cvv;
        this.creditNum = creditNum;
    }
    public int getCVV() {
        return CVV;
    }

    public void setCVV(int CVV) {
        this.CVV = CVV;
    }

    public int getCreditNum() {
        return creditNum;
    }

    public void setCreditNum(int creditNum) {
        this.creditNum = creditNum;
    }
}
