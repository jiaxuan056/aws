// Chau Jia Xuan
package boundary;

import java.util.Scanner;

public class PatientManagementUI {
    private Scanner sc = new Scanner(System.in);

    public int getMainMenuChoice() {
        System.out.println("\n====================");
        System.out.println("   Patient System   ");
        System.out.println("====================");
        System.out.println("1. Register Patient");
        System.out.println("2. Patient Management");
        System.out.println("3. Patient Queue");
        System.out.println("4. Generate Report");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }
    
    public int getPatientManagementChoice(){
        System.out.println("\n========================");
        System.out.println("   Patient Management   ");
        System.out.println("========================");
        System.out.println("1. View All Patients");
        System.out.println("2. Search Patient");
        System.out.println("3. Filter Patients");
        System.out.println("4. Update Patient");
        System.out.println("5. Remove Patient");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }
    
    public int getPatientQueueChoice() {
        System.out.println("\n=================");
        System.out.println("   Patient Queue ");
        System.out.println("=================");
        System.out.println("1. Add Patient to Queue");
        System.out.println("2. View Queue");
        System.out.println("3. Serve Next Patient");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }
    
    public int getFilterChoice() {
        System.out.println("\n=================");
        System.out.println("   Filter Menu   ");
        System.out.println("=================");
        System.out.println("1. By Gender");
        System.out.println("2. By Age Range");
        System.out.println("3. By Sickness");
        System.out.println("0. Back to Patient Management");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    public int getReportsMenuChoice() {
        System.out.println("\n=================");
        System.out.println("   Reports Menu  ");
        System.out.println("=================");
        System.out.println("1. Patient List Report");
        System.out.println("2. Monthly Summary Report");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    public String inputPatientName() {
        System.out.print("Enter patient name: ");
        return sc.nextLine();
    }

    public String inputPatientGender() {
        System.out.print("Enter patient gender (M/F): ");
        return sc.nextLine();
    }

    public String inputPatientAge() {
        System.out.print("Enter patient age: ");
        return sc.nextLine();
    }

    public String inputPatientContact() {
        System.out.print("Enter patient contact: ");
        return sc.nextLine();
    }
    
    public String inputPatientSickness() {
        System.out.print("Enter patient sickness: ");
        return sc.nextLine();
    }
    
    public String inputNameToRemove(){
        System.out.print("Enter patient name to remove: ");
        return sc.nextLine();
    }
    public String inputFilterGender() {
        System.out.print("Enter gender (M/F): ");
        return sc.nextLine();
    }

    public int inputMinAge() {
        System.out.print("Enter minimum age: ");
        return sc.nextInt();
    }

    public int inputMaxAge() {
        System.out.print("Enter maximum age: ");
        return sc.nextInt();
    }

    public String inputFilterSickness() {
        System.out.print("Enter sickness keyword: ");
        return sc.nextLine();
    }

    public String inputNameToSearch(){
        System.out.print("Enter patient name to search: ");
        return sc.nextLine();
    }
}
