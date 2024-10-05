package lms.controller.teacher;

public class CourseElement {
    private String title;
    private String description;
    private String uploadDate;
    private String section;
    private String path;

    public CourseElement(String title, String description, String uploadDate, String section, String path) {
        this.title = title;
        this.description = description;
        this.uploadDate = uploadDate;
        this.section = section;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setDueDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
