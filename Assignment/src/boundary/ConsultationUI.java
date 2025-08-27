//Yu hang
package boundary;

import java.util.Scanner;

public class ConsultationUI {
    private final Scanner sc = new Scanner(System.in);

    public int mainMenu() {
        System.out.println("\n==============================");
        System.out.println("      Consultation Module     ");
        System.out.println("==============================");
        System.out.println("1. Book consultation");
        System.out.println("2. Reschedule consultation");
        System.out.println("3. Cancel consultation");
        System.out.println("4. Complete consultation");
        System.out.println("5. Search consultations");
        System.out.println("6. Report: Consultations per Doctor");
        System.out.println("7. Report: Utilization by Half-hour");
        System.out.println("8. View all consultations");
        System.out.println("0. Back/Exit");
        System.out.print("Enter choice > ");
        return nextIntLoop();
    }

    // Generic re-prompting inputs
    public int nextIntLoop() {
        while (true) {
            String line = sc.nextLine().trim();
            try { return Integer.parseInt(line); }
            catch (Exception e) { System.out.print("Invalid number. Enter again > "); }
        }
    }

    public String inputLine(String prompt, boolean allowEmpty) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            if (allowEmpty || (s != null && s.trim().length() > 0)) return s.trim();
            System.out.println("Input cannot be empty.");
        }
    }

    // Step prompts
    public int chooseFromList(String title, String[] lines) {
        System.out.println("\n-- " + title + " --");
        for (int i = 0; i < lines.length; i++) {
            System.out.printf("%2d) %s%n", i+1, lines[i]);
        }
        System.out.print("Choose (1-" + lines.length + ") > ");
        while (true) {
            int c = nextIntLoop();
            if (c >= 1 && c <= lines.length) return c;
            System.out.print("Out of range. Choose again > ");
        }
    }

    public int chooseDuration() {
        System.out.println("\nDuration:");
        System.out.println("1) 30 minutes");
        System.out.println("2) 90 minutes");
        System.out.print("Choose (1-2) > ");
        while (true) {
            int c = nextIntLoop();
            if (c == 1 || c == 2) return c;
            System.out.print("Invalid. Choose 1 or 2 > ");
        }
    }
}
