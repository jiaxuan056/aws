//Ric Hang
package entity;

public class Medicine implements Comparable<Medicine> {
    private static int ID = 5001;

    private final String medicineId;
    private String name;
    private String dosageForm; // e.g., Tablet, Syrup, Injection
    private double unitPrice;
    private int stockQuantity;
    private int reorderLevel;

    public Medicine(String name, String dosageForm, double unitPrice, int stockQuantity, int reorderLevel) {
        this.medicineId = "M" + ID++;
        this.name = name;
        this.dosageForm = dosageForm;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
        this.reorderLevel = reorderLevel;
    }

    // Getters
    public String getMedicineId() { return medicineId; }
    public String getName() { return name; }
    public String getDosageForm() { return dosageForm; }
    public double getUnitPrice() { return unitPrice; }
    public int getStockQuantity() { return stockQuantity; }
    public int getReorderLevel() { return reorderLevel; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDosageForm(String dosageForm) { this.dosageForm = dosageForm; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }

    public boolean reduceStock(int qty) {
        if (qty <= 0 || qty > stockQuantity) return false;
        stockQuantity -= qty;
        return true;
    }

    public void addStock(int qty) { if (qty > 0) stockQuantity += qty; }

    public boolean isLowStock() { return stockQuantity <= reorderLevel; }

    @Override
    public int compareTo(Medicine other) { return this.name.compareToIgnoreCase(other.name); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine m = (Medicine) o;
        return medicineId.equals(m.medicineId);
    }

    @Override
    public String toString() {
        return medicineId + " - " + name + " (" + dosageForm + ") | RM" + String.format("%.2f", unitPrice)
                + " | Stock: " + stockQuantity + " | Reorder: " + reorderLevel;
    }
}


