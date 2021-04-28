package Draft;

public class Main {

    private static void set() {
        Util.setaBoolean(true);
    }

    public static void main(String[] args) {
        Util util1 = new Util();
        Util util2 = new Util();
        System.out.println(util1.getaBoolean());
        util2.setaBoolean(true);
        System.out.println(util1.getaBoolean());
    }
}
