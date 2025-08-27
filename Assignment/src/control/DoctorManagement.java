// WONG HWAI EARN
package control;
import entity.Consultation;
import dao.ClinicInitializer;
import adt.*;
import boundary.DoctorUI;
import entity.Doctor;
import utility.MessageUI;
import dao.*;
public class DoctorManagement {
    private ListInterface<Doctor> doctorList = new SortedArrayList<>();
    private ListInterface<Consultation> consultationList = new SortedArrayList<>();
    private DoctorUI doctorUI = new DoctorUI();

    public void runDoctorManagement() {
        DoctorInitializer d = new DoctorInitializer(); 
        doctorList = d.initializeDoctors();
        consultationList = ConsultationInitializer.seed(doctorList);
        int choice;
        do {
            choice = doctorUI.getMainMenuChoice();
            switch (choice) {
                case 1: registerDoctor(); break;
                case 2: manageDoctors(); break;
                case 3: manageAvailability(); break;
                case 4: generateReports(); break;
                case 0: MessageUI.displayExitMessage(); break;
                default: MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    // ===== Register =====
    private void registerDoctor() {
        String name = doctorUI.inputDoctorName();
        String specialization = doctorUI.inputDoctorSpecialization();
        String dutyDays = doctorUI.inputDoctorDutyDays();

        Doctor doctor = new Doctor(name, specialization, dutyDays);
        doctorList.add(doctor);

        System.out.println("Doctor registered: " + doctor.getId() + " - " + doctor.getName());
    }

    // ===== Management =====
    private void manageDoctors() {
    int choice;
    do {
        choice = doctorUI.getDoctorManagementChoice();
        switch (choice) {
            case 1: displayAllDoctors(); break;
            case 2: displayFilterDoctor(); break;
            case 3: searchDoctor(); break;
            case 4: updateDoctor(); break;
            case 5: removeDoctor(); break;
            case 0: break;
            default: MessageUI.displayInvalidChoiceMessage();
        }
    } while (choice != 0);
}


    public void displayAllDoctors() {
    if (doctorList.getNumberOfEntries() == 0) {
        System.out.println("No doctors registered.");
        return;
    }

    System.out.println("\n================= Doctor List =================");
    // Table header
    System.out.printf("%-10s %-20s %-15s %-20s\n",
            "ID", "Name", "Specialization", "Duty Days");
    System.out.println("------------------------------------------------------------------------------------------------");

    // Table rows
    for (int i = 1; i <= doctorList.getNumberOfEntries(); i++) {
        Doctor d = doctorList.getEntry(i);
        System.out.printf("%-10s %-20s %-15s %-20s\n",
                d.getId(),
                d.getName(),
                d.getSpecialization(),
                d.getDutyDays());
    }
}

    private void searchDoctor() {
        String id = doctorUI.inputDoctorIdToSearch();
        Doctor doctor = findDoctorById(id);
        if (doctor != null) {
            System.out.println(doctor);
        } else {
            System.out.println("Doctor not found.");
        }
    }

    private void displayFilterDoctor() {
    int choice = doctorUI.getSpecializationChoice();

    String specialization = "";
    switch (choice) {
        case 1: specialization = "Cardiology"; break;
        case 2: specialization = "Neurology"; break;
        case 3: specialization = "Pediatrics"; break;
        case 4: specialization = "Orthopedics"; break;
        case 5: specialization = "Dermatology"; break;
    }

    System.out.println("\n--- Doctors in " + specialization + " ---");
    boolean found = false;

    for (int i = 1; i <= doctorList.getNumberOfEntries(); i++) {
        Doctor d = doctorList.getEntry(i);
        if (d.getSpecialization().equalsIgnoreCase(specialization)) {
            if(!found){
                System.out.printf("%-10s %-20s %-15s %-20s\n",
            "ID", "Name", "Specialization", "Duty Days");
    System.out.println("------------------------------------------------------------------------------------------------");

            }
            System.out.printf("%-10s %-20s %-15s %-20s%n",
                    d.getId(), d.getName(), d.getSpecialization(), d.getDutyDays());
            found = true;
        }
    }

    if (!found) {
        System.out.println("No doctors found in " + specialization + ".");
    }
}

    
    public void updateDoctor() {
    String doctorId = doctorUI.inputDoctorIdToSearch();
    Doctor doctor = findDoctorById(doctorId);

    if (doctor == null) {
        System.out.println("Doctor not found.");
        return;
    }

    
    System.out.println("\nCurrent Doctor Information:");
    System.out.println("ID: " + doctor.getId());
    System.out.println("Name: " + doctor.getName());
    System.out.println("Specialization: " + doctor.getSpecialization());
    System.out.println("Duty Days: " + doctor.getDutyDays());

    int choice;
    do {
        choice = doctorUI.getDoctorUpdateChoice();
        switch (choice) {
            case 1:
                doctor.setName(doctorUI.inputDoctorName());
                System.out.println("Name updated.");
                break;
            case 2:
                doctor.setSpecialization(doctorUI.inputDoctorSpecialization());
                System.out.println("Specialization updated.");
                break;
            case 3:
                doctor.setDutyDays(doctorUI.inputDoctorDutyDays());
                System.out.println("Duty schedule updated.");
                break;
            case 0:
                System.out.println("Information Updated.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    } while (choice != 0);
}

    private void removeDoctor() {
        String id = doctorUI.inputDoctorIdToRemove();
        boolean removed = removeDoctorById(id);
        if (removed) {
            System.out.println("Doctor removed.");
        } else {
            System.out.println("Doctor not found.");
        }
    }

    // ===== Availability =======
    private void manageAvailability() {
        int choice;
        do {
            choice = doctorUI.getAvailabilityChoice();
            switch (choice) {
                case 1: checkAvailability(); break;
                case 0: break;
                default: MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    private boolean isAvailable(Doctor doctor, String day, int startHour, int startMinute, int durationMinutes) {
    // 1. Check fixed working hours
    int reqStart = startHour * 60 + startMinute;
    int reqEnd = reqStart + durationMinutes;
    int workStart = 8 * 60;       // 08:00
    int workEnd = 19 * 60 + 30;   // 19:30

    if (reqStart < workStart || reqEnd > workEnd) {
        return false; // outside working hours
    }

    // 2. Check clashes with existing consultations
    for (int i = 1; i <= consultationList.getNumberOfEntries(); i++) {
        Consultation c = consultationList.getEntry(i);
        if (c.getDoctorId().equals(doctor.getId())
                && c.getDay().equalsIgnoreCase(day)
                && c.getStatus().equals("BOOKED")) {

            int cStart = c.getStartHour() * 60 + c.getStartMinute();
            int cEnd = cStart + c.getDurationMinutes();

            // overlap condition
            if (reqStart < cEnd && cStart < reqEnd) {
                return false;
            }
        }
    }

    return true; 
}




    private void checkAvailability() {
    String id = doctorUI.inputDoctorIdForAppointment();
    Doctor doctor = findDoctorById(id);

    if (doctor != null) {
        String day = doctorUI.inputAppointmentDay();
        int startHour = doctorUI.inputAppointmentStartHour();
        int startMinute = doctorUI.inputAppointmentStartMinute();
        int duration = doctorUI.inputAppointmentDuration();

        boolean available = isAvailable(doctor, day, startHour, startMinute, duration);

        System.out.printf("Doctor %s from %02d:%02d for %d minutes on %s is %s.%n",
                doctor.getName(), startHour, startMinute, duration, day,
                available ? "AVAILABLE" : "NOT available");
    } else {
        System.out.println("Doctor not found.");
    }
}




    // ===== Reports =====
    private void generateReports() {
        int choice;
        do {
            choice = doctorUI.getReportsMenuChoice();
            switch (choice) {
                case 1: reportDoctorCountByDay(); break;
                case 2: reportDoctorCountBySpecialization(); break;
                case 0: break;
                default: MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    // ===== Helper methods =====
    private Doctor findDoctorById(String id) {
        for (int i = 1; i <= doctorList.getNumberOfEntries(); i++) {
            Doctor doctor = doctorList.getEntry(i);
            if (doctor.getId().equalsIgnoreCase(id)) {
                return doctor;
            }
        }
        return null;
    }

    private boolean removeDoctorById(String id) {
        for (int i = 1; i <= doctorList.getNumberOfEntries(); i++) {
            Doctor doctor = doctorList.getEntry(i);
            if (doctor.getId().equalsIgnoreCase(id)) {
                doctorList.remove(i);
                return true;
            }
        }
        return false;
    }

    private void reportDoctorCountByDay() {
    System.out.println("\n--- Doctor Count by Day of Week ---");

    int mon = 0, tue = 0, wed = 0, thu = 0, fri = 0, sat = 0, sun = 0;

    for (int i = 1; i <= doctorList.getNumberOfEntries(); i++) {
        Doctor d = doctorList.getEntry(i);
        String dutyDays = d.getDutyDays();

        if (dutyDays.contains("Mon")) mon++;
        if (dutyDays.contains("Tue")) tue++;
        if (dutyDays.contains("Wed")) wed++;
        if (dutyDays.contains("Thu")) thu++;
        if (dutyDays.contains("Fri")) fri++;
        if (dutyDays.contains("Sat")) sat++;
        if (dutyDays.contains("Sun")) sun++;
    }
    System.out.println("\n==============================================");
    System.out.println("       Doctor Availability by Day Report       ");
    System.out.println("==============================================");
    System.out.printf("%-10s | %-10s%n", "Day", "Doctor Count");
    System.out.println("----------------------------------------------");
    System.out.println("Monday     : " + mon + " doctor(s)");
    System.out.println("Tuesday    : " + tue + " doctor(s)");
    System.out.println("Wednesday  : " + wed + " doctor(s)");
    System.out.println("Thursday   : " + thu + " doctor(s)");
    System.out.println("Friday     : " + fri + " doctor(s)");
    System.out.println("Saturday   : " + sat + " doctor(s)");
    System.out.println("Sunday     : " + sun + " doctor(s)");
    System.out.println("\n==============================================");
}


    private void reportDoctorCountBySpecialization() {
    System.out.println("\n==============================================");
    System.out.println("     Doctor Count by Specialization Report     ");
    System.out.println("==============================================");
    System.out.printf("%-20s | %-10s%n", "Specialization", "Doctor Count");
    System.out.println("----------------------------------------------");

    // Cardiology
    int cardiologyCount = 0;
    for (int i = 1; i <= doctorList.getNumberOfEntries(); i++) {
        if (doctorList.getEntry(i).getSpecialization().equalsIgnoreCase("Cardiology")) {
            cardiologyCount++;
        }
    }
    System.out.printf("%-20s | %-10d%n", "Cardiology", cardiologyCount);

    // Dermatology
    int dermatologyCount = 0;
    for (int i = 1; i <= doctorList.getNumberOfEntries(); i++) {
        if (doctorList.getEntry(i).getSpecialization().equalsIgnoreCase("Dermatology")) {
            dermatologyCount++;
        }
    }
    System.out.printf("%-20s | %-10d%n", "Dermatology", dermatologyCount);

    // Pediatrics
    int pediatricsCount = 0;
    for (int i = 1; i <= doctorList.getNumberOfEntries(); i++) {
        if (doctorList.getEntry(i).getSpecialization().equalsIgnoreCase("Pediatrics")) {
            pediatricsCount++;
        }
    }
    System.out.printf("%-20s | %-10d%n", "Pediatrics", pediatricsCount);

    // Orthopedics
    int orthoCount = 0;
    for (int i = 1; i <= doctorList.getNumberOfEntries(); i++) {
        if (doctorList.getEntry(i).getSpecialization().equalsIgnoreCase("Orthopedics")) {
            orthoCount++;
        }
    }
    System.out.printf("%-20s | %-10d%n", "Orthopedics", orthoCount);

    // Neurology
    int neuroCount = 0;
    for (int i = 1; i <= doctorList.getNumberOfEntries(); i++) {
        if (doctorList.getEntry(i).getSpecialization().equalsIgnoreCase("Neurology")) {
            neuroCount++;
        }
    }
    System.out.printf("%-20s | %-10d%n", "Neurology", neuroCount);

    System.out.println("==============================================");
}


    public static void main(String[] args) { 
    DoctorManagement doctorManagement = new DoctorManagement(); 
    doctorManagement.runDoctorManagement(); }
}
