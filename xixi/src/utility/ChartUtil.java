// utility/ChartUtil.java
package utility;

public class ChartUtil {
    public static void printBar(String label, int value, int maxValue) {
        int width = 50;
        int len = (maxValue <= 0) ? 0 : (int)Math.round((value * 1.0 / maxValue) * width);
        if (len < 0) len = 0;
        if (len > width) len = width;
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < len; i++) bar.append('#');
        System.out.printf("%-24s | %-50s %d%n", label, bar.toString(), value);
    }
}
