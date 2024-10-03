package lms.util;

public enum Scenes {
    ADMIN_HOME("/admin/adminHome.fxml"),
    TEACHER_HOME("/teacher/teacherHome.fxml"),
    LOGIN("/general/login.fxml"),
    CREATE_ACC("/general/createAcc.fxml"),
    FORGOT_PASS("/general/forgotPass.fxml"),
    STUDENT_DASHBOARD("/student/dashboard.fxml");

    private final String resourceLocation;

    Scenes(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public String getResourceLocation() {
        return resourceLocation;
    }
}
