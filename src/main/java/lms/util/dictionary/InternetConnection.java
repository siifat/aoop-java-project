package lms.util.dictionary;

import org.fxmisc.richtext.InlineCssTextArea;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class InternetConnection {

    public static InlineCssTextArea getOnlineData(String word) {
        InlineCssTextArea textArea = null;
        String data = "";
        try {
            URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + word);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            if (con.getResponseCode() > 800) {
                System.out.println("Internet Connection error");
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = br.readLine();
                while (line != null) {
                    data = data + line;
                    line = br.readLine();
                }
                br.close();

                // Decode JSON data
                textArea = JSONDecoder.decode(data);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return textArea;
    }
}





