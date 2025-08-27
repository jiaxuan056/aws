// Ric Hang
package boundary;

import java.util.Scanner;

public class PharmacyManagementUI {
    private final Scanner sc = new Scanner(System.in);

    public int getMainMenuChoice() {
        System.out.println("\n=== Pharmacy Management ===");
        System.out.println("1. View Inventory");
        System.out.println("2. Sort Inventory (Name/Stock/Price)");
        System.out.println("3. Add New Medicine");
        System.out.println("4. Restock Medicine");
        System.out.println("5. Dispense Medicine (from Treatment)");
        System.out.println("6. View Dispense Records");
        System.out.println("7. Report: Low Stock");
        System.out.println("8. Report: Dispensing Summary");
        System.out.println("9. Filter Dispenses by Diagnosis & Doctor Specialization");
        System.out.println("10. Merge Duplicate Dispenses (same Treatment & Medicine)");
        System.out.println("0. Back to Main");
        System.out.print("Enter choice: ");
        try { return Integer.parseInt(sc.nextLine()); } catch (Exception e) { return -1; }
    }

    public int getSortChoice() {
        System.out.println("\nSort Inventory by:");
        System.out.println("1. Name (A→Z)");
        System.out.println("2. Stock (Low→High)");
        System.out.println("3. Price (Low→High)");
        System.out.print("Enter choice: ");
        try { return Integer.parseInt(sc.nextLine()); } catch (Exception e) { return -1; }
    }

    public String inputName() { System.out.print("Medicine name: "); return sc.nextLine().trim(); }
    public String inputForm() { System.out.print("Dosage form (Tablet/Syrup/...): "); return sc.nextLine().trim(); }
    public double inputPrice() { while (true) { System.out.print("Unit price (RM): "); try { return Double.parseDouble(sc.nextLine()); } catch (Exception e) {} System.out.println("Invalid."); } }
    public int inputQuantity() { while (true) { System.out.print("Quantity: "); try { return Integer.parseInt(sc.nextLine()); } catch (Exception e) {} System.out.println("Invalid."); } }
    public int inputReorderLevel() { while (true) { System.out.print("Reorder level: "); try { return Integer.parseInt(sc.nextLine()); } catch (Exception e) {} System.out.println("Invalid."); } }
    public String inputMedicineId() { System.out.print("Medicine ID: "); return sc.nextLine().trim(); }
    public String inputTreatmentId() { System.out.print("Treatment ID: "); return sc.nextLine().trim(); }

    public String inputDiagnosis() { System.out.print("Diagnosis contains: "); return sc.nextLine().trim(); }
    public String inputDoctorSpecialization() { System.out.print("Doctor specialization contains: "); return sc.nextLine().trim(); }

    public void pause() { System.out.print("\nPress Enter to continue..."); sc.nextLine(); }
}


