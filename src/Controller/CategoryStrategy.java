package Controller;

import Model.Category;
import Model.SpotifyObject;
import View.View;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CategoryStrategy implements CommandStrategy{
    CategoryController controller = new CategoryController();
    @Override
    public void handle(int currentPage, int totalPages) {
        JsonObject obj = controller.getData("/v1/browse/categories");
        JsonElement totalRef = obj.get("categories").getAsJsonObject().get("total");
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
        for (SpotifyObject c: controller.format(obj, currentPage, totalPages)) {
            Category category = (Category) c;
            System.out.println(category.getName());
        }
        View.getInstance().print("---PAGE "+currentPage+" OF "+totalPages+"---");
    }
}
