package lms.controller.teacher;

import javafx.scene.control.CheckBox;

public class StudentData {
    private String name;
    private String id;
    private String email;
    private CheckBox checkBox;

    public StudentData(String name, String id, String email, CheckBox checkBox) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.checkBox = checkBox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
