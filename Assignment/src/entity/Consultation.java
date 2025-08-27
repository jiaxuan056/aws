// yuhang
package entity;

public class Consultation implements Comparable<Consultation> {

    private static int RUNNING = 2001;

    private final String consultationID; // e.g., C2001
    private String doctorId;
    private String doctorName;
    private String patientId;
    private String patientName;
    private String day;            // Monday..Sunday
    private int startHour;         // 8 - 21
    private int startMinute;       // 0 or 30
    private int durationMinutes;   // 30 or 90
    private String status;         // BOOKED | COMPLETED | CANCELLED
    private String notes;

    public Consultation(String doctorId, String doctorName,
            String patientId, String patientName,
            String day, int startHour, int startMinute, int durationMinutes,
            String notes) {
        this.consultationID = "C" + (RUNNING++);
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.patientId = patientId;
        this.patientName = patientName;
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.durationMinutes = durationMinutes;
        this.status = "BOOKED";
        this.notes = notes == null ? "" : notes;
    }

    // Getters & setters
    public String getConsultationID() {
        return consultationID;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSlotKey() { // used to mirror in Doctor.bookedSlots
        return String.format("%s-%02d:%02d+%d", day, startHour, startMinute, durationMinutes);
    }

    public String timeString() {
        return String.format("%02d:%02d (%d min)", startHour, startMinute, durationMinutes);
    }

    @Override
    public String toString() {
        return String.format("Consultation[ID=%s, Doctor=%s(%s), Patient=%s(%s), %s %s, %s]",
                consultationID, doctorName, doctorId, patientName, patientId, day, timeString(), status);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Consultation)) {
            return false;
        }
        return consultationID.equals(((Consultation) o).consultationID);
    }

    @Override
    public int compareTo(Consultation other) {
        if (other == null) {
            return 1;
        }
        if (this.consultationID == null && other.consultationID == null) {
            return 0;
        }
        if (this.consultationID == null) {
            return -1;
        }
        if (other.consultationID == null) {
            return 1;
        }
        return this.consultationID.compareToIgnoreCase(other.consultationID);
    }
}
