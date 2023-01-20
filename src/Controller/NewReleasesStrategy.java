package Controller;

import Model.Album;
import Model.SpotifyObject;
import View.View;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;

public class NewReleasesStrategy implements CommandStrategy{
    AlbumController controller = new AlbumController();
    @Override
    public void handle(int currentPage, int totalPages) {
        JsonObject obj = controller.getData("/v1/browse/new-releases");
        JsonElement totalRef = obj.get("albums").getAsJsonObject().get("total");
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
        for (SpotifyObject a: controller.format(obj, currentPage,totalPages)) {
            Album album = (Album) a;
            View.getInstance().print(album.getName());
            View.getInstance().print(Arrays.toString(album.getArtists()));
            View.getInstance().print(album.getUrl()+"\n");
        }
        View.getInstance().print("---PAGE "+currentPage+" OF "+totalPages+"---");
    }
}
