package Model;

public class Playlist extends SpotifyObject{
    private String url;

    public Playlist(String id, String name, String url) {
        super(id,name);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
