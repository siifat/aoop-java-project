package lms.controller.teacher;

public class Assignment {
    private String title;
    private String description;
    private String dueDate;
    private String section;
    private String path;

    public Assignment(String title, String description, String dueDate, String section, String path) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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
