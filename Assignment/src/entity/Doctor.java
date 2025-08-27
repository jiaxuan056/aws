package entity;

public class Doctor implements Comparable<Doctor> {
    private static int ID = 1001;
    private String doctorID;
    private String name;
    private String specialization;
    private String dutyDays; // Example: "Monday,Wednesday,Friday"
    private String bookedSlots; // Example: "Monday-09,Monday-10,Tuesday-14"

    public Doctor(String name, String specialization, String dutyDays) {
        this.doctorID = "D" + ID++;
        this.name = name;
        this.specialization = specialization;
        this.dutyDays = dutyDays;
        this.bookedSlots = ""; // no slots booked initially
    }

    // --- Getters & Setters ---
    public String getId() {
        return doctorID;
    }

    public void setId(String id) {
        this.doctorID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDutyDays() {
        return dutyDays;
    }

    public void setDutyDays(String dutyDays) {
        this.dutyDays = dutyDays;
    }

    public String getBookedSlots() {
        return bookedSlots;
    }

    public void setBookedSlots(String bookedSlots) {
        this.bookedSlots = bookedSlots;
    }

    @Override
    public String toString() {
        return String.format("Doctor[ID=%s, Name=%s, Spec=%s, DutyDays=%s, BookedSlots=%s]",
                doctorID, name, specialization, dutyDays, bookedSlots);
    }

    @Override

public int compareTo(Doctor other) {
    // natural order by doctorID; change if you prefer name or specialization
    return this.doctorID.compareTo(other.doctorID);
}

}
