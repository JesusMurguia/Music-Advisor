package Controller;

import Model.Album;
import Model.SpotifyObject;
import Model.URLS;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class AlbumController extends SpotifyController{
    public JsonObject getData(String uri) {
        return super.getData(uri);
    }

    @Override
    public List<SpotifyObject> format(JsonObject obj, int currentPage, int totalPages) {
        List<SpotifyObject> albums = new ArrayList<>();
        List<JsonElement> items = getPages(obj.get("albums").getAsJsonObject().get("items").getAsJsonArray().asList(), Integer.parseInt(URLS.getLimit())).get(currentPage - 1);
        for (JsonElement album : items) {
            ArrayList<String> artists = new ArrayList<>();
            for (JsonElement artist : album.getAsJsonObject().get("artists").getAsJsonArray()) {
                artists.add(artist.getAsJsonObject().get("name").getAsString());
            }
            String id = album.getAsJsonObject().get("id").getAsString();
            String name = album.getAsJsonObject().get("name").getAsString();
            String url = album.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString();
            albums.add(new Album(id, name, url, artists.toArray(new String[0])));
        }
        return albums;
    }
}
