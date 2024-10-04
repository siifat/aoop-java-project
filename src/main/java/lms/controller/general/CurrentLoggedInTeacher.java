package lms.controller.general;

public class CurrentLoggedInTeacher {
    private String name;
    private String email;
    private String id;
    private String password;
    private String initial;
    private String mobile;
    private String role;
    private String approve;
    private String wantEmail;

    public CurrentLoggedInTeacher(String name, String email, String id, String password, String initial, String mobile, String role, String approve, String wantEmail) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.password = password;
        this.initial = initial;
        this.mobile = mobile;
        this.role = role;
        this.approve = approve;
        this.wantEmail = wantEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getWantEmail() {
        return wantEmail;
    }

    public void setWantEmail(String wantEmail) {
        this.wantEmail = wantEmail;
    }
}
