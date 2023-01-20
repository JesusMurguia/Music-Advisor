package Model;

public class URLS {
    private static String access;
    private static String resource;
    private static String limit;

    public static void setAccess(String access) {
        URLS.access = access;
    }

    public static void setResource(String resource) {
        URLS.resource = resource;
    }

    public static String getAccess() {
        return access;
    }

    public static String getResource() {
        return resource;
    }

    public static String getLimit() {
        return limit;
    }

    public static void setLimit(String limit) {
        URLS.limit = limit;
    }
}
