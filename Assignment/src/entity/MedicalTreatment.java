//Wai Kin
package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MedicalTreatment implements Comparable<MedicalTreatment> {

    private static int ID = 3001;
    private final String treatmentId;
    private final String patientId;
    private final String doctorId;
    private final String consultationId; // Link to consultation
    private String diagnosis;
    private String symptoms;
    private String medicalCondition;
    private String treatmentPlan;
    private String prescribedMedication;
    private final LocalDate treatmentDate;
    private LocalDate treatmentEndDate;
    private String treatmentStatus; // "Active", "Completed", "Discontinued"
    private String followUpNotes;
    private LocalDate followUpDate;
    private double treatmentCost;

    public MedicalTreatment(String patientId, String doctorId, String consultationId, String diagnosis,
            String symptoms, String medicalCondition, String treatmentPlan,
            String prescribedMedication, LocalDate treatmentDate,
            LocalDate treatmentEndDate, double treatmentCost) {
        this.treatmentId = "T" + ID++;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.consultationId = consultationId;
        this.diagnosis = diagnosis;
        this.symptoms = symptoms;
        this.medicalCondition = medicalCondition;
        this.treatmentPlan = treatmentPlan;
        this.prescribedMedication = prescribedMedication;
        this.treatmentDate = treatmentDate;
        this.treatmentEndDate = treatmentEndDate;
        this.treatmentStatus = "Active";
        this.followUpNotes = "";
        this.followUpDate = null;
        this.treatmentCost = treatmentCost;
    }

    // Getters
    public String getTreatmentId() {
        return treatmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public String getPrescribedMedication() {
        return prescribedMedication;
    }

    public LocalDate getTreatmentDate() {
        return treatmentDate;
    }

    public LocalDate getTreatmentEndDate() {
        return treatmentEndDate;
    }

    public String getTreatmentStatus() {
        return treatmentStatus;
    }

    public String getFollowUpNotes() {
        return followUpNotes;
    }

    public LocalDate getFollowUpDate() {
        return followUpDate;
    }

    public double getTreatmentCost() {
        return treatmentCost;
    }

    // Setters
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public void setPrescribedMedication(String prescribedMedication) {
        this.prescribedMedication = prescribedMedication;
    }

    public void setTreatmentStatus(String treatmentStatus) {
        this.treatmentStatus = treatmentStatus;
    }

    public void setFollowUpNotes(String followUpNotes) {
        this.followUpNotes = followUpNotes;
    }

    public void setFollowUpDate(LocalDate followUpDate) {
        this.followUpDate = followUpDate;
    }

    public void setTreatmentCost(double treatmentCost) {
        this.treatmentCost = treatmentCost;
    }

    public void setTreatmentEndDate(LocalDate treatmentEndDate) {
        this.treatmentEndDate = treatmentEndDate;
    }

    // Business methods
    public void completeTreatment() {
        this.treatmentStatus = "Completed";
    }

    public void discontinueTreatment(String reason) {
        this.treatmentStatus = "Discontinued";
        this.followUpNotes = "Discontinued: " + reason;
    }

    public void scheduleFollowUp(LocalDate followUpDate, String notes) {
        this.followUpDate = followUpDate;
        this.followUpNotes = notes;
    }

    public boolean isActive() {
        return "Active".equals(treatmentStatus);
    }

    public boolean isCompleted() {
        return "Completed".equals(treatmentStatus);
    }

    public boolean isDiscontinued() {
        return "Discontinued".equals(treatmentStatus);
    }

    public boolean isTreatmentCompleted() {
        return treatmentEndDate != null && LocalDate.now().isAfter(treatmentEndDate);
    }

    @Override
    public int compareTo(MedicalTreatment other) {
        // Sort by treatment date (most recent first)
        return other.treatmentDate.compareTo(this.treatmentDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MedicalTreatment treatment = (MedicalTreatment) obj;
        return treatmentId.equals(treatment.treatmentId);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String endDateInfo = (treatmentEndDate != null)
                ? " | End: " + treatmentEndDate.format(formatter) : "";
        String followUpInfo = (followUpDate != null)
                ? " | Follow-up: " + followUpDate.format(formatter) : "";

        return treatmentId + " - Patient: " + patientId + " | Doctor: " + doctorId
                + " | Diagnosis: " + diagnosis + " | Status: " + treatmentStatus
                + " | Date: " + treatmentDate.format(formatter) + endDateInfo
                + " | Cost: RM" + String.format("%.2f", treatmentCost) + followUpInfo;
    }
}
