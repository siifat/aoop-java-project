package lms.controller.student;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.File;

public class PDFFile {
    private String title;
    private String description;  // Size in KB or MB
    private LocalDateTime uploadDate;
    private String section = "A";  // Since there's only Section A

    public PDFFile(String title, String description, LocalDateTime uploadDate) {
        this.title = title;
        this.description = description;
        this.uploadDate = uploadDate;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getUploadDate() { return uploadDate; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }
}
