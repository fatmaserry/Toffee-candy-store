public class Main {
    public static void main(String[] args) {
        String name = "mina";
        String description = "A wearable device that connects to your\n"+
                "smartphone and allows you to track your fitness\n," +
                "receive notifications, make phone calls, send messages\n";
        float price = 2.43F;
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
        System.out.println(String.format("| price: %-65s", price) + "|");
        System.out.println("---------------------------------------------------------------------------");
    }
}