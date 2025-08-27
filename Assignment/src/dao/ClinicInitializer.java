//Wai Kin
package dao;

import entity.Patient;
import entity.Doctor;
import entity.MedicalTreatment;
import entity.Medicine;
import entity.DispenseRecord;
import entity.Consultation;
import adt.ListInterface;
import adt.SortedArrayList;
import java.time.LocalDate;
// import java.time.LocalDateTime;

public class ClinicInitializer {
    // Cached lists so additions persist across modules/instances
    private static ListInterface<Patient> PATIENTS;
    private static ListInterface<Doctor> DOCTORS;
    private static ListInterface<MedicalTreatment> TREATMENTS;
    private static ListInterface<Medicine> MEDICINES;
    private static ListInterface<DispenseRecord> DISPENSES;
    private static ListInterface<Consultation> CONSULTATIONS;
    
    public static ListInterface<Patient> initializePatients() {
        if (PATIENTS == null) {
            PATIENTS = new SortedArrayList<>();
        // Add sample patients
            PATIENTS.add(new Patient("John Smith", "M", 35, "0123456789", "Fever"));
            PATIENTS.add(new Patient("Mary Johnson", "F", 28, "0123456790", "Headache"));
            PATIENTS.add(new Patient("David Brown", "M", 45, "0123456791", "Cough"));
            PATIENTS.add(new Patient("Sarah Wilson", "F", 32, "0123456792", "Back Pain"));
            PATIENTS.add(new Patient("Michael Davis", "M", 52, "0123456793", "Diabetes"));
            PATIENTS.add(new Patient("Emily Taylor", "F", 25, "0123456794", "Allergy"));
            PATIENTS.add(new Patient("Robert Anderson", "M", 38, "0123456795", "Hypertension"));
            PATIENTS.add(new Patient("Lisa Martinez", "F", 41, "0123456796", "Asthma"));
            PATIENTS.add(new Patient("James Thompson", "M", 29, "0123456797", "Migraine"));
            PATIENTS.add(new Patient("Jennifer Garcia", "F", 36, "0123456798", "Arthritis"));
        }
        return PATIENTS;
    }
    
    public static ListInterface<Doctor> initializeDoctors() {
        if (DOCTORS == null) {
            DOCTORS = new SortedArrayList<>();
            DOCTORS.add(new Doctor("Dr. Alice Tan", "Cardiology", "Mon,Tue,Wed,Thu,Fri"));
            DOCTORS.add(new Doctor("Dr. Benjamin Lee", "Orthopedics", "Tue,Wed,Thu,Fri,Sat"));
            DOCTORS.add(new Doctor("Dr. Clara Wong", "Dermatology", "Mon,Tue,Wed,Thu"));
            DOCTORS.add(new Doctor("Dr. Daniel Lim", "Pediatrics", "Wed,Thu,Fri,Sat,Sun"));
            DOCTORS.add(new Doctor("Dr. Emily Chua", "Neurology", "Mond,Tue,Wed,Thu,Fri"));
        }
        return DOCTORS;
    }
    
