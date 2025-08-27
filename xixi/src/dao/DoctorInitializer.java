package dao;

import entity.Doctor;
import adt.ListInterface;
import adt.ArrayList;

public class DoctorInitializer {

    public static ListInterface<Doctor> initializeDoctors() {
        ListInterface<Doctor> doctorList = new ArrayList<>();

        doctorList.add(new Doctor("Dr. Alice Tan", "Cardiology", "Mon,Tue,Wed,Thu,Fri"));
        doctorList.add(new Doctor("Dr. Benjamin Lee", "Orthopedics", "Tue,Wed,Thu,Fri,Sat"));
        doctorList.add(new Doctor("Dr. Clara Wong", "Dermatology", "Mon,Tue,Wed,Thu"));
        doctorList.add(new Doctor("Dr. Daniel Lim", "Pediatrics", "Wed,Thu,Fri,Sat,Sun"));
        doctorList.add(new Doctor("Dr. Emily Chua", "Neurology", "Mond,Tue,Wed,Thu,Fri"));

        return doctorList;
    }

public static void main(String[] args) { 
    DoctorInitializer d = new DoctorInitializer(); 
    ListInterface<Doctor> doctorList = d.initializeDoctors(); 
    System.out.println("Doctors:\n" + doctorList); }
}
