package lms.controller.dictionary;

public class Record {
    private String sourceLanguage;
    private String targetLanguage;
    private String sourceText;
    private String targetText;

    public Record(String sourceLanguage, String targetLanguage, String sourceText, String targetText) {
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.sourceText = sourceText;
        this.targetText = targetText;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public String getSourceText() {
        return sourceText;
    }

    public String getTargetText() {
        return targetText;
    }
}
