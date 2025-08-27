//Chau Jia Xuan
package control;

import java.util.Scanner;
import java.util.Comparator;
import boundary.PatientManagementUI;
import utility.MessageUI;
import entity.Patient;
import adt.ListInterface;
import dao.ClinicInitializer;
import adt.ArrayList;

public class PatientManagement {
    private Scanner sc = new Scanner(System.in);
    private PatientManagementUI patientUI = new PatientManagementUI();
    private ListInterface<Patient> patientList = new ArrayList<>();
    private ListInterface<Patient> patientQueue = new ArrayList<>();

    public PatientManagement() {
        patientList = ClinicInitializer.initializePatients();
    }
    
    public void displayMenu() {
        int mainChoice;
        int count = 0;
        int patientManagementChoice;
        int PatientQueueChoice;
        int ReportsMenuChoice;
        
        do {
            if(count  == 1){
                break;
            }
            mainChoice = patientUI.getMainMenuChoice();
            switch (mainChoice) {
                case 1:
                    registerPatient();
                    break;
                case 2:
                    do{
                        patientManagementChoice = patientUI.getPatientManagementChoice();
                        switch (patientManagementChoice){
                            case 1:
                                viewAllPatients();
                                break;
                            case 2:
                                searchPatient();
                                break;
                            case 3:
                                filterPatient();
                                break;
                            case 4:
                                updatePatient();
                                break;
                            case 5:
                                removePatient();
                                break;
                            case 0:
                                count = 1;
                                displayMenu();
                                break;
                            default:
                                MessageUI.displayInvalidChoiceMessage();
                        }
                    }while(patientManagementChoice != 0);
                    break;
                case 3:
                    do{
                        count = 0;
                        PatientQueueChoice = patientUI.getPatientQueueChoice();
                        switch (PatientQueueChoice){
                            case 1:
                                addPatientToQueue();
                                break;
                            case 2:
                                viewQueue();
                                break;
                            case 3:
                                serveNextPatient();
                                break;
                            case 0:
                                count = 1;
                                displayMenu();
                                break;
                            default:
                                MessageUI.displayInvalidChoiceMessage();
                        }
                    }while(PatientQueueChoice != 0);
                    break;
                    case 4:
                    do{
                        count = 0;
                        ReportsMenuChoice = patientUI.getReportsMenuChoice();
                        switch (ReportsMenuChoice){
                            case 1:
                                generatePatientListReport();
                                break;
                            case 2:
                                generateSummaryReport();
                                break;
                            case 0:
                                count = 1;
                                displayMenu();
                                break;
                            default:
                                MessageUI.displayInvalidChoiceMessage();
                        }
                    }while(ReportsMenuChoice != 0);
                    break;
                    case 0:
                    MessageUI.displayExitMessage();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (mainChoice != 0);
    }

    int filterChoice;
    public void filterPatient() {
        if (patientList.isEmpty()) {
            System.out.println("\nNo patients available.");
            return;
        }
        do{
        System.out.println("\n--- Filter Options ---");
        System.out.println("1. Filter by Gender");
        System.out.println("2. Filter by Age Range");
        System.out.println("3. Filter by Sickness");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
        filterChoice = sc.nextInt();
        sc.nextLine();

        boolean found = false;

        switch (filterChoice) {
            case 1:
                System.out.print("Enter gender (M/F): ");
                String gender = sc.nextLine().toUpperCase();
                for (int i = 0; i < patientList.size(); i++) {
                    Patient p = patientList.get(i);
                    if (p.getGender().equalsIgnoreCase(gender)) {
                        System.out.println(p);
                        found = true;
                    }
                }
                break;

            case 2:
                System.out.print("Enter minimum age: ");
                int minAge = sc.nextInt();
                System.out.print("Enter maximum age: ");
                int maxAge = sc.nextInt();
                sc.nextLine();
                for (int i = 0; i < patientList.size(); i++) {
                    Patient p = patientList.get(i);
                    if (p.getAge() >= minAge && p.getAge() <= maxAge) {
                        System.out.println(p);
                        found = true;
                    }
                }
                break;

            case 3:
                System.out.print("Enter sickness keyword: ");
                String sickness = sc.nextLine().toLowerCase();
                for (int i = 0; i < patientList.size(); i++) {
                    Patient p = patientList.get(i);
                    if (p.getSickness().toLowerCase().contains(sickness)) {
                        System.out.println(p);
                        found = true;
                    }
                }
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        if (!found) {
            System.out.println("No patients found with the given filter.");
        }
        }while(filterChoice != 0);
    }
    
    public void registerPatient() {
        String name;
        do {
            name = patientUI.inputPatientName();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please try again.");
            } else if (!name.matches("[a-zA-Z]+")) {
                System.out.println("Name must only contain alphabetic letters. Please try again.");
            }
        } while (name.isEmpty() || !name.matches("[a-zA-Z]+"));


        String gender;
        do {
            gender = patientUI.inputPatientGender().toUpperCase();
            if (!gender.equals("M") && !gender.equals("F")) {
                System.out.println("Gender must be M or F. Please try again.");
            }
        } while (!gender.equals("M") && !gender.equals("F"));

        int age = 0;
        boolean valid = false;

        while (!valid) {
            String input = patientUI.inputPatientAge();
            if (input.matches("\\d+")) {
                age = Integer.parseInt(input);
                if (age > 0) {
                    valid = true;
                } else {
                    System.out.println("Age must be greater than 0.");
                }
            } else {
                System.out.println("Only integer values are allowed.");
            }
        }


        String contact;
        do {
            contact = patientUI.inputPatientContact();
            if (!contact.matches("\\d{10,12}")) {
                System.out.println("Contact must be 10-12 digits.");
            }
        } while (!contact.matches("\\d{10,12}"));

        String sickness;
        do {
            sickness = patientUI.inputPatientSickness();
            if (sickness.isEmpty()) {
                System.out.println("Sickness cannot be empty. Please try again.");
            }
        } while (sickness.isEmpty());
        
        
        Patient newPatient = new Patient(name, gender, age, contact, sickness);
        patientList.add(newPatient);

        System.out.println("Patient registered successfully!");
    }

    public void viewAllPatients() {
        if (patientList.isEmpty()) {
            System.out.println("No patients registered.");
        } else {
            System.out.println("\n=== Patient List ===");
            patientList.sort(new Comparator<Patient>() {
                public int compare(Patient a, Patient b) {
                    return a.compareTo(b);
                }
            });
            for (int i = 0; i < patientList.size(); i++) {
                System.out.println(patientList.get(i));
            }
        }
    }
    
    public void removePatient() {
        if (patientList.isEmpty()) {
            System.out.println("Patient List empty, No patient to remove.");
            return;
        }else{
            viewAllPatients();
        }

        String nameToRemove = patientUI.inputNameToRemove();
        boolean removed = false;

        for (int i = 0; i < patientList.size(); i++) {
            Patient p = patientList.get(i);
            if (p.getName().equals(nameToRemove)) {
                patientList.remove(p);
                System.out.println("Patient \"" + p.getName() + "\" removed successfully.");
                removed = true;
                break;
            }
        }

        if (!removed) {
            System.out.println("No patient found with the name \"" + nameToRemove + "\".");
        }
    }

    
    public void searchPatient() {
        if (patientList.isEmpty()) {
            System.out.println("Patient list empty, no patient to search.");
            return;
        }

        String keyword = patientUI.inputNameToSearch();
        boolean found = false;

        System.out.println("\n=== Search Results ===");
        for (int i = 0; i < patientList.size(); i++) {
            Patient p = patientList.get(i);
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(p);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No patient found matching \"" + keyword + "\".");
        }
    }

    
    public void updatePatient() {
    if (patientList.isEmpty()) {
        System.out.println("No patients to update.");
        return;
    }

    viewAllPatients();
    String nameToUpdate = patientUI.inputNameToSearch();
    boolean updated = false;

    for (int i = 0; i < patientList.size(); i++) {
        Patient p = patientList.get(i);
            if (p.getName().equalsIgnoreCase(nameToUpdate)) {
                patientList.remove(p);

                System.out.println("Updating patient: " + p.getName());

                String newName = patientUI.inputPatientName();
                String newGender = patientUI.inputPatientGender();
                int newAge = Integer.parseInt(patientUI.inputPatientAge());
                String newContact = patientUI.inputPatientContact();
                String newSickness = patientUI.inputPatientSickness();
                
                Patient updatedPatient = new Patient(newName, newGender, newAge, newContact, newSickness);
                patientList.add(updatedPatient);

                System.out.println("Patient updated successfully!");
                updated = true;
                break;
            }
        }
        if (!updated) {
            System.out.println("No patient found with that name.");
        }
    }

    public void addPatientToQueue() {
        if (patientList.isEmpty()) {
            System.out.println("No registered patients to add to the queue.");
            return;
        }

        viewAllPatients();
        String patientName = patientUI.inputPatientName();
        boolean found = false;

        for (int i = 0; i < patientList.size(); i++) {
            Patient p = patientList.get(i);
            if (p.getName().equals(patientName)) {
                patientQueue.add(p);
                System.out.println("Patient " + p.getName() + " added to queue.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No patient found with the name \"" + patientName + "\".");
        }
    }
     
     public void viewQueue() {
        if (patientQueue.isEmpty()) {
            System.out.println("The queue is empty.");
            return;
        }

        System.out.println("\n=== Current Queue ===");
        for (int i = 0; i < patientQueue.size(); i++) {
            System.out.println((i + 1) + ". " + patientQueue.get(i).getName());
        }
    }

    public void serveNextPatient() {
        if (patientQueue.isEmpty()) {
            System.out.println("The queue is empty.");
            return;
        }
        Patient next = patientQueue.get(0);
        patientQueue.remove(next);
        System.out.println("Serving patient: " + next.getName());
    }

    public void generatePatientListReport() {
        System.out.println("====================================");
        System.out.println("         Patient List Report        ");
        System.out.println("====================================");

        if (patientList.isEmpty()) {
            System.out.println("No patients registered.");
        } else {
            System.out.printf("%-5s %-20s %-10s %-5s %-15s %-20s%n",
                    "ID", "Name", "Gender", "Age", "Contact", "Sickness");
            System.out.println("----------------------------------------------------------------------------");

            for (int i = 0; i < patientList.size(); i++) {
                Patient p = patientList.get(i);
                System.out.printf("%-5s %-20s %-10s %-5d %-15s %-20s%n",
                        p.getPatientId(),
                        p.getName(),
                        p.getGender(),
                        p.getAge(),
                        p.getContact(),
                        p.getSickness()
                );
            }
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("Total Patients: " + patientList.size());
        }
        System.out.println("====================================");
    }


    public void generateSummaryReport() {
        System.out.println("\n=== Monthly Summary Report ===");

        if (patientList.isEmpty()) {
            System.out.println("No patients recorded this month.");
            return;
        }

        int total = patientList.size();
        int maleCount = 0, femaleCount = 0, totalAge = 0;

        java.util.Map<String, Integer> sicknessCount = new java.util.HashMap<>();

        for (int i = 0; i < total; i++) {
            Patient p = patientList.get(i);

            if (p.getGender().equalsIgnoreCase("M")) maleCount++;
            else if (p.getGender().equalsIgnoreCase("F")) femaleCount++;

            totalAge += p.getAge();
            sicknessCount.put(p.getSickness(),
                    sicknessCount.getOrDefault(p.getSickness(), 0) + 1);
        }

        String commonSickness = "N/A";
        int maxCount = 0;
        for (var entry : sicknessCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                commonSickness = entry.getKey();
            }
        }

        double avgAge = (double) totalAge / total;

        System.out.println("Total Patients   : " + total);
        System.out.println("Male Patients    : " + maleCount);
        System.out.println("Female Patients  : " + femaleCount);
        System.out.printf("Average Age      : %.1f%n", avgAge);
        System.out.println("Most Common Illness: " + commonSickness);
    }

    
    public static void main(String [] args){
        PatientManagement patientManagement = new PatientManagement();
        patientManagement.displayMenu();
    }
}
