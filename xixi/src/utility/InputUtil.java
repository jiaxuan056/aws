/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Scanner;

/**
 *
 * @author YuHang
 */
public final class InputUtil {
    private InputUtil() {}

    public static String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (ValidationUtil.nonEmpty(s)) return s;
            MessageUI.showError("Input required. Try again.");
        }
    }

    public static int readIntInRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v < min || v > max) throw new NumberFormatException();
                return v;
            } catch (Exception e) {
                MessageUI.showError("Enter an integer between " + min + " and " + max + ".");
            }
        }
    }

    public static String readPattern(Scanner sc, String prompt, String regex, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (s.matches(regex)) return s;
            MessageUI.showError(errorMsg);
        }
    }

    public static LocalDate readLocalDateNotPast(Scanner sc, String yPrompt, String mPrompt, String dPrompt) {
        LocalDate today = LocalDate.now();
        while (true) {
            try {
                int year  = readIntInRange(sc, yPrompt, today.getYear(), 9999);
                int month = readIntInRange(sc, mPrompt, 1, 12);
                int day   = readIntInRange(sc, dPrompt, 1, 31);
                LocalDate d = LocalDate.of(year, month, day);
                if (!ValidationUtil.notPast(d)) {
                    MessageUI.showError("Cannot choose a past date. Try again.");
                } else {
                    return d;
                }
            } catch (Exception e) {
                MessageUI.showError("Invalid date. Try again.");
            }
        }
    }

    public static LocalDate readLocalDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return LocalDate.parse(s);
            } catch (Exception e) {
                MessageUI.showError("Invalid format. Use YYYY-MM-DD (e.g., 2025-04-30).");
            }
        }
    }

    public static int readYear(Scanner sc, String prompt, int minYear, int maxYear) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                int y = Integer.parseInt(s);
                if (y >= minYear && y <= maxYear) return y;
            } catch (Exception ignored) {}
            MessageUI.showError("Enter a valid year between " + minYear + " and " + maxYear + ".");
        }
    }

    public static String readHHmmFromAllowed(Scanner sc, String prompt, String[] allowed) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            for (int i = 0; i < allowed.length; i++) {
                if (s.equals(allowed[i])) return s;
            }
            MessageUI.showError("Invalid/Unavailable time. Choose one from the list shown.");
        }
    }

    public static int readDuration30Steps(Scanner sc) {
        while (true) {
            System.out.print("Enter duration in minutes (30/60/90/120): ");
            String s = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (ValidationUtil.durationValid(v)) return v;
            } catch (Exception ignored) {}
            MessageUI.showError("Invalid duration. Try again.");
        }
    }

    public static YearMonth readYearMonth(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return YearMonth.parse(s);
            } catch (Exception e) {
                MessageUI.showError("Invalid format. Use YYYY-MM (e.g., 2025-08).");
            }
        }
    }

    public static LocalDateTime readLocalDateTimeNotPast(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                LocalDateTime t = LocalDateTime.parse(s);
                if (ValidationUtil.dateTimeNotPast(t)) return t;
                MessageUI.showError("Cannot choose a past date-time. Try again.");
            } catch (Exception e) {
                MessageUI.showError("Invalid format. Use YYYY-MM-DDTHH:MM (e.g., 2025-08-12T14:30).");
            }
        }
    }
}
