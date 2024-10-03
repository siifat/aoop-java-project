package lms.controller.dictionary;

public class Word {
    private String word;
    private String meaning;
    private boolean isBookmarked;

    public Word(String word, String meaning, boolean isBookmarked) {
        this.word = word;
        this.meaning = meaning;
        this.isBookmarked = isBookmarked;
    }

    public Word(String word, String meaning, int isBookmarked) {
        this.word = word;
        this.meaning = meaning;
        if (isBookmarked == 1) {
            this.isBookmarked = true;
        } else {
            this.isBookmarked = false;
        }
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    @Override
    public String toString() {
        return this.getWord();
    }
}

