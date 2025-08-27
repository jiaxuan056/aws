/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.time.LocalDate;

/**
 *
 * @author choon
 */
public class TimeUtil {
    public static LocalDate[] nextWeekWindow() {
        LocalDate today = LocalDate.now();
        LocalDate[] arr = new LocalDate[7];
        for (int i = 0; i < 7; i++) arr[i] = today.plusDays(i);
        return arr;
    }
//yuhang
    public static String toDayName(LocalDate date) {
        return date.getDayOfWeek().toString().substring(0,1) + date.getDayOfWeek().toString().substring(1).toLowerCase();
    }
}
