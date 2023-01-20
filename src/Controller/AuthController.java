package Controller;

import Model.Auth;
import Model.URLS;
import View.View;

import java.io.IOException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthController {
    Auth auth;

    public AuthController(){
        auth = new Auth();
    }
    public String getLink() {
        return URLS.getAccess() + "/authorize?client_id="+System.getenv("CLIENT_ID")+"&redirect_uri=http://localhost:8080&response_type=code";
    }

    public static String getEncodedString() {
        return Base64.getEncoder().encodeToString((System.getenv("CLIENT_ID")+":"+System.getenv("CLIENT_SECRET")).getBytes());
    }

    public static boolean hasCode(String code) {
        if (code == null) return false;
        Pattern pattern = Pattern.compile("/?code=(.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(code);
        return matcher.find();
    }

    public Boolean isAuth() {
        return auth.isAuth();
    }

    public String getAccessToken(){
        return auth.getAccessToken();
    }

    public void setAccessToken(String token){
        auth.setAccessToken(token);
    }

    public void setAuth(Boolean value) { auth.setAuth(value); }

    public void accessMessage(){ View.getInstance().print("Please, provide access for application.");}

    public void handleAuth() {
        View.getInstance().print("use this link to request the access code:"+"\n"+getLink());
        ServerController serverController = new ServerController();
        try {
            serverController.start();
        } catch (IOException  e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
