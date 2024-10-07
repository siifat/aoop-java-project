package lms.controller.student;

import javafx.beans.property.StringProperty;

public class Course {
    private String sub;
    private String ct1;
    private String ct2;
    private String ct3;
    private String assignment1;
    private String assignment2;
    private String mid;
    private String finale;

    public Course(String sub, String ct1, String ct2, String ct3, String assignment1, String assignment2, String mid, String finale) {
        this.sub = sub;
        this.ct1 = ct1;
        this.ct2 = ct2;
        this.ct3 = ct3;
        this.assignment1 = assignment1;
        this.assignment2 = assignment2;
        this.mid = mid;
        this.finale = finale;
    }

    public String getSub() {
        return sub;
    }

    public String getCt1() {
        return ct1;
    }

    public String getCt2() {
        return ct2;
    }

    public String getCt3() {
        return ct3;
    }

    public String getAssignment1() {
        return assignment1;
    }

    public String getAssignment2() {
        return assignment2;
    }

    public String getMid() {
        return mid;
    }

    public String getFinale() {
        return finale;
    }
}
