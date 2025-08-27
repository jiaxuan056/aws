// Yuhang
package control;

import adt.SortedArrayList;
import adt.ListInterface;
import boundary.ConsultationUI;
import dao.ClinicInitializer;
import entity.Consultation;
import entity.Doctor;
import entity.Patient;
import utility.ChartUtil;
import utility.MessageUI;
import utility.TimeUtil;
import utility.ValidationUtil;

import java.time.LocalDate;

public class ConsultationManagement {

    private final ConsultationUI ui = new ConsultationUI();

    private ListInterface<Doctor> doctorList = new SortedArrayList<>();
    private ListInterface<Consultation> consultationList = new SortedArrayList<>();
    private ListInterface<Patient> patientList = new SortedArrayList<>();

    public ConsultationManagement() {
        // Load doctors and consultations from unified initializer
        doctorList = ClinicInitializer.initializeDoctors();
        patientList = ClinicInitializer.initializePatients();
        consultationList = ClinicInitializer.initializeConsultations();
        // Mirror seeds into doctor.bookedSlots
        for (int i = 0; i < consultationList.size(); i++) {
            Consultation c = consultationList.get(i);
            Doctor d = findDoctorById(c.getDoctorId());
            if (d != null) ValidationUtil.addSlotTag(d, c);
        }
    }

    public void run() {
        while (true) {
            int choice = ui.mainMenu();
            switch (choice) {
                case 1: book(); break;
                case 2: reschedule(); break;
                case 3: cancel(); break;
                case 4: complete(); break;
                case 5: search(); break;
                case 6: reportConsultationsPerDoctor(); break;
                case 7: reportUtilizationByHalfHour(); break;
                case 8: viewAll(); break;
                case 0: MessageUI.displayExitMessage(); return;
                default: MessageUI.displayInvalidChoiceMessage();
            }
        }
    }

    
    private void book() {
        // choose doctor 
        if (doctorList.isEmpty()) {
            System.out.println("! No doctors available.");
            return;
        }
        String[] doctorLines = new String[doctorList.size()];
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor d = doctorList.get(i);
            doctorLines[i] = String.format("%s (%s)", d.getName(), d.getId());
        }
        int docIdx = ui.chooseFromList("Select Doctor", doctorLines);
        Doctor doc = doctorList.get(docIdx - 1);

        //  duty days
        LocalDate[] win = TimeUtil.nextWeekWindow();
        int eligible = 0;
        for (int i = 0; i < win.length; i++) {
            String dayName = TimeUtil.toDayName(win[i]);
            if (ValidationUtil.doctorWorksOnDay(doc, dayName)) eligible++;
        }
        if (eligible == 0) {
            System.out.println("! This doctor has no duty days in the next week window.");
            return;
        }

        //day labels + whether day is FULL
        String[] actualDayNames = new String[eligible];
        String[] dayLines = new String[eligible];
        boolean[] dayHasFree30 = new boolean[eligible];
        int k = 0;
        for (int i = 0; i < win.length; i++) {
            String dayName = TimeUtil.toDayName(win[i]);
            if (!ValidationUtil.doctorWorksOnDay(doc, dayName)) continue;
            boolean hasFree = ValidationUtil.dayHasAnyFree30(consultationList, doc.getId(), dayName);
            actualDayNames[k] = dayName;
            dayHasFree30[k] = hasFree;
            dayLines[k] = hasFree ? dayName : (dayName + " (FULL)");
            k++;
        }

        // If FULL,can't proceed
        boolean anyFreeDay = false;
        for (int i = 0; i < eligible; i++) if (dayHasFree30[i]) { anyFreeDay = true; break; }
        if (!anyFreeDay) {
            System.out.println("! All eligible duty days are FULL in the next week window.");
            return;
        }

        // choose a day 
        String chosenDay;
        while (true) {
            int dayChoice = ui.chooseFromList("Select Day (within next week)", dayLines);
            if (!dayHasFree30[dayChoice-1]) {
                System.out.println("Selected day is FULL. Please choose another day.");
                continue; // re-prompt if full
            }
            chosenDay = actualDayNames[dayChoice-1];
            break;
        }

        // choose duration and then a free slot
        int duration;
        String[] slots;
        while (true) {
            int durChoice = ui.chooseDuration();
            duration = (durChoice == 1) ? 30 : 90;
            slots = buildAvailableSlotsFor(doc.getId(), chosenDay, duration);
            if (slots.length == 0) {
                System.out.println("No available time slots on " + chosenDay + " for " + duration + " minutes.");
                System.out.println("Choose a different duration.");
                continue; // re-prompt duration
            }
            break;
        }

