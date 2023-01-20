package Controller;

import Model.Album;
import Model.SpotifyObject;
import Model.URLS;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class SpotifyController {
    public JsonObject getData(String uri){
        AuthController auth = new AuthController();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + auth.getAccessToken())
                .uri(URI.create(URLS.getResource()+uri))
                .GET()
                .build();
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return JsonParser.parseString(response.body()).getAsJsonObject();
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    public abstract List<SpotifyObject> format(JsonObject obj, int currentPage, int totalPages);



    public static <T> List<List<T>> getPages(Collection<T> c, Integer pageSize) {
        if (c == null)
            return Collections.emptyList();
        List<T> list = new ArrayList<T>(c);
        if (pageSize == null || pageSize <= 0 || pageSize > list.size())
            pageSize = list.size();
        int numPages = (int) Math.ceil((double)list.size() / (double)pageSize);
        List<List<T>> pages = new ArrayList<List<T>>(numPages);
        for (int pageNum = 0; pageNum < numPages;)
            pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
        return pages;
    }
}