    public static ListInterface<MedicalTreatment> initializeMedicalTreatments() {
        if (TREATMENTS == null) {
            TREATMENTS = new SortedArrayList<>();
            // Ensure patients and doctors are initialized
        ListInterface<Patient> patientList = initializePatients();
        ListInterface<Doctor> doctorList = initializeDoctors();
        
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate twoDaysAgo = today.minusDays(2);
            LocalDate threeDaysAgo = today.minusDays(3);
            LocalDate fourDaysAgo = today.minusDays(4);
            LocalDate fiveDaysAgo = today.minusDays(5);
        LocalDate nextWeek = today.plusDays(7);
        LocalDate nextMonth = today.plusMonths(1);
        
            String patientId1 = patientList.get(0).getPatientId();
            String patientId2 = patientList.get(1).getPatientId();
            String patientId3 = patientList.get(2).getPatientId();
            String patientId4 = patientList.get(3).getPatientId();
            String patientId5 = patientList.get(4).getPatientId();
            String patientId6 = patientList.get(5).getPatientId();
            String patientId7 = patientList.get(6).getPatientId();
            String patientId8 = patientList.get(7).getPatientId();

            String doctorId1 = doctorList.get(0).getId();
            String doctorId2 = doctorList.get(1).getId();
            String doctorId3 = doctorList.get(2).getId();
            String doctorId4 = doctorList.get(3).getId();
            String doctorId5 = doctorList.get(4).getId();

            // For now, use placeholder consultation IDs - these will be linked when consultations are created
            TREATMENTS.add(new MedicalTreatment(patientId1, doctorId1, "C001", "Hypertension",
                                             "High blood pressure, headache, dizziness", 
                                             "Primary hypertension", 
                                             "Blood pressure monitoring and medication", 
                                             "Amlodipine 5mg daily", today, nextMonth, 150.00));
            TREATMENTS.add(new MedicalTreatment(patientId2, doctorId2, "C002", "Diabetes Type 2",
                                             "Increased thirst, frequent urination, fatigue", 
                                             "Type 2 diabetes mellitus", 
                                             "Blood sugar monitoring and diet control", 
                                             "Metformin 500mg twice daily", yesterday, nextMonth, 200.00));
            TREATMENTS.add(new MedicalTreatment(patientId3, doctorId3, "C003", "Asthma",
                                             "Shortness of breath, wheezing, chest tightness", 
                                             "Bronchial asthma", 
                                             "Inhaler therapy and trigger avoidance", 
                                             "Salbutamol inhaler as needed", twoDaysAgo, nextWeek, 120.00));
            TREATMENTS.add(new MedicalTreatment(patientId4, doctorId1, "C004", "Migraine",
                                             "Severe headache, nausea, sensitivity to light", 
                                             "Classic migraine", 
                                             "Pain management and lifestyle changes", 
                                             "Ibuprofen 400mg as needed", today, nextWeek, 80.00));
            TREATMENTS.add(new MedicalTreatment(patientId5, doctorId2, "C005", "Back Pain",
                    "Lower back pain, difficulty moving",
                                             "Lumbar strain", 
                    "Physical therapy and pain management",
                    "Diclofenac 50mg twice daily", threeDaysAgo, nextWeek, 180.00));
            
            // Add more treatments to demonstrate frequency analysis
            TREATMENTS.add(new MedicalTreatment(patientId1, doctorId3, "C006", "Hypertension",
                    "Follow-up for blood pressure control",
                    "Primary hypertension",
                    "Medication adjustment and monitoring",
                    "Amlodipine 10mg daily", fourDaysAgo, nextMonth, 160.00));
            TREATMENTS.add(new MedicalTreatment(patientId2, doctorId4, "C007", "Diabetes Type 2",
                    "Routine diabetes check-up",
                    "Type 2 diabetes mellitus",
                    "Blood sugar monitoring and medication review",
                    "Metformin 1000mg twice daily", fiveDaysAgo, nextMonth, 220.00));
            TREATMENTS.add(new MedicalTreatment(patientId6, doctorId1, "C008", "Hypertension",
                    "New patient with high blood pressure",
                    "Primary hypertension",
                    "Initial assessment and medication",
                    "Lisinopril 10mg daily", twoDaysAgo, nextMonth, 170.00));
            TREATMENTS.add(new MedicalTreatment(patientId7, doctorId2, "C009", "Asthma",
                    "Asthma exacerbation",
                    "Bronchial asthma",
                    "Emergency treatment and medication adjustment",
                    "Prednisone 20mg daily for 5 days", yesterday, nextWeek, 140.00));
            TREATMENTS.add(new MedicalTreatment(patientId8, doctorId3, "C010", "Migraine",
                    "Chronic migraine management",
                    "Chronic migraine",
                    "Preventive medication and lifestyle counseling",
                    "Propranolol 40mg twice daily", today, nextMonth, 190.00));
            
            // Add more treatments for better frequency distribution
            TREATMENTS.add(new MedicalTreatment(patientId3, doctorId4, "C011", "Diabetes Type 2",
                    "Diabetes screening and diagnosis",
                    "Type 2 diabetes mellitus",
                    "Initial diabetes management plan",
                    "Metformin 500mg daily", threeDaysAgo, nextMonth, 210.00));
            TREATMENTS.add(new MedicalTreatment(patientId4, doctorId5, "C012", "Back Pain",
                    "Chronic back pain evaluation",
                    "Chronic lumbar pain",
                    "Comprehensive pain management",
                    "Tramadol 50mg as needed", fourDaysAgo, nextWeek, 250.00));
            TREATMENTS.add(new MedicalTreatment(patientId5, doctorId1, "C013", "Hypertension",
                    "Hypertension follow-up",
                    "Primary hypertension",
                    "Blood pressure monitoring and medication review",
                    "Amlodipine 5mg daily", fiveDaysAgo, nextMonth, 155.00));
            TREATMENTS.add(new MedicalTreatment(patientId6, doctorId2, "C014", "Asthma",
                    "Asthma control assessment",
                    "Bronchial asthma",
                    "Inhaler technique review and medication adjustment",
                    "Formoterol 12mcg twice daily", twoDaysAgo, nextWeek, 130.00));
            TREATMENTS.add(new MedicalTreatment(patientId7, doctorId3, "C015", "Migraine",
                    "Migraine prevention consultation",
                    "Chronic migraine",
                    "Preventive treatment plan",
                    "Topiramate 25mg daily", yesterday, nextMonth, 200.00));
        }
        return TREATMENTS;
    }
    
