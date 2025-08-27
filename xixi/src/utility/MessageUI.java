/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

public final class MessageUI {

    public MessageUI() {
    }

    public static void showInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void showError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void showSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    // Removed instance message methods; use static helpers instead

    public static void showTitle(String title) {
        System.out.println("\n==== " + title.toUpperCase() + " ====");
    }

    // Compatibility with older calls
    public static void displayExitMessage() {
        System.out.println("Exiting System...");
    }

    public static void displayInvalidChoiceMessage() {
        System.out.println("Invalid choice. Please try again.");
    }
}
