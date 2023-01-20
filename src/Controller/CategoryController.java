package Controller;

import Model.Category;
import Model.SpotifyObject;
import Model.URLS;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryController extends SpotifyController{
    public JsonObject getData(String uri) {
        return super.getData(uri);
    }

    @Override
    public List<SpotifyObject> format(JsonObject obj, int currentPage, int totalPages) {
        List<SpotifyObject> categories = new ArrayList<>();
        List<JsonElement> items;
        if(currentPage == -1 && totalPages == -1)
            items = obj.get("categories").getAsJsonObject().get("items").getAsJsonArray().asList();
        else
            items = getPages(obj.get("categories").getAsJsonObject().get("items").getAsJsonArray().asList(), Integer.parseInt(URLS.getLimit())).get(currentPage - 1);

        for (JsonElement category : items) {
            String id = category.getAsJsonObject().get("id").getAsString();
            String name = category.getAsJsonObject().get("name").getAsString();
            categories.add(new Category(id, name));
        }
        return categories;
    }

    public Category findCategoryByName(String name, int currentPage, int totalPages){
        List<SpotifyObject> categories = format(getData("/v1/browse/categories"), -1, -1);
        Category result = (Category) categories.stream()
                .filter(item -> item.getName().equals(name))
                .findFirst()
                .orElse(null);
        return result;
    }
}