    public static ListInterface<Medicine> initializeMedicines() {
        if (MEDICINES == null) {
            MEDICINES = new SortedArrayList<>();
            // Add sample medicines
            MEDICINES.add(new Medicine("Paracetamol", "Tablet", 0.50, 500, 100));
            MEDICINES.add(new Medicine("Ibuprofen", "Tablet", 0.80, 300, 80));
            MEDICINES.add(new Medicine("Amoxicillin", "Capsule", 1.20, 200, 60));
            MEDICINES.add(new Medicine("Salbutamol", "Inhaler", 15.00, 50, 10));
            MEDICINES.add(new Medicine("Metformin", "Tablet", 0.70, 400, 100));
            MEDICINES.add(new Medicine("Amlodipine", "Tablet", 0.95, 250, 60));
            MEDICINES.add(new Medicine("Diclofenac", "Tablet", 0.65, 350, 70));
            MEDICINES.add(new Medicine("Propranolol", "Tablet", 0.90, 220, 50));
        }
        return MEDICINES;
    }
    
    public static ListInterface<DispenseRecord> initializeDispenseRecords() {
        if (DISPENSES == null) {
            DISPENSES = new SortedArrayList<>();
            // Sample dispenses will be added as pharmacy operations occur
            // Add a few initial records to demonstrate reports
            ListInterface<MedicalTreatment> treatments = initializeMedicalTreatments();
            ListInterface<Medicine> meds = initializeMedicines();
            if (treatments.size() >= 8 && meds.size() >= 5) {
                DISPENSES.add(new DispenseRecord(treatments.get(0).getTreatmentId(), meds.get(0).getMedicineId(), 10, LocalDate.now().minusDays(5), meds.get(0).getUnitPrice()));
                DISPENSES.add(new DispenseRecord(treatments.get(1).getTreatmentId(), meds.get(1).getMedicineId(), 5, LocalDate.now().minusDays(4), meds.get(1).getUnitPrice()));
                DISPENSES.add(new DispenseRecord(treatments.get(2).getTreatmentId(), meds.get(2).getMedicineId(), 3, LocalDate.now().minusDays(3), meds.get(2).getUnitPrice()));
                DISPENSES.add(new DispenseRecord(treatments.get(3).getTreatmentId(), meds.get(3).getMedicineId(), 2, LocalDate.now().minusDays(2), meds.get(3).getUnitPrice()));
                DISPENSES.add(new DispenseRecord(treatments.get(4).getTreatmentId(), meds.get(4).getMedicineId(), 12, LocalDate.now().minusDays(1), meds.get(4).getUnitPrice()));
                DISPENSES.add(new DispenseRecord(treatments.get(5).getTreatmentId(), meds.get(0).getMedicineId(), 8, LocalDate.now(), meds.get(0).getUnitPrice()));
                DISPENSES.add(new DispenseRecord(treatments.get(6).getTreatmentId(), meds.get(1).getMedicineId(), 6, LocalDate.now().minusDays(6), meds.get(1).getUnitPrice()));
            }
        }
        return DISPENSES;
    }
    
