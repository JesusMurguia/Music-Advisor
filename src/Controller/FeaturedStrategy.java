package Controller;

import Model.Playlist;
import Model.SpotifyObject;
import View.View;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FeaturedStrategy implements CommandStrategy{
    PlaylistController controller = new PlaylistController();
    @Override
    public void handle(int currentPage, int totalPages) {
        JsonObject obj = controller.getData("/v1/browse/featured-playlists");
        JsonElement totalRef = obj.get("playlists").getAsJsonObject().get("total");
        if(totalRef!=null){
            totalPages = totalRef.getAsInt();
        }
        if(currentPage <= 0){
            CommandExecuter.nextPage();
            View.getInstance().print("No more pages.");
            return;
        }
        if(currentPage > totalPages){
            CommandExecuter.prevPage();
            View.getInstance().print("No more pages.");
            return;
        }
        for (SpotifyObject p: controller.format(obj, currentPage, totalPages)) {
            Playlist playlist = (Playlist) p;
            View.getInstance().print(playlist.getName());
            View.getInstance().print(playlist.getUrl()+"\n");
        }
        View.getInstance().print("---PAGE "+currentPage+" OF "+totalPages+"---");
    }
}
