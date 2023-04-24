package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpRequests {

    public HttpRequests(){
    }

    public static String getQuery(String query){
        String spaceFreeQuery = query.replaceAll("\\s+", "%20");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-search74.p.rapidapi.com/?query="+spaceFreeQuery+"&limit=3&related_keywords=false"))
                .header("X-RapidAPI-Key", "4d03f88e57msha2957900ed0414bp136955jsn98f06480b074")
                .header("X-RapidAPI-Host", "google-search74.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Google Response: \n"+response.body());

            ObjectMapper objectMapper = new ObjectMapper();
            SearchResults searchResults = objectMapper.readValue(response.body(), SearchResults.class);

            // Print the search results
            List<SearchResult> results = searchResults.getResults();
            for (SearchResult result : results) {
                System.out.println(result.getTitle() + " "+ result.getUrl()+ ""+ result.getDescription());
            }

            return response.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
