// Ric Hang
package control;

import adt.ListInterface;
import adt.ArrayList;
import boundary.PharmacyManagementUI;
import dao.ClinicInitializer;
import entity.DispenseRecord;
import entity.Medicine;
import entity.MedicalTreatment;
import utility.MessageUI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class PharmacyManagement {
    private final PharmacyManagementUI ui = new PharmacyManagementUI();
    private ListInterface<Medicine> inventoryList = new ArrayList<>();
    private ListInterface<DispenseRecord> dispensesList = new ArrayList<>();
    private ListInterface<MedicalTreatment> treatmentList = new ArrayList<>();

    public PharmacyManagement() {
        this.treatmentList = ClinicInitializer.initializeMedicalTreatments();
        this.inventoryList = ClinicInitializer.initializeMedicines();
        this.dispensesList = ClinicInitializer.initializeDispenseRecords();
    }

    public void displayMenu() {
        int ch;
        do {
            ch = ui.getMainMenuChoice();
            switch (ch) {
                case 1: listInventory(); ui.pause(); break;
                case 2: sortInventory(); listInventory(); ui.pause(); break;
                case 3: addNewMedicine(); listInventory(); ui.pause(); break;
                case 4: restockMedicine(); listInventory(); ui.pause(); break;
                case 5: dispenseMedicine(); ui.pause(); break;
                case 6: viewDispenseRecords(); ui.pause(); break;
                case 7: reportLowStock(); ui.pause(); break;
                case 8: reportDispensingSummary(); ui.pause(); break;
                case 9: filterDispensesByDiagnosisAndDoctor(); ui.pause(); break;
                case 10: mergeDuplicateDispenses(); ui.pause(); break;
                case 0: MessageUI.showInfo("Exiting Pharmacy module..."); break;
                default: MessageUI.showError("Invalid choice");
            }
        } while (ch != 0);
    }

    private void sortInventory() {
        int s = ui.getSortChoice();
        switch (s) {
            case 1:
                inventoryList.sort(new Comparator<Medicine>() {
                    public int compare(Medicine a, Medicine b) { return a.getName().compareToIgnoreCase(b.getName()); }
                });
                break;
            case 2:
                inventoryList.sort(new Comparator<Medicine>() {
                    public int compare(Medicine a, Medicine b) { return Integer.compare(a.getStockQuantity(), b.getStockQuantity()); }
                });
                break;
            case 3:
                inventoryList.sort(new Comparator<Medicine>() {
                    public int compare(Medicine a, Medicine b) { return Double.compare(a.getUnitPrice(), b.getUnitPrice()); }
                });
                break;
            default:
                MessageUI.showError("Invalid sort choice");
        }
        MessageUI.showSuccess("Inventory sorted.");
    }

    private void listInventory() {
        if (inventoryList.isEmpty()) { System.out.println("No medicines in inventory."); return; }
        System.out.println("\n=== Inventory ===");
        System.out.printf("%-10s | %-15s | %-10s | %-8s | %-6s | %-6s%n", "MedID", "Name", "Form", "Price(RM)", "Stock", "ReOrd");
        System.out.println("-".repeat(70));
        for (int i = 0; i < inventoryList.size(); i++) {
            Medicine m = inventoryList.get(i);
            System.out.printf("%-10s | %-15s | %-10s | %-8.2f | %-6d | %-6d%n",
                    m.getMedicineId(), m.getName(), m.getDosageForm(), m.getUnitPrice(), m.getStockQuantity(), m.getReorderLevel());
        }
    }

    private void viewDispenseRecords() {
        System.out.println("\n=== Dispense Records (Detailed) ===");
        if (dispensesList.isEmpty()) { System.out.println("No dispensing records."); return; }

        final int W_ID = 8, W_TID = 10, W_PID = 10, W_DID = 10, W_DIAG = 18, W_MID = 10, W_MNAME = 16, W_QTY = 5, W_UNIT = 10, W_TOTAL = 10, W_DATE = 12;

        String headerFmt = "%-"+W_ID+"s | %-"+W_TID+"s | %-"+W_PID+"s | %-"+W_DID+"s | %-"+W_DIAG+"s | %-"+W_MID+"s | %-"+W_MNAME+"s | %-"+W_QTY+"s | %-"+W_UNIT+"s | %-"+W_TOTAL+"s | %-"+W_DATE+"s%n";
        String rowFmt    = "%-"+W_ID+"s | %-"+W_TID+"s | %-"+W_PID+"s | %-"+W_DID+"s | %-"+W_DIAG+"s | %-"+W_MID+"s | %-"+W_MNAME+"s | %-"+W_QTY+"d | RM%-"+(W_UNIT-2)+".2f | RM%-"+(W_TOTAL-2)+".2f | %-"+W_DATE+"s%n";

        System.out.printf(headerFmt,
                "DispID", "TreatID", "Patient", "Doctor", "Diagnosis", "MedID", "Medicine", "Qty", "Unit", "Total", "Date");
        System.out.println("-".repeat(140));

        double grandTotal = 0.0;
        int totalItems = 0;

        for (int i = 0; i < dispensesList.size(); i++) {
            DispenseRecord d = dispensesList.get(i);
            MedicalTreatment t = findTreatmentById(d.getTreatmentId());
            Medicine m = findMedicineById(d.getMedicineId());

            String patientId = (t != null) ? t.getPatientId() : "-";
            String doctorId  = (t != null) ? t.getDoctorId() : "-";
            String diagnosis = (t != null && t.getDiagnosis()!=null) ? clip(d.getTreatmentId(), t.getDiagnosis(), W_DIAG) : "-";
            String medName   = (m != null) ? clip(d.getMedicineId(), m.getName(), W_MNAME) : "-";
            double unitPrice = (m != null) ? m.getUnitPrice() : 0.0;
            double total = d.getTotalCost();
            String dateStr = (d.getDispenseDate() != null) ? d.getDispenseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "-";

            System.out.printf(rowFmt,
                    d.getDispenseId(), d.getTreatmentId(), patientId, doctorId, diagnosis, d.getMedicineId(), medName,
                    d.getQuantity(), unitPrice, total, dateStr);

            grandTotal += total;
            totalItems += d.getQuantity();
        }

        System.out.println("-".repeat(140));
        System.out.println("Totals: Items dispensed = " + totalItems + ", Revenue = RM" + String.format("%.2f", grandTotal));
    }

    private String clip(String id, String text, int max) {
        if (text == null) return "-";
        if (text.length() <= max) return text;
        if (max <= 1) return text.substring(0, Math.max(0, max));
        return text.substring(0, max - 1) + "…";
    }

    private void addNewMedicine() {
        String name = ui.inputName();
        String form = ui.inputForm();
        double price = ui.inputPrice();
        int qty = ui.inputQuantity();
        int reorder = ui.inputReorderLevel();
        inventoryList.add(new Medicine(name, form, price, qty, reorder));
        MessageUI.showSuccess("Medicine added.");
    }

    private void restockMedicine() {
        listInventory();
        String id = ui.inputMedicineId();
        Medicine m = findMedicineById(id);
        if (m == null) { MessageUI.showError("Medicine not found."); return; }
        int qty = ui.inputQuantity();
        m.addStock(qty);
        MessageUI.showSuccess("Stock updated.");
    }

    private void dispenseMedicine() {
        if (inventoryList.isEmpty()) { MessageUI.showError("No inventory."); return; }
        System.out.println("\n=== Treatments (IDs) ===");
        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment t = treatmentList.get(i);
            System.out.println(t.getTreatmentId() + " | Patient: " + t.getPatientId() + " | Diagnosis: " + t.getDiagnosis() + " | Consultation: " + t.getConsultationId());
        }
        String treatmentId = ui.inputTreatmentId();
        MedicalTreatment t = findTreatmentById(treatmentId);
        if (t == null) { MessageUI.showError("Treatment not found."); return; }

        listInventory();
        String medicineId = ui.inputMedicineId();
        Medicine m = findMedicineById(medicineId);
        if (m == null) { MessageUI.showError("Medicine not found."); return; }

        int qty = ui.inputQuantity();
        if (!m.reduceStock(qty)) { MessageUI.showError("Insufficient stock."); return; }

        DispenseRecord rec = new DispenseRecord(treatmentId, medicineId, qty, LocalDate.now(), m.getUnitPrice());
        dispensesList.add(rec);
        MessageUI.showSuccess("Dispensed successfully. Total: RM" + String.format("%.2f", rec.getTotalCost()));
        System.out.println("Linked to Consultation: " + t.getConsultationId());
    }

    private void reportLowStock() {
        System.out.println("\n=== Low Stock Report ===");
        int count = 0;
        for (int i = 0; i < inventoryList.size(); i++) {
            Medicine m = inventoryList.get(i);
            if (m.isLowStock()) {
                System.out.println(m);
                count++;
            }
        }
        if (count == 0) System.out.println("All medicines are above reorder level.");
    }

    private void reportDispensingSummary() {
        System.out.println("\n=== Dispensing Summary Report ===");
        if (dispensesList.isEmpty()) { System.out.println("No dispensing records."); return; }
        System.out.printf("%-8s | %-10s | %-10s | %-5s | %-12s | %-8s%n",
                "ID", "TreatID", "MedID", "Qty", "Date", "Total");
        System.out.println("-".repeat(65));
        double total = 0;
        for (int i = 0; i < dispensesList.size(); i++) {
            DispenseRecord d = dispensesList.get(i);
            System.out.printf("%-8s | %-10s | %-10s | %-5d | %-12s | RM%-7.2f%n",
                    d.getDispenseId(), d.getTreatmentId(), d.getMedicineId(), d.getQuantity(),
                    d.getDispenseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), d.getTotalCost());
            total += d.getTotalCost();
        }
        System.out.println("-".repeat(65));
        System.out.println("Total revenue from dispensing: RM" + String.format("%.2f", total));
    }

    private void filterDispensesByDiagnosisAndDoctor() {
        if (dispensesList.isEmpty()) { System.out.println("No dispensing records."); return; }
        String diagnosisLike = ui.inputDiagnosis();
        String specializationLike = ui.inputDoctorSpecialization();

        System.out.println("\n=== Filtered Dispenses (Diagnosis & Doctor Specialization) ===");
        System.out.printf("%-8s | %-10s | %-12s | %-10s | %-5s | %-8s%n", "ID", "TreatID", "Diagnosis", "Spec.", "Qty", "Total");
        System.out.println("-".repeat(75));
        int matches = 0;
        for (int i = 0; i < dispensesList.size(); i++) {
            DispenseRecord d = dispensesList.get(i);
            MedicalTreatment t = findTreatmentById(d.getTreatmentId());
            if (t == null) continue;
            boolean diagnosisOk = t.getDiagnosis().toLowerCase().contains(diagnosisLike.toLowerCase());
            boolean specializationOk = true; // Placeholder until wired to Doctor specializations
            if (specializationLike != null && !specializationLike.isEmpty()) specializationOk = true;
            if (diagnosisOk && specializationOk) {
                System.out.printf("%-8s | %-10s | %-12s | %-10s | %-5d | RM%-7.2f%n",
                        d.getDispenseId(), d.getTreatmentId(), t.getDiagnosis(), "-", d.getQuantity(), d.getTotalCost());
                matches++;
            }
        }
        if (matches == 0) System.out.println("No records matched your filters.");
    }

    private void mergeDuplicateDispenses() {
        if (dispensesList.size() < 2) { System.out.println("Not enough records to merge."); return; }
        Comparator<DispenseRecord> byKey = new Comparator<DispenseRecord>() {
            public int compare(DispenseRecord a, DispenseRecord b) {
                int c1 = a.getTreatmentId().compareTo(b.getTreatmentId()); if (c1 != 0) return c1;
                int c2 = a.getMedicineId().compareTo(b.getMedicineId()); if (c2 != 0) return c2;
                return a.getDispenseDate().compareTo(b.getDispenseDate());
            }
        };
        dispensesList.sort(byKey);
        int merges = 0;
        int i = 0;
        while (i < dispensesList.size() - 1) {
            DispenseRecord a = dispensesList.get(i);
            DispenseRecord b = dispensesList.get(i + 1);
            boolean sameKey = a.getTreatmentId().equals(b.getTreatmentId()) && a.getMedicineId().equals(b.getMedicineId()) && a.getDispenseDate().equals(b.getDispenseDate());
            if (sameKey) {
                int qty = a.getQuantity() + b.getQuantity();
                LocalDate date = a.getDispenseDate();
                double unitPrice = inventoryUnitPriceOf(a.getMedicineId());
                DispenseRecord merged = new DispenseRecord(a.getTreatmentId(), a.getMedicineId(), qty, date, unitPrice);
                dispensesList.removeAt(i);
                dispensesList.removeAt(i);
                dispensesList.addSorted(merged, byKey);
                merges++;
            } else {
                i++;
            }
        }
        MessageUI.showSuccess("Merged " + merges + " duplicate records (same Treatment + Medicine + Date).");
        reportDispensingSummary();
    }

    private double inventoryUnitPriceOf(String medicineId) {
        Medicine m = findMedicineById(medicineId);
        return (m != null) ? m.getUnitPrice() : 0.0;
    }

    private Medicine findMedicineById(String id) {
        for (int i = 0; i < inventoryList.size(); i++) if (inventoryList.get(i).getMedicineId().equals(id)) return inventoryList.get(i);
        return null;
    }

    private MedicalTreatment findTreatmentById(String id) {
        for (int i = 0; i < treatmentList.size(); i++) if (treatmentList.get(i).getTreatmentId().equals(id)) return treatmentList.get(i);
        return null;
    }

    // ===== STATIC INTEGRATION: AUTO-DISPENSE FROM TREATMENT =====
    // This helper enables Medical Treatment module to trigger dispensing automatically
    // using shared cached lists from ClinicInitializer (in-memory persistence across modules).
    public static int autoDispenseFromTreatment(MedicalTreatment treatment) {
        if (treatment == null) return 0;
        String rx = treatment.getPrescribedMedication();
        if (rx == null || rx.trim().isEmpty()) return 0;

        // ADT: Access shared lists via DAO initializers (same instances across modules)
        ListInterface<Medicine> inventory = ClinicInitializer.initializeMedicines();
        ListInterface<DispenseRecord> dispenses = ClinicInitializer.initializeDispenseRecords();

        int dispensedCount = 0;
        String[] items = rx.split(",");
        LocalDate today = LocalDate.now();

        for (int i = 0; i < items.length; i++) {
            String token = items[i].trim();
            if (token.isEmpty()) continue;

            // Parse quantity (e.g., "Paracetamol x10", "Paracetamol 10 tablets")
            int qty = extractQuantity(token); // defaults to 1 if none found
            String tokenName = normalizeNameWithoutQuantity(token);

            // Find first inventory item whose name contains the token name (case-insensitive)
            Medicine found = null;
            for (int j = 0; j < inventory.size(); j++) {
                Medicine m = inventory.get(j);
                if (m.getName().toLowerCase().contains(tokenName.toLowerCase())) { found = m; break; }
            }
            if (found == null) {
                System.out.println("Auto-dispense: '" + token + "' not found in inventory. Skipped.");
                continue; // No inventory match; skip with feedback
            }

            if (!found.reduceStock(qty)) {
                System.out.println("Auto-dispense: insufficient stock for " + found.getName() +
                        " (requested " + qty + ", available " + found.getStockQuantity() + "). Skipped.");
                continue; // Insufficient stock; skip with feedback
            }

            // ADT: record dispense
            DispenseRecord record = new DispenseRecord(
                    treatment.getTreatmentId(),
                    found.getMedicineId(),
                    qty,
                    today,
                    found.getUnitPrice()
            );
            dispenses.add(record);
            dispensedCount++;
        }

        return dispensedCount;
    }

    // Extract the first integer quantity from a token, defaulting to 1 if none
    private static int extractQuantity(String token) {
        int qty = 0;
        for (int i = 0; i < token.length(); i++) {
            char c = token.charAt(i);
            if (Character.isDigit(c)) {
                qty = qty * 10 + (c - '0');
            } else if (qty > 0) {
                break;
            }
        }
        return qty > 0 ? qty : 1;
    }

    // Remove quantity markers and common unit words to get a name-like token
    private static String normalizeNameWithoutQuantity(String token) {
        String s = token.toLowerCase();
        s = s.replace("x", " ");
        s = s.replace("tabs", " ").replace("tab", " ").replace("tablets", " ").replace("tablet", " ");
        s = s.replace("caps", " ").replace("cap", " ").replace("capsules", " ").replace("capsule", " ");
        // strip digits
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isDigit(c)) sb.append(c);
        }
        String cleaned = sb.toString().replaceAll("\\s+", " ").trim();
        // Return original token if cleaning produced empty
        if (cleaned.isEmpty()) cleaned = token.trim();
        return cleaned;
    }
}