        int slotChoice = ui.chooseFromList("Select Time Slot", slots);
        String time = slots[slotChoice-1];
        int sh = Integer.parseInt(time.substring(0,2));
        int sm = Integer.parseInt(time.substring(3,5));

        //patient info with ID verify
        String pid;
        Patient patient;
        while (true) {
            pid = ui.inputLine("Patient ID (e.g., P1001) or 0 to cancel: ", false);
            if ("0".equals(pid)) { System.out.println("(Cancelled by user)"); return; }
            patient = findPatientById(pid);
            if (patient == null) {
                System.out.println("! Patient ID not found. Try again.");
                continue;
            }
            break;
        }
        String pname = patient.getName();
        String notes = ui.inputLine("Notes (optional): ", true);

        // Summary & confirm
        System.out.println("\n--- Booking Summary ---");
        System.out.println("Doctor   : " + doc.getName() + " (" + doc.getId() + ")");
        System.out.println("Day      : " + chosenDay);
        System.out.println("Time     : " + String.format("%02d:%02d", sh, sm));
        System.out.println("Duration : " + duration + " minutes");
        System.out.println("Patient  : " + pname + " (" + pid + ")");
        System.out.println("Notes    : " + (notes == null ? "" : notes));
        String confirm = ui.inputLine("Confirm? (Y/N) > ", false);
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("(Cancelled by user)");
            return;
        }

        // Finaval validation (just in case)
        if (ValidationUtil.slotTaken(consultationList, doc.getId(), chosenDay, sh, sm, duration)) {
            System.out.println("! Sorry, that slot just got taken. Try again.");
        
            
            slots = buildAvailableSlotsFor(doc.getId(), chosenDay, duration);
            if (slots.length == 0) {
                System.out.println("No more available time slots on " + chosenDay + " for " + duration + " minutes.");
                return; // nothing else we can offer now
            }
            slotChoice = ui.chooseFromList("Select Time Slot", slots);
            time = slots[slotChoice-1];
            sh = Integer.parseInt(time.substring(0,2));
            sm = Integer.parseInt(time.substring(3,5));
        }

        Consultation c = new Consultation(doc.getId(), doc.getName(), pid, pname,
                chosenDay, sh, sm, duration, notes);
        consultationList.add(c);
        ValidationUtil.addSlotTag(doc, c);
        System.out.println("✓ Booked: " + c);
    }

    private String[] buildAvailableSlotsFor(String doctorId, String day, int duration) {
        int latestStartMinute = 21*60 - duration; // must finish by 21:00
        int count = 0;
        for (int h = 8; h <= 21; h++) {
            for (int m = 0; m <= 30; m += 30) {
                int startTotal = h*60 + m;
                if (startTotal > latestStartMinute) break;
                if (!ValidationUtil.slotTaken(consultationList, doctorId, day, h, m, duration)) count++;
            }
        }
        String[] out = new String[count];
        int idx = 0;
        for (int h = 8; h <= 21; h++) {
            for (int m = 0; m <= 30; m += 30) {
                int startTotal = h*60 + m;
                if (startTotal > latestStartMinute) break;
                if (!ValidationUtil.slotTaken(consultationList, doctorId, day, h, m, duration)) {
                    out[idx++] = String.format("%02d:%02d", h, m);
                }
            }
        }
        return out;
    }

    
    private void reschedule() {
        Consultation c = null;
        while (c == null) {
            String id = ui.inputLine("Consultation ID to reschedule (0 to back): ", false);
            if ("0".equals(id)) return;
            c = findConsultationById(id);
            if (c == null) System.out.println("! Consultation not found. Try again.");
            else if ("CANCELLED".equalsIgnoreCase(c.getStatus())) {
                System.out.println("! Cannot reschedule a cancelled consultation.");
                c = null;
            }
        }
        Doctor doc = findDoctorById(c.getDoctorId());
        if (doc == null) { System.out.println("! Doctor not found for this consultation."); return; }

        
        LocalDate[] win = TimeUtil.nextWeekWindow();
        int eligible = 0;
        for (int i = 0; i < win.length; i++) {
            String dayName = TimeUtil.toDayName(win[i]);
            if (ValidationUtil.doctorWorksOnDay(doc, dayName)) eligible++;
        }
        if (eligible == 0) { System.out.println("! This doctor has no duty days in the next week."); return; }

        String[] actualDayNames = new String[eligible];
        String[] dayLines = new String[eligible];
        boolean[] dayHasFree30 = new boolean[eligible];
        int k = 0;
        for (int i = 0; i < win.length; i++) {
            String dayName = TimeUtil.toDayName(win[i]);
            if (!ValidationUtil.doctorWorksOnDay(doc, dayName)) continue;
            boolean hasFree = ValidationUtil.dayHasAnyFree30(consultationList, doc.getId(), dayName);
            actualDayNames[k] = dayName;
            dayHasFree30[k] = hasFree;
            dayLines[k] = hasFree ? dayName : (dayName + " (FULL)");
            k++;
        }
        boolean anyFreeDay = false;
        for (int i = 0; i < eligible; i++) if (dayHasFree30[i]) { anyFreeDay = true; break; }
        if (!anyFreeDay) { System.out.println("! All eligible duty days are FULL in the next week."); return; }

        String chosenDay;
        while (true) {
            int dayChoice = ui.chooseFromList("Select new Day", dayLines);
            if (!dayHasFree30[dayChoice-1]) {
                System.out.println("Selected day is FULL. Please choose another day.");
                continue;
            }
            chosenDay = actualDayNames[dayChoice-1];
            break;
        }

        int duration;
        String[] slots;
        while (true) {
            int durChoice = ui.chooseDuration();
            duration = (durChoice == 1) ? 30 : 90;
            slots = buildAvailableSlotsFor(doc.getId(), chosenDay, duration);
            if (slots.length == 0) {
                System.out.println("No available time slots on " + chosenDay + " for " + duration + " minutes.");
                System.out.println("Choose a different duration.");
                continue; // re-prompt duration
            }
            break;
        }
        int slotChoice = ui.chooseFromList("Select new Time Slot", slots);
        int sh = Integer.parseInt(slots[slotChoice-1].substring(0,2));
        int sm = Integer.parseInt(slots[slotChoice-1].substring(3,5));

        ValidationUtil.removeSlotTag(doc, c);
        c.setDay(chosenDay);
        c.setStartHour(sh);
        c.setStartMinute(sm);
        c.setDurationMinutes(duration);
        ValidationUtil.addSlotTag(doc, c);

        System.out.println("✓ Rescheduled: " + c);
    }

    private void cancel() {
        Consultation c = null;
        while (c == null) {
            String id = ui.inputLine("Consultation ID to cancel (0 to back): ", false);
            if ("0".equals(id)) return;
            c = findConsultationById(id);
            if (c == null) System.out.println("! Consultation not found. Try again.");
        }
        if ("CANCELLED".equalsIgnoreCase(c.getStatus())) {
            System.out.println("! Already cancelled.");
            return;
        }
        c.setStatus("CANCELLED");
        Doctor d = findDoctorById(c.getDoctorId());
        if (d != null) ValidationUtil.removeSlotTag(d, c);
        System.out.println("✓ Cancelled: " + c.getConsultationID());
    }

    private void complete() {
        Consultation c = null;
        while (c == null) {
            String id = ui.inputLine("Consultation ID to complete (0 to back): ", false);
            if ("0".equals(id)) return;
            c = findConsultationById(id);
            if (c == null) System.out.println("! Consultation not found. Try again.");
        }
        if ("CANCELLED".equalsIgnoreCase(c.getStatus())) {
            System.out.println("! Cannot complete a cancelled consultation.");
            return;
        }
        c.setStatus("COMPLETED");
        System.out.println("✓ Marked as COMPLETED: " + c.getConsultationID());
    }

    private void search() {
        while (true) {
            System.out.println("\n-- Search --");
            System.out.println("1) By Consultation ID");
            System.out.println("2) By Doctor ID");
            System.out.println("3) By Doctor Name");
            System.out.println("4) By Day");
            System.out.println("5) By Status");
            System.out.println("0) Back");
            System.out.print("Enter choice > ");
            int ch = ui.nextIntLoop();
            if (ch == 0) return;

            String label = null;
            String key = null;
            switch (ch) {
                case 1: label = "Consultation ID"; break;
                case 2: label = "Doctor ID"; break;
                case 3: label = "Doctor Name"; break;
                case 4: label = "Day (Mon/Tue/.../full)"; break;
                case 5: label = "Status (BOOKED/COMPLETED/CANCELLED)"; break;
                default: System.out.println("Invalid."); continue;
            }
            key = ui.inputLine(label + ": ", false);
            if (ch == 4) key = utility.ValidationUtil.normalizeDay(key);

            int count = 0;
            for (int i = 0; i < consultationList.size(); i++) {
                Consultation c = consultationList.get(i);
                boolean show = false;
                if (ch == 1 && c.getConsultationID().equalsIgnoreCase(key)) show = true;
                else if (ch == 2 && c.getDoctorId().equalsIgnoreCase(key)) show = true;
                else if (ch == 3 && c.getDoctorName().toLowerCase().contains(key.toLowerCase())) show = true;
                else if (ch == 4 && c.getDay().equalsIgnoreCase(key)) show = true;
                else if (ch == 5 && c.getStatus().equalsIgnoreCase(key)) show = true;

                if (show) { System.out.println(c); count++; }
            }
            if (count == 0) System.out.println("(no results)");
        }
    }

    private void reportConsultationsPerDoctor() {
        System.out.println("\n== Report: Consultations per Doctor (BOOKED+COMPLETED) ==");
        int max = 0;
        int n = doctorList.size();
        int[] counts = new int[n];
        for (int j = 0; j < n; j++) {
            Doctor d = doctorList.get(j);
            int cnt = 0;
            for (int i = 0; i < consultationList.size(); i++) {
                Consultation c = consultationList.get(i);
                if (!"CANCELLED".equalsIgnoreCase(c.getStatus())
                        && d.getId().equalsIgnoreCase(c.getDoctorId())) cnt++;
            }
            counts[j] = cnt;
            if (cnt > max) max = cnt;
        }
        for (int j = 0; j < n; j++) {
            Doctor d = doctorList.get(j);
            ChartUtil.printBar(d.getName(), counts[j], max);
        }
    }

    private void reportUtilizationByHalfHour() {
        System.out.println("\n== Report: Utilization by Half-hour ==");
        int buckets = (21 - 8) * 2 + 1; // indices 0..26 (08:00..21:00)
        int[] count = new int[buckets];
        int max = 0;

        for (int i = 0; i < consultationList.size(); i++) {
            Consultation c = consultationList.get(i);
            if ("CANCELLED".equalsIgnoreCase(c.getStatus())) continue;
            int startIndex = ((c.getStartHour() - 8) * 2) + (c.getStartMinute() == 30 ? 1 : 0);
            int blocks = c.getDurationMinutes() / 30;
            for (int b = 0; b < blocks; b++) {
                int idx = startIndex + b;
                if (idx >= 0 && idx < buckets) {
                    count[idx]++;
                    if (count[idx] > max) max = count[idx];
                }
            }
        }
        for (int idx = 0; idx < buckets; idx++) {
            int totalMinsFrom0800 = idx * 30;
            int h = 8 + totalMinsFrom0800 / 60;
            int m = totalMinsFrom0800 % 60;
            ChartUtil.printBar(String.format("%02d:%02d", h, m), count[idx], max);
        }
    }

    private void viewAll() {
        System.out.println("\n== All Consultations ==");
        if (consultationList.isEmpty()) {
            System.out.println("(none)");
            return;
        }
        System.out.printf("%-8s | %-8s | %-20s | %-8s | %-6s | %-9s | %-10s\n",
                "ID", "Doctor", "Doctor Name", "Patient", "Day", "Time", "Status");
        System.out.println("-".repeat(80));
        for (int i = 0; i < consultationList.size(); i++) {
            Consultation c = consultationList.get(i);
            String time = String.format("%02d:%02d (%d)", c.getStartHour(), c.getStartMinute(), c.getDurationMinutes());
            System.out.printf("%-8s | %-8s | %-20s | %-8s | %-6s | %-9s | %-10s\n",
                    c.getConsultationID(), c.getDoctorId(), c.getDoctorName(), c.getPatientId(), c.getDay(), time, c.getStatus());
        }
    }

    
    private Doctor findDoctorById(String id) {
        if (id == null) return null;
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor d = doctorList.get(i);
            if (id.equalsIgnoreCase(d.getId())) return d;
        }
        return null;
    }

    private Consultation findConsultationById(String id) {
        if (id == null) return null;
        for (int i = 0; i < consultationList.size(); i++) {
            Consultation c = consultationList.get(i);
            if (id.equalsIgnoreCase(c.getConsultationID())) return c;
        }
        return null;
    }

    private Patient findPatientById(String id) {
        if (id == null) return null;
        for (int i = 0; i < patientList.size(); i++) {
            Patient p = patientList.get(i);
            if (id.equalsIgnoreCase(p.getPatientId())) return p;
        }
        return null;
    }

    public static void main(String[] args) {
        new ConsultationManagement().run();
    }
}
