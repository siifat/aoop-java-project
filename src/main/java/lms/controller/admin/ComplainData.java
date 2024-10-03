package lms.controller.admin;

public class ComplainData {
    private String cName;
    private String cId;
    private String cEmail;
    private String cRole;
    private String cComplainTitle;
    private String cDescription;

    public ComplainData(String cName, String cId, String cEmail, String cRole, String cComplainTitle, String cDescription) {
        this.cName = cName;
        this.cId = cId;
        this.cEmail = cEmail;
        this.cRole = cRole;
        this.cComplainTitle = cComplainTitle;
        this.cDescription = cDescription;
    }

    public String getcName() {
        return cName;
    }

    public String getcId() {
        return cId;
    }

    public String getcEmail() {
        return cEmail;
    }

    public String getcRole() {
        return cRole;
    }

    public String getcComplainTitle() {
        return cComplainTitle;
    }

    public String getcDescription() {
        return cDescription;
    }
}