package Assignment3;

public class MoneyUtil {

    public static String costToString(double cost) {
        return cost * 100 % 10 != 0 ? "$" + cost : "$" + cost + "0";
    }
}
