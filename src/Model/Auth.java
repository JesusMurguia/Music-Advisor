package Model;


public class Auth {
    public static Boolean isAuth = false;
    private static String accessToken;


    public static Boolean isAuth() {
        return isAuth;
    }

    public static String getAccessToken(){
        return accessToken;
    }

    public static void setAccessToken(String token){
        accessToken = token;
    }

    public static void setAuth(Boolean auth) {
        isAuth = auth;
    }

}
