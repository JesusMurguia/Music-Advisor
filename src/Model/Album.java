package Model;

public class Album extends SpotifyObject{
    private String url;
    private String[] artists;

    public Album(String id, String name, String url, String[] artists) {
        super(id, name);
        this.url = url;
        this.artists = artists;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getArtists() {
        return artists;
    }

    public void setArtists(String[] artists) {
        this.artists = artists;
    }

}
