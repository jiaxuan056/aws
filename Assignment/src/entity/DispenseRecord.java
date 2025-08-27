// Ric Hang
package entity;

import java.time.LocalDate;

public class DispenseRecord implements Comparable<DispenseRecord> {
    private static int ID = 6001;

    private final String dispenseId;
    private final String treatmentId;
    private final String medicineId;
    private final int quantity;
    private final LocalDate dispenseDate;
    private final double totalCost;

    public DispenseRecord(String treatmentId, String medicineId, int quantity, LocalDate dispenseDate, double unitPrice) {
        this.dispenseId = "DS" + ID++;
        this.treatmentId = treatmentId;
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.dispenseDate = dispenseDate;
        this.totalCost = unitPrice * quantity;
    }

    public String getDispenseId() { return dispenseId; }
    public String getTreatmentId() { return treatmentId; }
    public String getMedicineId() { return medicineId; }
    public int getQuantity() { return quantity; }
    public LocalDate getDispenseDate() { return dispenseDate; }
    public double getTotalCost() { return totalCost; }

    @Override
    public int compareTo(DispenseRecord other) {
        // Most recent first
        return other.dispenseDate.compareTo(this.dispenseDate);
    }

    @Override
    public String toString() {
        return dispenseId + " | Treatment: " + treatmentId + " | Medicine: " + medicineId
                + " | Qty: " + quantity + " | Date: " + dispenseDate + " | Total: RM" + String.format("%.2f", totalCost);
    }
}


