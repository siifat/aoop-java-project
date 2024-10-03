package lms.controller.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lms.controller.cat.CatImage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CatAPI implements APIService<CatImage> {
    private String catURL;
    public CatAPI() {

    }

    /**
     * Get data from API
     * @return return translated text from API
     */
    @Override
    public CatImage getData() {
        CatImage catImage = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.thecatapi.com/v1/images/search"))
                    .build();
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);
            for (JsonElement element : jsonArray) {
                catImage = gson.fromJson(element, CatImage.class);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

        return catImage;
    }
}
