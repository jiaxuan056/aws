// utility/TimeUtil.java
package utility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class TimeUtil {

    // Convert LocalDate to "Monday" .. "Sunday"
    public static String toDayName(LocalDate date) {
        DayOfWeek dow = date.getDayOfWeek();
        return dow.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    // Build 7-day window: tomorrow to "same weekday next week" (7 days)
    public static LocalDate[] nextWeekWindow() {
        LocalDate start = LocalDate.now().plusDays(1); // tomorrow
        LocalDate[] arr = new LocalDate[7];
        for (int i = 0; i < 7; i++) arr[i] = start.plusDays(i);
        return arr;
    }

    // Working day hours: 08:00..21:00 (inclusive end minute)
    public static boolean isValidStart(int hour, int minute, int durationMinutes) {
        if (!(minute == 0 || minute == 30)) return false;
        if (hour < 8 || hour > 21) return false;
        int endTotal = hour * 60 + minute + durationMinutes;
        return endTotal <= 21 * 60; // must finish by 21:00
    }
}
