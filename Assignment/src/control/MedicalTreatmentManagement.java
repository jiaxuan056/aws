//Wai Kin
package control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import boundary.MedicalTreatmentUI;
import entity.MedicalTreatment;
import entity.Patient;
import entity.Doctor;
import entity.Consultation;
import adt.SortedArrayList;
import adt.ListInterface;
import dao.ClinicInitializer;

public class MedicalTreatmentManagement {
    final MedicalTreatmentUI treatmentUI = new MedicalTreatmentUI();
    private ListInterface<MedicalTreatment> treatmentList = new SortedArrayList<>(); //ADT object declared 
    private ListInterface<Patient> patientList = new SortedArrayList<>();; //ADT object declared 
    private ListInterface<Doctor> doctorList = new SortedArrayList<>();; //ADT object declared 
    private ListInterface<Consultation> consultationList = new SortedArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    // Feature flag: set to true to auto-dispense after adding treatment
    private static final boolean AUTO_DISPENSE_ENABLED = false;

    private void maybeAutoDispense(MedicalTreatment treatment) {
        if (AUTO_DISPENSE_ENABLED) {
            dispenseMedicineForTreatment(treatment);
        }
    }

    public MedicalTreatmentManagement() {
        // Initialize with sample data from DAO
        this.patientList = ClinicInitializer.initializePatients();
        this.doctorList = ClinicInitializer.initializeDoctors();
        this.treatmentList = ClinicInitializer.initializeMedicalTreatments();
        this.consultationList = ClinicInitializer.initializeConsultations();
    }

