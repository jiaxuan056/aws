//Wai Kin
package boundary;

import java.util.Scanner;
import control.PatientManagement;
import control.DoctorManagement;
import control.MedicalTreatmentManagement;
import control.ConsultationManagement;
import control.PharmacyManagement;
// import utility.MessageUI;

public class ClinicManagementSystem {
    private static Scanner sc = new Scanner(System.in);
    // removed unused MessageUI instance

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("           CLINIC MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));
        System.out.println("Welcome to the Clinic Management System!");
        System.out.println("This system manages patients, doctors, and medical treatments for the clinic.");
        System.out.println();

        int choice;
        do {
            displayMainMenu();
            choice = getMenuChoice();
            
            switch (choice) {
                case 1:
                    System.out.println("\n" + "=".repeat(40));
                    System.out.println("    PATIENT MANAGEMENT MODULE");
                    System.out.println("=".repeat(40));
                    PatientManagement patientManagement = new PatientManagement();
                    patientManagement.displayMenu();
                    break;
                case 2:
                    System.out.println("\n" + "=".repeat(40));
                    System.out.println("    DOCTOR MANAGEMENT MODULE");
                    System.out.println("=".repeat(40));
                    DoctorManagement doctorManagement = new DoctorManagement();
                    doctorManagement.runDoctorManagement();
                    break;
                case 3:
                    System.out.println("\n" + "=".repeat(40));
                    System.out.println("    CONSULTATION MANAGEMENT MODULE");
                    System.out.println("=".repeat(40));
                    new ConsultationManagement().run();
                    break;
                case 4:
                    MedicalTreatmentManagement medicalTreatment = new MedicalTreatmentManagement();
                    medicalTreatment.displayMenu();
                    break;
                case 5:
                    System.out.println("\n" + "=".repeat(40));
                    System.out.println("    PHARMACY MANAGEMENT MODULE");
                    System.out.println("=".repeat(40));
                    new PharmacyManagement().displayMenu();
                    break;
                case 0:
                    System.out.println("\nThank you for using the Clinic Management System!");
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        
        sc.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Patient Management");
        System.out.println("2. Doctor Management");
        System.out.println("3. Consultation Management");
        System.out.println("4. Medical Treatment Management (Streamlined)");
        System.out.println("5. Pharmacy Management");
        System.out.println("0. Exit");
        System.out.println("=".repeat(50));
    }

    private static int getMenuChoice() {
        System.out.print("Enter your choice: ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