    public static ListInterface<Consultation> initializeConsultations() {
        if (CONSULTATIONS == null) {
            CONSULTATIONS = new SortedArrayList<>();
            // Link seeded consultations to real doctors and patients
            ListInterface<Doctor> doctorList = initializeDoctors();
            ListInterface<Patient> patientList = initializePatients();

            if (doctorList.size() >= 5 && patientList.size() >= 8) {
                Doctor d1 = doctorList.get(0); // Dr. Sarah Chen (Mon,Wed,Fri)
                Doctor d2 = doctorList.get(1); // Dr. Michael Wong (Tue,Thu)
                Doctor d3 = doctorList.get(2); // Dr. Emily Rodriguez (Mon,Fri)
                Doctor d4 = doctorList.get(3); // Dr. James Lee (Wed,Thu)
                Doctor d5 = doctorList.get(4); // Dr. Lisa Patel (Mon..Fri)

                Patient p1 = patientList.get(0);
                Patient p2 = patientList.get(1);
                Patient p3 = patientList.get(2);
                Patient p4 = patientList.get(3);
                Patient p5 = patientList.get(4);
                Patient p6 = patientList.get(5);
                Patient p7 = patientList.get(6);
                Patient p8 = patientList.get(7);

                // d1 on Monday/Wednesday/Friday
                Consultation c1 = new Consultation(d1.getId(), d1.getName(), p1.getPatientId(), p1.getName(), "Monday", 9, 0, 30, "Hypertension review");
                c1.setStatus("COMPLETED");
                CONSULTATIONS.add(c1);

                Consultation c2 = new Consultation(d1.getId(), d1.getName(), p4.getPatientId(), p4.getName(), "Wednesday", 10, 30, 90, "Migraine assessment");
                c2.setStatus("BOOKED");
                CONSULTATIONS.add(c2);

                // d2 on Tuesday/Thursday
                Consultation c3 = new Consultation(d2.getId(), d2.getName(), p2.getPatientId(), p2.getName(), "Tuesday", 14, 0, 30, "Diabetes management");
                c3.setStatus("COMPLETED");
                CONSULTATIONS.add(c3);

                Consultation c4 = new Consultation(d2.getId(), d2.getName(), p5.getPatientId(), p5.getName(), "Thursday", 16, 0, 90, "Back pain follow-up");
                c4.setStatus("BOOKED");
                CONSULTATIONS.add(c4);

                // d3 on Monday/Friday
                Consultation c5 = new Consultation(d3.getId(), d3.getName(), p3.getPatientId(), p3.getName(), "Friday", 10, 30, 30, "Asthma review");
                c5.setStatus("COMPLETED");
                CONSULTATIONS.add(c5);

                Consultation c6 = new Consultation(d3.getId(), d3.getName(), p6.getPatientId(), p6.getName(), "Monday", 11, 0, 30, "Allergy consultation");
                c6.setStatus("BOOKED");
                CONSULTATIONS.add(c6);

                // d4 on Wednesday/Thursday
                Consultation c7 = new Consultation(d4.getId(), d4.getName(), p7.getPatientId(), p7.getName(), "Wednesday", 15, 0, 90, "Orthopedics evaluation");
                c7.setStatus("BOOKED");
                CONSULTATIONS.add(c7);

                // d5 on Mon..Fri
                Consultation c8 = new Consultation(d5.getId(), d5.getName(), p8.getPatientId(), p8.getName(), "Tuesday", 9, 0, 30, "Routine check-up");
                c8.setStatus("COMPLETED");
                CONSULTATIONS.add(c8);

                Consultation c9 = new Consultation(d5.getId(), d5.getName(), p1.getPatientId(), p1.getName(), "Thursday", 13, 30, 30, "BP monitoring");
                c9.setStatus("BOOKED");
                CONSULTATIONS.add(c9);

                Consultation c10 = new Consultation(d5.getId(), d5.getName(), p2.getPatientId(), p2.getName(), "Friday", 17, 0, 90, "Comprehensive review");
                c10.setStatus("BOOKED");
                CONSULTATIONS.add(c10);
            }
        }
        return CONSULTATIONS;
    }
    
    public static void assignSampleDuties(ListInterface<Doctor> doctorList) {
        // No-op: using preset dutyDays in constructor for this simplified model
    }
    
    
}
