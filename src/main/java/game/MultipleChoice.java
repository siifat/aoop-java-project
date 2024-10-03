package game;

import java.util.ArrayList;

public class MultipleChoice extends Game {
    private ArrayList<MultipleChoiceQuestion> questions;
    private int numberOfQuestions;

    public MultipleChoice() {
        questions = new ArrayList<>();
    }

    public MultipleChoiceQuestion returnRandomQuestion() {
        int min = 0;
        int max = this.numberOfQuestions - 1;
        int randomInt = 0;

        while (true) {
            randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);
            if (!this.questions.get(randomInt).isChosen()) {
                this.questions.get(randomInt).setChosen(true);
                return this.questions.get(randomInt);
            }
        }
    }

    public ArrayList<MultipleChoiceQuestion> getQuestions() {
        return this.questions;
    }

    public int getNumberOfQuestions() {
        return this.numberOfQuestions;
    }

    public void addQuestion(MultipleChoiceQuestion q) {
        this.questions.add(q);
        numberOfQuestions = this.questions.size();
    }

    public static MultipleChoice getMultipleChoice() {
        MultipleChoice multipleChoice = new MultipleChoice();

        multipleChoice.addQuestion(new MultipleChoiceQuestion("Hello! My name ___ Sifat.", "is", "are", "was", "were", "A"));
        multipleChoice.addQuestion(new MultipleChoiceQuestion("Here ___ the train.", "came", "come", "comes", "coming", "C"));
        multipleChoice.addQuestion(new MultipleChoiceQuestion("We're ___ dinner. Do you wanna join?", "have", "having", "has", "had", "B"));
        multipleChoice.addQuestion(new MultipleChoiceQuestion("At 8PM last night, I ___ the television.", "watched", "has watched", "had watched", "was watching", "D"));
        multipleChoice.addQuestion(new MultipleChoiceQuestion("I ___ have a big meeting tomorrow.", "will", "is going to", "is", "shall", "A"));
        multipleChoice.addQuestion(new MultipleChoiceQuestion("Do you like the app?", "Yes", "No", "I hate it", "This app is terrible", "A"));
        multipleChoice.addQuestion(new MultipleChoiceQuestion("What ___ you doing?", "is", "are", "am", "was", "B"));
        multipleChoice.addQuestion(new MultipleChoiceQuestion("I ___ to the cinema last night.", "go", "goes","went", "going", "C"));
        multipleChoice.addQuestion(new MultipleChoiceQuestion("How much do you rate this app?", "7.5", "2.5", "5", "10", "D"));

        return multipleChoice;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (MultipleChoiceQuestion q : questions) {
            result.append(q.toString()).append(q.isChosen()).append("\n");
        }
        return result.toString();
    }
}
