// utility/ValidationUtil.java
package utility;

import entity.Doctor;
import entity.Consultation;
import adt.ListInterface;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ValidationUtil {

    private static final String[] DAYS = {
        "Mon","Tue","Wed","Thu","Fri","Sat","Sun"
    };

    public static String normalizeDay(String input) {
        if (input == null) return null;
        String s = input.trim().toLowerCase();
        if (s.length() >= 3) s = s.substring(0,3);
        switch (s) {
            case "mon": return "Mon";
            case "tue": return "Tue";
            case "wed": return "Wed";
            case "thu": return "Thu";
            case "fri": return "Fri";
            case "sat": return "Sat";
            case "sun": return "Sun";
            default: return null;
        }
    }

    public static boolean isValidDay(String day) {
        if (day == null) return false;
        for (String d : DAYS) if (d.equals(normalizeDay(day))) return true;
        return false;
    }

    public static boolean doctorWorksOnDay(Doctor d, String day) {
        day = normalizeDay(day);
        if (d == null || day == null) return false;
        String duty = d.getDutyDays();
        if (duty == null || duty.isEmpty()) return false;
        String[] parts = duty.split(",");
        for (String p : parts) if (day.equalsIgnoreCase(p.trim())) return true;
        return false;
    }

    // ==== occupancy on 30-min blocks (08:00..21:00 => 26 blocks) ====
    private static int blockIndex(int hour, int minute) {
        return (hour - 8) * 2 + (minute == 30 ? 1 : 0); // 8:00 -> 0, 8:30 ->1, 9:00->2...
    }
    private static int blocksNeeded(int durationMinutes) {
        return durationMinutes / 30;
    }

    public static boolean overlaps(Consultation a, Consultation b) {
        if (!a.getDoctorId().equalsIgnoreCase(b.getDoctorId())) return false;
        if (!a.getDay().equalsIgnoreCase(b.getDay())) return false;
        int aStart = a.getStartHour()*60 + a.getStartMinute();
        int aEnd   = aStart + a.getDurationMinutes();
        int bStart = b.getStartHour()*60 + b.getStartMinute();
        int bEnd   = bStart + b.getDurationMinutes();
        return aStart < bEnd && bStart < aEnd; // strict overlap on minutes
    }

    public static boolean slotTaken(ListInterface<Consultation> list, String doctorId, String day,
                                    int startHour, int startMin, int durationMinutes) {
        Consultation probe = new Consultation(doctorId, "", "", "", day, startHour, startMin, durationMinutes, "");
        for (int i = 1; i <= list.getNumberOfEntries(); i++) {
            Consultation c = list.getEntry(i);
            if ("CANCELLED".equalsIgnoreCase(c.getStatus())) continue;
            if (overlaps(probe, c)) return true;
        }
        return false;
    }

    // Compute if the day has ANY free 30-min slot
    public static boolean dayHasAnyFree30(ListInterface<Consultation> list, String doctorId, String day) {
        for (int h = 8; h <= 20; h++) {
            for (int m = 0; m <= 30; m += 30) {
                if (!slotTaken(list, doctorId, day, h, m, 30)) return true;
            }
        }
        return false;
    }

    // Doctor.bookedSlots mirror helpers (store readable tags)
    public static void addSlotTag(Doctor d, Consultation c) {
        String tag = c.getSlotKey(); // e.g., Monday-10:30+90
        String slots = d.getBookedSlots();
        if (slots == null || slots.isEmpty()) d.setBookedSlots(tag);
        else d.setBookedSlots(slots + "," + tag);
    }

    public static void removeSlotTag(Doctor d, Consultation c) {
        String slots = d.getBookedSlots();
        if (slots == null || slots.isEmpty()) return;
        String tag = c.getSlotKey();
        String[] arr = slots.split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            String s = arr[i].trim();
            if (!s.equals(tag)) {
                if (sb.length() > 0) sb.append(",");
                sb.append(s);
            }
        }
        d.setBookedSlots(sb.toString());
    }

    // ===== Additional helpers used by InputUtil =====
    public static boolean nonEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean notPast(LocalDate d) {
        if (d == null) return false;
        return !d.isBefore(LocalDate.now());
    }

    public static boolean durationValid(int minutes) {
        return minutes == 30 || minutes == 60 || minutes == 90 || minutes == 120;
    }

    public static boolean dateTimeNotPast(LocalDateTime t) {
        if (t == null) return false;
        return !t.isBefore(LocalDateTime.now());
    }
}
