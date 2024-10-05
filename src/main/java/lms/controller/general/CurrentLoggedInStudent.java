package lms.controller.general;

public class CurrentLoggedInStudent {
    private String name;
    private String email;
    private String id;
    private String password;
    private String approved;
    private String mobile;
    private String registration;
    private String role;
    private String wantEmail;
    private String profilePicture;
    private String bio;

    public CurrentLoggedInStudent(String name, String email, String id, String password, String approved, String mobile, String registration, String role, String wantEmail, String profilePicture, String bio) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.password = password;
        this.approved = approved;
        this.mobile = mobile;
        this.registration = registration;
        this.role = role;
        this.wantEmail = wantEmail;
        this.profilePicture = profilePicture;
        this.bio = bio;
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

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getWantEmail() {
        return wantEmail;
    }

    public void setWantEmail(String wantEmail) {
        this.wantEmail = wantEmail;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
