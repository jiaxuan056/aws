//Chau Jia Xuan
package entity;

public class Patient implements Comparable<Patient> {
    private static int ID = 1001;
    private String patientId;
    private String name;
    private String gender;
    private int age;
    private String contact;
    private String sickness;

    public Patient(String name, String gender, int age, String contact, String sickness) {
        this.patientId = "P" + ID++;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.contact = contact;
        this.sickness = sickness;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getContact() {
        return contact;
    }
    
    public String getSickness() {
        return sickness;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public void setSickness(String sickness) {
        this.sickness = sickness;
    }

    public int compareTo(Patient other) {
        return this.name.compareTo(other.name);
    }
    
    public String toString() {
        return patientId + " - " + name + " (" + gender + ", " + age + "y, " + contact + ", " + sickness + ")";
    }
}
