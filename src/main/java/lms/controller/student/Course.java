package lms.controller.student;

public class Course {
    private String sub;
    private String ct1;
    private String ct2;
    private String ct3;
    private String assignment1;
    private String assignment;
    private String mid;
    private String finale;

    public Course(String sub, String ct1, String ct2, String ct3, String assignment1, String assignment, String mid, String finale) {
        this.sub = sub;
        this.ct1 = ct1;
        this.ct2 = ct2;
        this.ct3 = ct3;
        this.assignment1 = assignment1;
        this.assignment = assignment;
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

    public String getAssignment() {
        return assignment;
    }

    public String getMid() {
        return mid;
    }

    public String getFinale() {
        return finale;
    }
}
