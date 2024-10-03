package lms.controller.admin;

public class StApproveData {
    private String id;
    private String name;
    private String email;
    private String mobile;
    private String role;
    private String registration;
    private String approved;

    public StApproveData(String name, String id, String email, String mobile, String role, String registration, String approved) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.mobile = mobile;
        this.role = role;
        this.registration = registration;
        this.approved = approved;
    }

    public String getId() {return id;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getMobile() {return mobile;}
    public String getRole() {return role;}
    public String getRegistration() {return registration;}
    public String getApproved() {
        return approved;
    }
}
