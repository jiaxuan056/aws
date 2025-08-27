// dao/ConsultationInitializer.java
package dao;

import adt.ArrayList;
import adt.ListInterface;
import entity.Consultation;
import entity.Doctor;

public class ConsultationInitializer {
    public static ListInterface<Consultation> seed(ListInterface<Doctor> doctors) {
        ListInterface<Consultation> list = new ArrayList<>();
        if (doctors == null || doctors.isEmpty()) return list;

        Doctor d1 = doctors.getEntry(1);
        if (d1 != null) {
            list.add(new Consultation(
                d1.getId(), d1.getName(), "P1001", "Alice Ng",
                "Mon", 10, 0, 30, "First visit"
            ));
        }
        if (doctors.getNumberOfEntries() >= 2) {
            Doctor d2 = doctors.getEntry(2);
            if (d2 != null) {
                list.add(new Consultation(
                    d2.getId(), d2.getName(), "P1002", "Jason Lim",
                    "Wed", 14, 30, 90, "Follow-up"
                ));
            }
        }
        return list;
    }
}
