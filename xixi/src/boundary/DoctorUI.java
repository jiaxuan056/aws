//WONG HWAI EARN
package boundary;

import java.util.Scanner;

public class DoctorUI {
    private Scanner sc = new Scanner(System.in);

    public int getMainMenuChoice() {
        int choice = -1;
        while (true) {
            System.out.println("\n====================");
            System.out.println("   Doctor System    ");
            System.out.println("====================");
            System.out.println("1. Register Doctor");
            System.out.println("2. Doctor Management");
            System.out.println("3. Availability & Appointment");
            System.out.println("4. Generate Report");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            String input = sc.nextLine();

            try {
                choice = Integer.parseInt(input);
                if (choice >= 0 && choice <= 4) break;
                else System.out.println("Please enter a number between 0 and 4.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return choice;
    }

    public int getDoctorManagementChoice() {
    int choice = -1;
    while (true) {
        System.out.println("\n========================");
        System.out.println("   Doctor Management    ");
        System.out.println("========================");
        System.out.println("1. View All Doctors");
        System.out.println("2. Display Doctors by Specialization");
        System.out.println("3. Search Doctor");
        System.out.println("4. Update Doctor");
        System.out.println("5. Remove Doctor");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter choice: ");
        String input = sc.nextLine();

        try {
            choice = Integer.parseInt(input);
            if (choice >= 0 && choice <= 5) break;   
            else System.out.println("Please enter a number between 0 and 5.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    return choice;
}


    public int getSpecializationChoice() {
    int choice = -1;
    while (true) {
        System.out.println("\n--- Display Doctors by Specialization ---");
        System.out.println("1. Cardiology");
        System.out.println("2. Neurology");
        System.out.println("3. Pediatrics");
        System.out.println("4. Orthopedics");
        System.out.println("5. Dermatology");
        System.out.print("Choose specialization (1-5): ");

        try {
            choice = Integer.parseInt(sc.nextLine());
            if (choice >= 1 && choice <= 5) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1–5.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    return choice;
}

    
    public int getAvailabilityChoice() {
        int choice = -1;
        while (true) {
            System.out.println("\n==============================");
            System.out.println("   Availability & Appointment ");
            System.out.println("==============================");
            System.out.println("1. Check Availability");
            System.out.println("2. Book Appointment");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            String input = sc.nextLine();

            try {
                choice = Integer.parseInt(input);
                if (choice >= 0 && choice <= 2) break;
                else System.out.println("Please enter a number between 0 and 2.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return choice;
    }

    public int getReportsMenuChoice() {
        int choice = -1;
        while (true) {
            System.out.println("\n=================");
            System.out.println("   Reports Menu  ");
            System.out.println("=================");
            System.out.println("1. Number of Doctor Availability by Day");
            System.out.println("2. Doctor Count by Specialization");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            String input = sc.nextLine();

            try {
                choice = Integer.parseInt(input);
                if (choice >= 0 && choice <= 2) break;
                else System.out.println("Please enter a number between 0 and 2.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return choice;
    }

    // ===== Inputs =====
    public String inputDoctorName() {
        System.out.print("Enter doctor name: ");
        return sc.nextLine();
    }

    public String inputDoctorSpecialization() {
    while (true) {
        System.out.println("Select specialization:");
        System.out.println("1. Cardiology");
        System.out.println("2. Dermatology");
        System.out.println("3. Pediatrics");
        System.out.println("4. Orthopedics");
        System.out.println("5. Neurology");
        System.out.print("Enter choice: ");

        if (sc.hasNextInt()) {
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1: return "Cardiology";
                case 2: return "Dermatology";
                case 3: return "Pediatrics";
                case 4: return "Orthopedics";
                case 5: return "Neurology";
                default:
                    System.out.println("Invalid choice. Please enter 1–5.\n");
            }
        } else {
            System.out.println("Invalid input. Please enter a number (1–5).\n");
            sc.nextLine();
        }
    }
}


    public int getDoctorUpdateChoice() {
        int choice = -1;
        while (true) {
            System.out.println("\n--- Update Doctor ---");
            System.out.println("1. Update Name");
            System.out.println("2. Update Specialization");
            System.out.println("3. Update Duty Schedule");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            String input = sc.nextLine();

            try {
                choice = Integer.parseInt(input);
                if (choice >= 0 && choice <= 3) break;
                else System.out.println("Please enter a number between 0 and 3.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return choice;
    }
    
    public String inputDoctorDutyDays() {
    String dutyDays = ""; 
    boolean choosing = true;

    while (choosing) {
        System.out.println("\nSelect duty days (0 to Finish):");
        System.out.println("1. Mon" + (dutyDays.contains("Mon") ? " (selected)" : ""));
        System.out.println("2. Tue" + (dutyDays.contains("Tue") ? " (selected)" : ""));
        System.out.println("3. Wed" + (dutyDays.contains("Wed") ? " (selected)" : ""));
        System.out.println("4. Thu" + (dutyDays.contains("Thu") ? " (selected)" : ""));
        System.out.println("5. Fri" + (dutyDays.contains("Fri") ? " (selected)" : ""));
        System.out.println("6. Sat" + (dutyDays.contains("Sat") ? " (selected)" : ""));
        System.out.println("7. Sun" + (dutyDays.contains("Sun") ? " (selected)" : ""));
        System.out.print("Enter choice: ");

        if (!sc.hasNextInt()) {
            sc.nextLine(); // clear invalid input
            System.out.println("Invalid input. Please enter a number between 0 and 7.");
            continue;
        }

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) {
            if (dutyDays.isEmpty()) {
                System.out.println("You must select at least one day.");
                continue;
            }
            choosing = false;
            break;
        }

        String chosenDay = "";
        switch (choice) {
            case 1: chosenDay = "Mon"; break;
            case 2: chosenDay = "Tue"; break;
            case 3: chosenDay = "Wed"; break;
            case 4: chosenDay = "Thu"; break;
            case 5: chosenDay = "Fri"; break;
            case 6: chosenDay = "Sat"; break;
            case 7: chosenDay = "Sun"; break;
            default: 
                System.out.println("Invalid choice. Please enter 0–7.");
                continue;
        }

        if (dutyDays.contains(chosenDay)) {
            // remove
            dutyDays = dutyDays.replace("," + chosenDay, "");
            dutyDays = dutyDays.replace(chosenDay + ",", "");
            dutyDays = dutyDays.replace(chosenDay, "");
            System.out.println(chosenDay + " removed.");
        } else {
            // add
            if (!dutyDays.isEmpty()) dutyDays += ",";
            dutyDays += chosenDay;
            System.out.println(chosenDay + " added.");
        }
    }

    String finalDays = "";
    if (dutyDays.contains("Mon")) finalDays += (finalDays.isEmpty() ? "" : ",") + "Mon";
    if (dutyDays.contains("Tue")) finalDays += (finalDays.isEmpty() ? "" : ",") + "Tue";
    if (dutyDays.contains("Wed")) finalDays += (finalDays.isEmpty() ? "" : ",") + "Wed";
    if (dutyDays.contains("Thu")) finalDays += (finalDays.isEmpty() ? "" : ",") + "Thu";
    if (dutyDays.contains("Fri")) finalDays += (finalDays.isEmpty() ? "" : ",") + "Fri";
    if (dutyDays.contains("Sat")) finalDays += (finalDays.isEmpty() ? "" : ",") + "Sat";
    if (dutyDays.contains("Sun")) finalDays += (finalDays.isEmpty() ? "" : ",") + "Sun";

    return finalDays;
}




    public String inputDoctorIdToSearch() {
        System.out.print("Enter doctor ID to search: ");
        return sc.nextLine();
    }

    public String inputDoctorIdToRemove() {
        System.out.print("Enter doctor ID to remove: ");
        return sc.nextLine();
    }

    public String inputDoctorIdForAppointment() {
        System.out.print("Enter doctor ID for appointment: ");
        return sc.nextLine();
    }

    public String inputAppointmentDay() {
        System.out.print("Enter appointment day (e.g. Mon): ");
        return sc.nextLine();
    }

    public int inputAppointmentHour() {
        System.out.print("Enter appointment start hour (24h format, 8–21): ");
        int hour = sc.nextInt();
        sc.nextLine();
        return hour;
    }
    
    public int inputAppointmentStartHour() {
    int hour = -1;
    do {
        try {
            System.out.print("Enter appointment start hour (8–21): ");
            hour = Integer.parseInt(sc.nextLine());
            if (hour < 8 || hour > 21) {
                System.out.println("Invalid start hour. Please enter between 8 and 21.");
                hour = -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    } while (hour == -1);
    return hour;
}
    
    public int inputAppointmentEndHour() {
    int hour = -1;
    do {
        try {
            System.out.print("Enter appointment end hour (9–22): ");
            hour = Integer.parseInt(sc.nextLine());
            if (hour < 9 || hour > 22) {
                System.out.println("Invalid end hour. Please enter between 9 and 22.");
                hour = -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    } while (hour == -1);
    return hour;
}
    
    public int inputAppointmentStartMinute() {
    int minute;
    while (true) {
        System.out.print("Enter start minute (0 or 30): ");
        try {
            minute = Integer.parseInt(sc.nextLine());
            if (minute == 0 || minute == 30) {
                return minute;
            } else {
                System.out.println("Invalid input. Only 0 or 30 are allowed.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
        }
    }
}
    
    public int inputAppointmentDuration() {
    int duration;
    while (true) {
        System.out.print("Enter appointment duration (30 or 90 minutes): ");
        try {
            duration = Integer.parseInt(sc.nextLine());
            if (duration == 30 || duration == 90) {
                return duration;
            } else {
                System.out.println("Invalid input. Only 30 or 90 are allowed.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
        }
    }
}
}
