//Wai Kin
package boundary;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MedicalTreatmentUI {
    private Scanner scanner = new Scanner(System.in);

    public int getMainMenuChoice() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("    MEDICAL TREATMENT MANAGEMENT SYSTEM");
        System.out.println("=".repeat(50));
        
        System.out.println("\n--- CORE OPERATIONS ---");
        System.out.println("1. Add Treatment (from Consultation)");
        
        System.out.println("\n--- TREATMENT MANAGEMENT ---");
        System.out.println("2. Update Treatment");
        System.out.println("3. Delete Treatment");
        System.out.println("4. Search/View Treatment");
        System.out.println("5. View Treatment History");
        System.out.println("6. Schedule Follow-up Appointment");
        
        System.out.println("\n--- REPORTS & ANALYTICS ---");
        System.out.println("7. Treatment History Report (Per Patient)");
        System.out.println("8. Generate Summary Analysis Report");
        
        System.out.println("\n--- VIEW ---");
        System.out.println("0. Exit");
        
        System.out.println("\n" + "-".repeat(50));
        System.out.print("Enter your choice: ");
        return getIntInput();
    }

    // ===== CORE OPERATION INPUTS =====
    public String inputConsultationId() {
        System.out.print("Enter Consultation ID (links patient & doctor): ");
        return scanner.nextLine().trim();
    }

    public String inputPatientId() {
        System.out.print("Enter Patient ID: ");
        return scanner.nextLine().trim();
    }

    public String inputDoctorId() {
        System.out.print("Enter Doctor ID: ");
        return scanner.nextLine().trim();
    }

    public String inputDiagnosis() {
        System.out.print("Enter Diagnosis (doctor's conclusion): ");
        return scanner.nextLine().trim();
    }

    public String inputSymptoms() {
        System.out.print("Enter Symptoms (patient-reported): ");
        return scanner.nextLine().trim();
    }

    public String inputCondition() {
        System.out.print("Enter Medical Condition (observed/known conditions): ");
        return scanner.nextLine().trim();
    }

    public String inputTreatmentPlan() {
        System.out.print("Enter Treatment Plan (procedures/steps): ");
        return scanner.nextLine().trim();
    }

    public String inputPrescribedMedication() {
        System.out.print("Enter Prescribed Medication (e.g., name/dosage): ");
        return scanner.nextLine().trim();
    }

    public LocalDate inputTreatmentDate() {
        while (true) {
            System.out.print("Enter Treatment Date (dd/MM/yyyy) [Enter for today]: ");
            String dateStr = scanner.nextLine().trim();
            if (dateStr.isEmpty()) {
                return LocalDate.now();
            }
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy");
            }
        }
    }

    public LocalDate inputEndDate() {
        while (true) {
            System.out.print("Enter Treatment End Date (dd/MM/yyyy) [Enter for today]: ");
            String dateStr = scanner.nextLine().trim();
            if (dateStr.isEmpty()) {
                return LocalDate.now();
            }
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy");
            }
        }
    }

    public double inputTreatmentCost() {
        while (true) {
            System.out.print("Enter Treatment Cost (RM): ");
            String costStr = scanner.nextLine().trim();
            try {
                return Double.parseDouble(costStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid cost format. Please enter a valid number.");
            }
        }
    }

    // ===== TREATMENT MANAGEMENT INPUTS =====
    public String inputTreatmentId() {
        System.out.print("Enter Treatment ID: ");
        return scanner.nextLine().trim();
    }

    public String inputConfirmation(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public LocalDate inputFollowUpDate() {
        while (true) {
            System.out.print("Enter Follow-up Date (dd/MM/yyyy): ");
            String dateStr = scanner.nextLine().trim();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy");
            }
        }
    }

    // ===== ADVANCED SEARCH INPUTS =====
    public int inputSearchChoice() {
        while (true) {
            System.out.print("Enter your search choice (1-4): ");
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4 (1=ID, 2=Patient, 3=Specialization, 4=Date).");
            }
        }
    }

    public String inputSpecialization() {
        System.out.print("Enter Doctor Specialization: ");
        return scanner.nextLine().trim();
    }

    // ===== UTILITY METHODS =====
    public void displaySuccess(String message) {
        System.out.println("\nSUCCESS: " + message);
    }

    public void displayError(String message) {
        System.out.println("\n✗ ERROR: " + message);
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
