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

    public String getCName() {
        return cName;
    }

    public String getCId() {
        return cId;
    }

    public String getCEmail() {
        return cEmail;
    }

    public String getCRole() {
        return cRole;
    }

    public String getCComplainTitle() {
        return cComplainTitle;
    }

    public String getCDescription() {
        return cDescription;
    }
}