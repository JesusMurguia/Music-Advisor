package Controller;

import Model.Category;
import Model.Playlist;
import Model.SpotifyObject;
import Model.URLS;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class PlaylistController extends SpotifyController{
    public JsonObject getData(String uri) {
        return super.getData(uri);
    }

    @Override
    public List<SpotifyObject> format(JsonObject obj, int currentPage, int totalPages) {
        List<SpotifyObject> playlists = new ArrayList<>();
        if(obj.get("playlists") == null && obj.get("error") != null){
            System.out.println(obj.get("error").getAsJsonObject().get("message").getAsString());
            return playlists;
        }
        List<JsonElement> items = getPages(obj.get("playlists").getAsJsonObject().get("items").getAsJsonArray().asList(), Integer.parseInt(URLS.getLimit())).get(currentPage - 1);
        for (JsonElement playlist : items) {
            String id = playlist.getAsJsonObject().get("id").getAsString();
            String name = playlist.getAsJsonObject().get("name").getAsString();
            String url = playlist.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString();
            playlists.add(new Playlist(id, name, url));
        }
        return playlists;
    }
}
