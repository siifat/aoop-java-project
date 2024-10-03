package lms.controller.admin;

public class TeachApproveData {
    private String initial;
    private String name;
    private String email;
    private String mobile;
    private String role;
    private String approved;

    public TeachApproveData(String initial, String name, String email, String mobile, String role, String approved) {
        this.initial = initial;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.role = role;
        this.approved = approved;
    }

    public String getInitial() {return initial;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getMobile() {return mobile;}
    public String getRole() {return role;}
    public String getApproved() {
        return approved;
    }
}
