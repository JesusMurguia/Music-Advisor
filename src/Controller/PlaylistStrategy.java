package Controller;

import Model.Category;
import Model.Playlist;
import Model.SpotifyObject;
import Model.URLS;
import View.View;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PlaylistStrategy implements CommandStrategy{
    CategoryController categoryController = new CategoryController();
    PlaylistController controller = new PlaylistController();
    String name;

    public PlaylistStrategy(String name){
        this.name = name;
    }

    @Override
    public void handle(int currentPage, int totalPages) {
        Category category = categoryController.findCategoryByName(name.trim(), currentPage, totalPages);
        if(category == null){
            View.getInstance().print("Unknown category name.");
            return;
        }
        JsonObject obj = controller.getData("/v1/browse/categories/"+category.getId()+"/playlists?");
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
