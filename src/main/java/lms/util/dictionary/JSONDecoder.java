package lms.util.dictionary;

import org.fxmisc.richtext.InlineCssTextArea;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JSONDecoder {
    public static InlineCssTextArea decode(String data) {
        InlineCssTextArea textArea = new InlineCssTextArea();
        textArea.setWrapText(true);

        try {
            Object object = JSONValue.parse(data);
            JSONArray jsonArray = (JSONArray) object;
            JSONObject jsonObject = (JSONObject) jsonArray.getFirst();

            String word = (String) jsonObject.get("word");
            String phonetic = (String) jsonObject.get("phonetic");

            textArea.appendText("Word: ");
            textArea.append(word + "\n", "-fx-font-weight: bold; -fx-fill: darkblue;");
            textArea.appendText("Phonetic: ");
            textArea.append(phonetic + "\n\n", "-fx-font-style: italic; -fx-fill: darkgreen;");

            JSONArray meaningsArray = (JSONArray) jsonObject.get("meanings");
            for (int i = 0; i < meaningsArray.size(); i++) {
                JSONObject meaningObject = (JSONObject) meaningsArray.get(i);
                String partOfSpeech = (String) meaningObject.get("partOfSpeech");

                textArea.appendText("Part of Speech: ");
                textArea.append(partOfSpeech + "\n", "-fx-font-weight: bold; -fx-fill: darkred;");

                JSONArray definitionsArray = (JSONArray) meaningObject.get("definitions");
                for (int j = 0; j < definitionsArray.size() && j < 2; j++) { // Limit to 2 definitions
                    JSONObject definitionObject = (JSONObject) definitionsArray.get(j);
                    String definition = (String) definitionObject.get("definition");

                    textArea.appendText("  Definition " + (j + 1) + ": ");
                    textArea.append(definition + "\n", "-fx-fill: black;");

                    // Check for example if exists
                    if (definitionObject.containsKey("example")) {
                        String example = (String) definitionObject.get("example");
                        textArea.appendText("    Example: ");
                        textArea.append(example + "\n", "-fx-font-style: italic; -fx-fill: gray;");
                    }

                    // Check for synonyms if exists
                    JSONArray synonymsArray = (JSONArray) definitionObject.get("synonyms");
                    if (synonymsArray != null && !synonymsArray.isEmpty()) {
                        textArea.appendText("    Synonyms: ");
                        for (int k = 0; k < synonymsArray.size(); k++) {
                            textArea.append(synonymsArray.get(k) + ", ", "-fx-fill: black;");
                        }
                        textArea.replaceText(textArea.getText().length() - 2, textArea.getText().length(), "\n");
                    }

                    // Check for antonyms if exists
                    JSONArray antonymsArray = (JSONArray) definitionObject.get("antonyms");
                    if (antonymsArray != null && !antonymsArray.isEmpty()) {
                        textArea.appendText("    Antonyms: ");
                        for (int k = 0; k < antonymsArray.size(); k++) {
                            textArea.append(antonymsArray.get(k) + ", ", "-fx-fill: black;");
                        }
                        textArea.replaceText(textArea.getText().length() - 2, textArea.getText().length(), "\n");
                    }
                }
                textArea.appendText("\n"); // Add separation between different parts of speech
            }
        } catch (Exception e) {
            textArea.append("error", "-fx-fill: red;");
            e.printStackTrace();
        }

        return textArea;
    }
}