    public void displayMenu() {
        int choice;
        do {
            choice = treatmentUI.getMainMenuChoice();
            switch (choice) {
                case 1:
                    addTreatmentAndDispenseMedicine();
                    break;
                case 2:
                    updateTreatment();
                    break;
                case 3:
                    deleteTreatment();
                    break;
                case 4:
                    searchTreatment();
                    break;
                case 5:
                    viewTreatmentHistory();
                    break;
                case 6:
                    scheduleFollowUp();
                    break;
                case 7:
                    generateTreatmentHistoryReport();
                    break;
                case 8:
                    generateSummaryAnalysisReport();
                    break;
                case 9:
                    mergeDuplicateTreatmentsSameDay();
                    break;
                case 0:
                    System.out.println("Exiting Medical Treatment Management...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    // ===== 1. ADD TREATMENT & DISPENSE MEDICINE (STREAMLINED WORKFLOW) =====
    private void addTreatmentAndDispenseMedicine() {
        System.out.println("\n=== ADD TREATMENT & DISPENSE MEDICINE ===");
        
        System.out.println("\n--- Available Consultations ---");
        // ADT method called from ListInterface class (iterator)
        for (Consultation consultation : consultationList) { // ADT method called from ListInterface class
            System.out.println(consultation.getConsultationID() + " | Patient: " + consultation.getPatientId() + 
                             " | Doctor: " + consultation.getDoctorId() + " | Status: " + consultation.getStatus());
        }
        
        String consultationId = treatmentUI.inputConsultationId();
        Consultation consultation = findConsultationById(consultationId, consultationList);
        
        if (consultation == null) {
            treatmentUI.displayError("Consultation not found.");
            return;
        }
        
        if (!"COMPLETED".equalsIgnoreCase(consultation.getStatus())) {
            treatmentUI.displayError("Consultation must be COMPLETED before adding treatment.");
            return;
        }
        
        // Derive patient and doctor from the selected consultation
        String patientId = consultation.getPatientId();
        String doctorId = consultation.getDoctorId();
        
        Patient patient = findPatientById(patientId);
        Doctor doctor = findDoctorById(doctorId);
        
        if (patient == null) {
            treatmentUI.displayError("Patient not found.");
            return;
        }
        if (doctor == null) {
            treatmentUI.displayError("Doctor not found.");
            return;
        }
        
        String diagnosis = treatmentUI.inputDiagnosis();
        if (diagnosis.isEmpty()) {
            treatmentUI.displayError("Diagnosis cannot be empty.");
            return;
        }
        
        String symptoms = treatmentUI.inputSymptoms();
        String medicalCondition = treatmentUI.inputCondition();
        String treatmentPlan = treatmentUI.inputTreatmentPlan();
        String prescribedMedication = treatmentUI.inputPrescribedMedication();
        LocalDate treatmentDate = treatmentUI.inputTreatmentDate();
        LocalDate treatmentEndDate = treatmentUI.inputEndDate();
        double treatmentCost = treatmentUI.inputTreatmentCost();
        
        MedicalTreatment newTreatment = new MedicalTreatment(patientId, doctorId, consultationId, diagnosis, 
                                                           symptoms, medicalCondition, treatmentPlan, 
                                                           prescribedMedication, treatmentDate, 
                                                           treatmentEndDate, treatmentCost);
        // ADT method called from ListInterface class
        treatmentList.add(newTreatment); // ADT method called from ListInterface class
        // Optionally trigger pharmacy dispensing (feature-flagged)
        maybeAutoDispense(newTreatment);
        // Always attempt auto-dispense through Pharmacy integration (ensures at least a try)
        int autoDispensed = control.PharmacyManagement.autoDispenseFromTreatment(newTreatment);
        if (autoDispensed > 0) {
            System.out.println("Auto-dispensed " + autoDispensed + " item(s) from inventory.");
        } else {
            System.out.println("No items auto-dispensed (no inventory match or insufficient stock).");
        }
        
        treatmentUI.displaySuccess("New treatment added successfully!");
        System.out.println("Treatment ID: " + newTreatment.getTreatmentId());
        System.out.println("Linked to Consultation: " + consultationId);
        
        // Optional: Launch dispensing step here if needed
        // System.out.println("\n=== MEDICINE DISPENSING ===");
        // dispenseMedicineForTreatment(newTreatment);

        System.out.print("\nPress Enter to return to the menu...");
        scanner.nextLine();
    }

    // ===== 2. UPDATE TREATMENT =====
    private void updateTreatment() {
        System.out.println("\n=== UPDATE TREATMENT ===");
        String treatmentId = treatmentUI.inputTreatmentId();
        
        MedicalTreatment treatment = findTreatmentById(treatmentId);
        if (treatment == null) {
            treatmentUI.displayError("Treatment not found.");
            return;
        }
        
        System.out.println("Current Treatment Details:");
        System.out.println(treatment);
        
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        String newDiagnosis = treatmentUI.inputDiagnosis();
        if (!newDiagnosis.isEmpty()) {
            treatment.setDiagnosis(newDiagnosis);
            System.out.println("Diagnosis updated to: " + newDiagnosis);
        }
        
        String newTreatmentPlan = treatmentUI.inputTreatmentPlan();
        if (!newTreatmentPlan.isEmpty()) {
            treatment.setTreatmentPlan(newTreatmentPlan);
            System.out.println("Treatment plan updated to: " + newTreatmentPlan);
        }
        
        treatmentUI.displaySuccess("Treatment updated successfully!");
    }

    // ===== 3. DELETE TREATMENT =====
    private void deleteTreatment() {
        System.out.println("\n=== DELETE TREATMENT ===");
        String treatmentId = treatmentUI.inputTreatmentId();
        
        MedicalTreatment treatment = findTreatmentById(treatmentId);
        if (treatment == null) {
            treatmentUI.displayError("Treatment not found.");
            return;
        }
        
        System.out.println("Treatment to delete:");
        System.out.println(treatment);
        
        String confirm = treatmentUI.inputConfirmation("Are you sure you want to delete this treatment? (yes/no): ");
        if (confirm.equalsIgnoreCase("yes")) {
            treatmentList.remove(treatment);
            treatmentUI.displaySuccess("Treatment deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // ===== 4. SEARCH TREATMENT =====
    private void searchTreatment() {
        System.out.println("\n=== SEARCH TREATMENT ===");
        System.out.println("Search Options:");
        System.out.println("1. Search by Treatment ID");
        System.out.println("2. Search by Patient Name");
        System.out.println("3. Search by Doctor Specialization");
        System.out.println("4. Search by Date");
        
        int choice = treatmentUI.inputSearchChoice();
        
            switch (choice) {
                case 1:
                searchByTreatmentId();
                    break;
                case 2:
                searchByPatientName();
                    break;
                case 3:
                searchByDoctorSpecialization();
                    break;
                case 4:
                searchByDate();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
    }

    // ===== ADVANCED SEARCH METHODS WITH MULTIPLE ENTITY DEPENDENCIES =====
    
    /**
     * Search by Treatment ID (basic search)
     */
    private void searchByTreatmentId() {
        System.out.println("\n=== SEARCH BY TREATMENT ID ===");
        String treatmentId = treatmentUI.inputTreatmentId();
        
        MedicalTreatment treatment = findTreatmentById(treatmentId);
        if (treatment == null) {
            treatmentUI.displayError("Treatment not found.");
            return;
        }
        
        System.out.println("Treatment Details:");
        System.out.println(treatment);
    }
    
    /**
     * Search by Patient Name with Cross-Entity Analysis
     * Demonstrates: Patient → MedicalTreatment → Doctor (3-class dependency)
     */
    private void searchByPatientName() {
        System.out.println("\n=== SEARCH BY PATIENT NAME ===");
        System.out.print("Enter Patient Name (or partial name): ");
        String patientName = scanner.nextLine().trim();
        
        // ADT: collect matches using ListInterface.add() on a SortedArrayList
        ListInterface<Patient> matchingPatients = new SortedArrayList<>(); // ADT object declared
        
        // ADT: traverse patientList via iterator()
        for (Patient patient : matchingPatients) { // ADT method called from ListInterface class
            if (patient.getName().toLowerCase().contains(patientName.toLowerCase())) {
                // ADT: add to results using ListInterface.add()
                matchingPatients.add(patient); // ADT method called from ListInterface class
            }
        }
        
        if (matchingPatients.isEmpty()) {
            System.out.println("No patients found with name: " + patientName);
            return;
        }
        
        System.out.println("Patients with Name: " + patientName);
        System.out.printf("%-15s | %-20s | %-15s | %-15s | %-15s%n", 
                         "Patient ID", "Patient Name", "Age", "Gender", "Treatment Count");
        System.out.println("-".repeat(85));
        
        // ADT: iterate results via iterator()
        for (Patient patient : matchingPatients) { // ADT: iterator()
            String patientId = patient.getPatientId();
            
            // Use ADT methods to calculate treatment count
            int treatmentCount = countTreatmentsForPatient(patientId);
            
            System.out.printf("%-15s | %-20s | %-15d | %-15s | %-15d%n", 
                patientId, patient.getName(), patient.getAge(), patient.getGender(), treatmentCount);
        }
        
        System.out.println("-".repeat(85));
        System.out.println("Total patients found: " + matchingPatients.size());
    }
    
    /**
     * Search by Doctor Specialization with Treatment Analysis
     * Demonstrates: Doctor → MedicalTreatment → Patient (3-class dependency)
     */
    private void searchByDoctorSpecialization() {
        System.out.println("\n=== SEARCH BY DOCTOR SPECIALIZATION ===");
        String specialization = treatmentUI.inputSpecialization();
        
        // ADT: collect matching doctors using ListInterface.add()
        ListInterface<Doctor> matchingDoctors = new SortedArrayList<>(); // ADT object declared
        
        // ADT: traverse doctorList via iterator()
        for (Doctor doctor : matchingDoctors) { // ADT method called from ListInterface class
            if (doctor.getSpecialization().toLowerCase().contains(specialization.toLowerCase())) {
                // ADT: add to results using ListInterface.add()
                matchingDoctors.add(doctor); // ADT method called from ListInterface class
            }
        }
        
        if (matchingDoctors.isEmpty()) {
            System.out.println("No doctors found with specialization: " + specialization);
            return;
        }
        
        System.out.println("Doctors with Specialization: " + specialization);
        System.out.printf("%-15s | %-20s | %-15s | %-15s | %-15s%n", 
                         "Doctor ID", "Doctor Name", "Specialization", "Treatment Count", "Total Revenue");
        System.out.println("-".repeat(85));
        
        // ADT: iterate results via iterator()
        for (Doctor doctor : matchingDoctors) { // ADT: iterator()
            String doctorId = doctor.getId();
            
            // Use ADT methods to calculate statistics
            int treatmentCount = countTreatmentsForDoctor(doctorId);
            double totalRevenue = calculateTotalRevenueForDoctor(doctorId);
            
            System.out.printf("%-15s | %-20s | %-15s | %-15d | RM%-12.2f%n", 
                doctorId, doctor.getName(), doctor.getSpecialization(), treatmentCount, totalRevenue);
        }
        
        System.out.println("-".repeat(85));
        System.out.println("Total doctors found: " + matchingDoctors.size());
    }
    
    /** Search by Date (exact date match) with sortable results */
    private void searchByDate() {
        System.out.println("\n=== SEARCH BY DATE ===");
        System.out.print("Enter Date (dd/MM/yyyy): ");
        String dateStr = scanner.nextLine().trim();
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            System.out.println("Invalid date format. Use dd/MM/yyyy.");
            return;
        }
        
        // ADT: collect matches
        ListInterface<MedicalTreatment> results = new SortedArrayList<>(); // ADT object declared 
        for (MedicalTreatment t : treatmentList) { // ADT: iterator()
            if (t.getTreatmentDate() != null && t.getTreatmentDate().isEqual(date)) {
                results.add(t); // ADT: add()
            }
        }

        if (results.size() == 0) {
            System.out.println("No treatments found on this date.");
            return;
        }
        
        sortTreatmentsInteractive(results);
        printTreatmentListCompact(results);
    }

    // ===== Helpers: sorting and printing treatment lists =====
    private void sortTreatmentsInteractive(ListInterface<MedicalTreatment> list) {
        System.out.println("\nSort by: 1=Date  2=Cost  3=Diagnosis  4=Doctor ID");
        System.out.print("Enter choice: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            choice = 1;
        }

            switch (choice) {
                case 2:
                list.sort(new java.util.Comparator<MedicalTreatment>() {
                    public int compare(MedicalTreatment a, MedicalTreatment b) {
                        return Double.compare(a.getTreatmentCost(), b.getTreatmentCost());
                    }
                });
                    break;
                case 3:
                list.sort(new java.util.Comparator<MedicalTreatment>() {
                    public int compare(MedicalTreatment a, MedicalTreatment b) {
                        String da = (a.getDiagnosis() == null) ? "" : a.getDiagnosis();
                        String db = (b.getDiagnosis() == null) ? "" : b.getDiagnosis();
                        return da.compareToIgnoreCase(db);
                    }
                });
                    break;
            case 4:
                list.sort(new java.util.Comparator<MedicalTreatment>() {
                    public int compare(MedicalTreatment a, MedicalTreatment b) {
                        return a.getDoctorId().compareTo(b.getDoctorId());
                    }
                });
                    break;
            case 1:
                default:
                list.sort(new java.util.Comparator<MedicalTreatment>() {
                    public int compare(MedicalTreatment a, MedicalTreatment b) {
                        return a.getTreatmentDate().compareTo(b.getTreatmentDate());
                    }
                });
        }
    }

    private void printTreatmentListCompact(ListInterface<MedicalTreatment> list) {
        System.out.printf("%-12s | %-12s | %-12s | %-20s | %-12s | %-10s%n",
                "Treatment ID", "Patient ID", "Doctor ID", "Diagnosis", "Date", "Cost (RM)");
        System.out.println("-".repeat(80));
        for (int i = 0; i < list.size(); i++) {
            MedicalTreatment t = list.get(i);
            System.out.printf("%-12s | %-12s | %-12s | %-20s | %-12s | RM%-9.2f%n",
                    t.getTreatmentId(), t.getPatientId(), t.getDoctorId(),
                    t.getDiagnosis(),
                    t.getTreatmentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    t.getTreatmentCost());
        }
    }

    // ===== 5. VIEW TREATMENT HISTORY =====
    private void viewTreatmentHistory() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("    TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                    MEDICAL TREATMENT MODULE SUBSYSTEM");
        System.out.println("                    TREATMENT HISTORY REPORT");
        System.out.println("=".repeat(80));
        System.out.println("Generated on: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("Generated at: " + java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println("HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println("-".repeat(80));

        // ADT: check collection emptiness via ListInterface.size()
        if (treatmentList.size() == 0) {
            System.out.println("No treatments found.");
            return;
        }
        
        System.out.println("ALL TREATMENTS OVERVIEW");
        System.out.println("-".repeat(230));
        
        final int W_ID = 12;
        final int W_PID = 12;
        final int W_DID = 12;
        final int W_DIAG = 18;
        final int W_PLAN = 56;
        final int W_MED = 32;
        final int W_DATE = 12;
        final int W_COST = 10;

        System.out.printf("%-" + W_ID + "s | %-" + W_PID + "s | %-" + W_DID + "s | %-" + W_DIAG + "s | %-" + W_PLAN + "s | %-" + W_MED + "s | %-" + W_DATE + "s | %-" + W_COST + "s%n",
                "Treatment ID", "Patient ID", "Doctor ID", "Diagnosis", "Plan", "Medication", "Date", "Cost (RM)");
        System.out.println("-".repeat(230));
        
        double totalCost = 0.0;
        // ADT: iterate treatments via size()/get(i)
        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment treatment = treatmentList.get(i);
            String diag = clipText(treatment.getDiagnosis(), W_DIAG);
            String plan = clipText(treatment.getTreatmentPlan(), W_PLAN);
            String med = clipText(treatment.getPrescribedMedication(), W_MED);

            System.out.printf("%-" + W_ID + "s | %-" + W_PID + "s | %-" + W_DID + "s | %-" + W_DIAG + "s | %-" + W_PLAN + "s | %-" + W_MED + "s | %-" + W_DATE + "s | RM%-9.2f%n",
                            treatment.getTreatmentId(),
                            treatment.getPatientId(),
                            treatment.getDoctorId(),
                diag,
                plan,
                med,
                treatment.getTreatmentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                treatment.getTreatmentCost());
            totalCost += treatment.getTreatmentCost();
        }
        
        System.out.println("-".repeat(150));
        System.out.println("SUMMARY:");
        // ADT: derive counts from ListInterface.size()
        System.out.println("Total Number of Treatments: " + treatmentList.size());
        System.out.println("Total Treatment Cost: RM " + String.format("%.2f", totalCost));
        System.out.println("Average Cost per Treatment: RM " + String.format("%.2f", totalCost / treatmentList.size()));
        System.out.println("=".repeat(80));
        System.out.println("END OF THE REPORT");
        System.out.println("=".repeat(80));
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    /** Utility: clip long text to fit column width */
    private String clipText(String text, int maxWidth) {
        if (text == null) {
            return "";
        }
        if (text.length() <= maxWidth) {
            return text;
        }
        if (maxWidth <= 1) {
            return text.substring(0, Math.max(0, maxWidth));
        }
        return text.substring(0, maxWidth - 1) + "…"; // ellipsis
    }

    // ===== 6. SCHEDULE FOLLOW-UP =====
    private void scheduleFollowUp() {
        System.out.println("\n=== SCHEDULE FOLLOW-UP ===");
        String treatmentId = treatmentUI.inputTreatmentId();
        
        MedicalTreatment treatment = findTreatmentById(treatmentId);
        if (treatment == null) {
            treatmentUI.displayError("Treatment not found.");
            return;
        }

        System.out.println("Current Treatment:");
        System.out.println(treatment);
        
        LocalDate followUpDate = treatmentUI.inputFollowUpDate();
        treatment.setFollowUpDate(followUpDate);
        System.out.println("Follow-up scheduled for: " + followUpDate);
        treatmentUI.displaySuccess("Follow-up appointment scheduled successfully!");
    }

    // ===== 7. TREATMENT HISTORY REPORT (PER PATIENT) =====
    private void generateTreatmentHistoryReport() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("    TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                    MEDICAL TREATMENT MODULE SUBSYSTEM");
        System.out.println("                    TREATMENT HISTORY REPORT (PER PATIENT)");
        System.out.println("=".repeat(80));
        System.out.println("Generated on: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("Generated at: " + java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println("HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println("-".repeat(80));

        String patientId = treatmentUI.inputPatientId();
        
        System.out.println("\nTREATMENT HISTORY FOR PATIENT: " + patientId);
        System.out.println("-".repeat(80));
        
        Patient patient = findPatientById(patientId);
        String patientName = (patient != null) ? patient.getName() : "Unknown";
        System.out.println("Patient Name: " + patientName);
        System.out.println("Patient ID: " + patientId);
        System.out.println("-".repeat(80));
        
        System.out.printf("%-12s | %-12s | %-20s | %-12s | %-10s%n", 
                         "Treatment ID", "Doctor ID", "Diagnosis", "Date", "Cost (RM)");
        System.out.println("-".repeat(80));
            
            boolean found = false;
        double totalCost = 0.0;
        int treatmentCount = 0;
        
        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment treatment = treatmentList.get(i);
            if (treatment.getPatientId().equals(patientId)) {
                System.out.printf("%-12s | %-12s | %-20s | %-12s | RM%-9.2f%n",
                            treatment.getTreatmentId(),
                            treatment.getDoctorId(),
                    treatment.getDiagnosis(),
                    treatment.getTreatmentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    treatment.getTreatmentCost());
                    found = true;
                totalCost += treatment.getTreatmentCost();
                treatmentCount++;
                }
            }
            
            if (!found) {
            System.out.println("No treatments found for this patient.");
        } else {
        System.out.println("-".repeat(80));
            System.out.println("SUMMARY:");
            System.out.println("Total Number of Treatments: " + treatmentCount);
            System.out.println("Total Treatment Cost: RM " + String.format("%.2f", totalCost));
            System.out.println("Average Cost per Treatment: RM " + String.format("%.2f", totalCost / treatmentCount));
        }
        
        System.out.println("=".repeat(80));
        System.out.println("END OF THE REPORT");
        System.out.println("=".repeat(80));
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }


    // analyzeCostDistribution removed as bar chart output is not required in view history

    // ===== 10. NEW SUMMARY ANALYSIS REPORT =====
    public void generateSummaryAnalysisReport() {
        System.out.println("\n\n" + "=".repeat(80));
        System.out.println("          TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("                    MEDICAL TREATMENT MODULE SUBSYSTEM");
        System.out.println("                       SUMMARY OF TREATMENT ANALYSIS");
        System.out.println("\nGenerated at: " + java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("=".repeat(80));
        System.out.println("TUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY HIGHLY CONFIDENTIAL DOCUMENT");
        System.out.println("-".repeat(80));

        System.out.printf("%-12s | %-12s | %-12s | %-20s | %-12s | %-10s%n", 
                         "Treatment ID", "Patient ID", "Doctor ID", "Diagnosis", "Status", "Cost (RM)");
        System.out.println("-".repeat(80));
        
        double totalCost = 0.0;
        MedicalTreatment mostExpensive = null;
        MedicalTreatment leastExpensive = null;

        if (treatmentList.size() > 0) {
            mostExpensive = treatmentList.get(0);
            leastExpensive = treatmentList.get(0);
        }

        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment t = treatmentList.get(i);
            System.out.printf("%-12s | %-12s | %-12s | %-20s | %-12s | RM%-9.2f%n",
                t.getTreatmentId(), t.getPatientId(), t.getDoctorId(),
                t.getDiagnosis(), t.getTreatmentStatus(), t.getTreatmentCost());
            totalCost += t.getTreatmentCost();
            
            if (t.getTreatmentCost() > mostExpensive.getTreatmentCost()) {
                mostExpensive = t;
            }
            if (t.getTreatmentCost() < leastExpensive.getTreatmentCost()) {
                leastExpensive = t;
            }
        }
        System.out.println("-".repeat(80));

        System.out.println("Total Number of Treatments: " + treatmentList.size());
        System.out.println("Total Treatment Cost: RM " + String.format("%.2f", totalCost));
        System.out.println("\n");
        
        System.out.println("GRAPHICAL REPRESENTATION OF MEDICAL TREATMENT MODULE");
        System.out.println("-".repeat(80));

        String[] diagnoses = new String[treatmentList.size()];
        int[] diagnosisCounts = new int[treatmentList.size()];
        int uniqueDiagnoses = 0;
        for (int i = 0; i < treatmentList.size(); i++) {
            String diagnosis = treatmentList.get(i).getDiagnosis();
            boolean found = false;
            for (int j = 0; j < uniqueDiagnoses; j++) {
                if (diagnoses[j].equalsIgnoreCase(diagnosis)) {
                    diagnosisCounts[j]++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                diagnoses[uniqueDiagnoses] = diagnosis;
                diagnosisCounts[uniqueDiagnoses] = 1;
                uniqueDiagnoses++;
            }
        }
        String[] finalDiagnoses = new String[uniqueDiagnoses];
        int[] finalDiagnosisCounts = new int[uniqueDiagnoses];
        System.arraycopy(diagnoses, 0, finalDiagnoses, 0, uniqueDiagnoses);
        System.arraycopy(diagnosisCounts, 0, finalDiagnosisCounts, 0, uniqueDiagnoses);
        printVerticalBarChart("Treatments by Diagnosis", finalDiagnoses, finalDiagnosisCounts, 10);

        int activeCount = 0, completedCount = 0, discontinuedCount = 0;
        for (int i = 0; i < treatmentList.size(); i++) {
            switch (treatmentList.get(i).getTreatmentStatus()) {
                case "Active": activeCount++; break;
                case "Completed": completedCount++; break;
                case "Discontinued": discontinuedCount++; break;
            }
        }
        printVerticalBarChart("Treatments by Status", 
            new String[]{"Active", "Completed", "Discontinued"}, 
            new int[]{activeCount, completedCount, discontinuedCount}, 10);
        
        String mostCommonDiagnosis = "";
        int maxCount = 0;
        for (int i = 0; i < uniqueDiagnoses; i++) {
            if (diagnosisCounts[i] > maxCount) {
                maxCount = diagnosisCounts[i];
                mostCommonDiagnosis = diagnoses[i];
            }
        }
        System.out.println("\nDiagnosis with the most treatments (" + maxCount + "):");
        System.out.println("< " + mostCommonDiagnosis + " >");
        
        if (mostExpensive != null) {
            System.out.println("\nMost expensive treatment (RM " + String.format("%.2f", mostExpensive.getTreatmentCost()) + "):");
            System.out.println("< " + mostExpensive.getDiagnosis() + " for Patient " + mostExpensive.getPatientId() + " >");
        }
        
        if (leastExpensive != null) {
            System.out.println("\nLeast expensive treatment (RM " + String.format("%.2f", leastExpensive.getTreatmentCost()) + "):");
            System.out.println("< " + leastExpensive.getDiagnosis() + " for Patient " + leastExpensive.getPatientId() + " >");
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("END OF THE REPORT");
        System.out.println("=".repeat(80));
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // ===== 11. MERGE DUPLICATE TREATMENTS (SAME PATIENT & DOCTOR, SAME DAY) =====
    private void mergeDuplicateTreatmentsSameDay() {
        if (treatmentList.size() < 2) {
            System.out.println("Not enough treatments to merge.");
            return;
        }

        // Sort by PatientID, then DoctorID, then TreatmentDate
        treatmentList.sort(new java.util.Comparator<MedicalTreatment>() {
            @Override
            public int compare(MedicalTreatment a, MedicalTreatment b) {
                int c1 = a.getPatientId().compareTo(b.getPatientId());
                if (c1 != 0) return c1;
                int c2 = a.getDoctorId().compareTo(b.getDoctorId());
                if (c2 != 0) return c2;
                return a.getTreatmentDate().compareTo(b.getTreatmentDate());
            }
        });

        int merges = 0;
        int i = 0;
        while (i < treatmentList.size() - 1) {
            MedicalTreatment a = treatmentList.get(i);
            MedicalTreatment b = treatmentList.get(i + 1);
            boolean samePatient = a.getPatientId().equals(b.getPatientId());
            boolean sameDoctor = a.getDoctorId().equals(b.getDoctorId());
            boolean sameDay = a.getTreatmentDate().equals(b.getTreatmentDate());
            if (samePatient && sameDoctor && sameDay) {
                // Merge b into a: sum costs, extend end date, concatenate notes/plan/medication
                double newCost = a.getTreatmentCost() + b.getTreatmentCost();
                a.setTreatmentCost(newCost);
                java.time.LocalDate maxEnd = a.getTreatmentEndDate();
                if (b.getTreatmentEndDate() != null && (maxEnd == null || b.getTreatmentEndDate().isAfter(maxEnd))) {
                    maxEnd = b.getTreatmentEndDate();
                }
                if (maxEnd != null) a.setTreatmentEndDate(maxEnd);

                String combinedPlan = a.getTreatmentPlan();
                if (b.getTreatmentPlan() != null && !b.getTreatmentPlan().isEmpty()) {
                    combinedPlan = (combinedPlan == null || combinedPlan.isEmpty()) ? b.getTreatmentPlan() : combinedPlan + " | " + b.getTreatmentPlan();
                }
                a.setTreatmentPlan(combinedPlan);

                String combinedRx = a.getPrescribedMedication();
                if (b.getPrescribedMedication() != null && !b.getPrescribedMedication().isEmpty()) {
                    combinedRx = (combinedRx == null || combinedRx.isEmpty()) ? b.getPrescribedMedication() : combinedRx + ", " + b.getPrescribedMedication();
                }
                a.setPrescribedMedication(combinedRx);

                String notes = a.getFollowUpNotes();
                if (b.getFollowUpNotes() != null && !b.getFollowUpNotes().isEmpty()) {
                    notes = (notes == null || notes.isEmpty()) ? b.getFollowUpNotes() : notes + " | " + b.getFollowUpNotes();
                }
                a.setFollowUpNotes(notes);

                // Remove the merged duplicate (b)
                treatmentList.removeAt(i + 1);
                merges++;
                // Do not increment i to check if more duplicates follow
            } else {
                i++;
            }
        }

        System.out.println("Merged " + merges + " duplicate treatment(s) (same patient & doctor on the same day).");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // ===== OPTIONAL: Dispensing workflow helper =====
    // This method is intentionally kept for Pharmacy integration when
    // auto-dispense is enabled after adding a treatment.
    private void dispenseMedicineForTreatment(MedicalTreatment treatment) {
        System.out.println("\n=== MEDICINE DISPENSING ===");
        System.out.println("Treatment: " + treatment.getDiagnosis());
        System.out.println("Prescribed Medication: " + treatment.getPrescribedMedication());
        
        System.out.println("Medicine dispensing completed for treatment: " + treatment.getTreatmentId());
        System.out.println("Please proceed to Pharmacy Management for detailed dispensing records.");
    }

    // ===== HELPER METHODS =====
    private Consultation findConsultationById(String consultationId, ListInterface<Consultation> consultations) {
        for (int i = 0; i < consultations.size(); i++) {
            Consultation consultation = consultations.get(i);
            if (consultation.getConsultationID().equals(consultationId)) {
                return consultation;
            }
        }
        return null;
    }

    private Patient findPatientById(String patientId) {
        for (int i = 0; i < patientList.size(); i++) {
            Patient patient = patientList.get(i);
            if (patient.getPatientId().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }

    private Doctor findDoctorById(String doctorId) {
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor doctor = doctorList.get(i);
            if (doctor.getId().equals(doctorId)) {
                return doctor;
            }
        }
        return null;
    }

    private MedicalTreatment findTreatmentById(String treatmentId) {
        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment treatment = treatmentList.get(i);
            if (treatment.getTreatmentId().equals(treatmentId)) {
                return treatment;
            }
        }
        return null;
    }

    private String generateBarChart(int value, int maxValue) {
        if (maxValue <= 0) return "";
        
        int barLength = Math.max(1, Math.min(20, (int) Math.round((value * 20.0) / maxValue)));
        return "█".repeat(barLength);
    }

    // analyzeCostDistribution removed as bar chart output is not required in view history
    
    private void printVerticalBarChart(String title, String[] labels, int[] counts, int maxHeight) {
        System.out.println("\n" + title);
        System.out.println("-".repeat(title.length()));

        int maxValue = 0;
        for (int count : counts) {
            if (count > maxValue) {
                maxValue = count;
            }
        }
        if (maxValue == 0) maxValue = 1; // Avoid division by zero

        for (int i = maxHeight; i > 0; i--) {
            System.out.printf("%2d | ", i);
            for (int j = 0; j < counts.length; j++) {
                double scaledValue = (double) counts[j] / maxValue * maxHeight;
                if (scaledValue >= i) {
                    System.out.print(" ███    ");
                } else {
                    System.out.print("        ");
                }
            }
            System.out.println();
        }

        System.out.println("---|--------" + "--------".repeat(counts.length));
        System.out.print("   | ");
        for (String label : labels) {
            System.out.printf(" %-7s", label.length() > 7 ? label.substring(0, 6) + "." : label);
        }
        System.out.println("\n   '-------------------------------------> " + "Category");
        System.out.println();
    }

    // ===== HELPER METHODS FOR ADVANCED SEARCHING =====
    
    private int countUniqueDoctorsForPatient(String patientId) {
        ListInterface<String> uniqueDoctors = new SortedArrayList<>();
        
        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment treatment = treatmentList.get(i);
            if (treatment.getPatientId().equals(patientId)) {
                if (!uniqueDoctors.contains(treatment.getDoctorId())) {
                    uniqueDoctors.add(treatment.getDoctorId());
                }
            }
        }
        
        return uniqueDoctors.size();
    }
    
    private double calculateTotalCostForPatient(String patientId) {
        double totalCost = 0.0;
        
        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment treatment = treatmentList.get(i);
            if (treatment.getPatientId().equals(patientId)) {
                totalCost += treatment.getTreatmentCost();
            }
        }
        
        return totalCost;
    }
    
    private int countTreatmentsForDoctor(String doctorId) {
        int count = 0;
        
        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment treatment = treatmentList.get(i);
            if (treatment.getDoctorId().equals(doctorId)) {
                count++;
            }
        }
        
        return count;
    }
    
    private double calculateTotalRevenueForDoctor(String doctorId) {
        double totalRevenue = 0.0;
        
        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment treatment = treatmentList.get(i);
            if (treatment.getDoctorId().equals(doctorId)) {
                totalRevenue += treatment.getTreatmentCost();
            }
        }
        
        return totalRevenue;
    }

    private int countTreatmentsForPatient(String patientId) {
        int count = 0;
        
        for (int i = 0; i < treatmentList.size(); i++) {
            MedicalTreatment treatment = treatmentList.get(i);
            if (treatment.getPatientId().equals(patientId)) {
                count++;
            }
        }
        
        return count;
    }
}
