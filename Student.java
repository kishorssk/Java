package inninc.nieplacementcellapp;

/**
 * Created by Pradyumna1 on 4/22/2017.
 */
public class Student {

    private String usn;
    private String name;
    private String dob;
    private String email;
    private String phone;
    private String branch;
    private String cgpa;
    private String cgpaPercent;
    private String pucMarks;
    private String schoolMarks;
    private String password;
    private String placed;

    public Student() {
    }

    public Student(String usn, String name, String dob, String email, String phone, String branch, String cgpa, String cgpaPercent, String pucMarks, String schoolMarks) {
        this.usn = usn;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.branch = branch;
        this.cgpa = cgpa;
        this.cgpaPercent = cgpaPercent;
        this.pucMarks = pucMarks;
        this.schoolMarks = schoolMarks;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getCgpaPercent() {
        return cgpaPercent;
    }

    public void setCgpaPercent(String cgpaPercent) {
        this.cgpaPercent = cgpaPercent;
    }

    public String getPucMarks() {
        return pucMarks;
    }

    public void setPucMarks(String pucMarks) {
        this.pucMarks = pucMarks;
    }

    public String getSchoolMarks() {
        return schoolMarks;
    }

    public void setSchoolMarks(String schoolMarks) {
        this.schoolMarks = schoolMarks;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlaced() {
        return placed;
    }

    public void setPlaced(String placed) {
        this.placed = placed;
    }
}
