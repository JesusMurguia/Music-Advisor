package MusicAdvisor;

import Controller.*;
import Model.URLS;

import java.util.Scanner;

public class Main {
    static AuthController authController = new AuthController();
    public static void main(String[] args){
        String access = args.length > 1 && args[0].equals("-access") ? args[1] : "https://accounts.spotify.com";
        String resource = args.length > 3 && args[2].equals("-resource") ? args[3] : "https://api.spotify.com";
        String limit = args.length > 5 && args[4].equals("-page") ? args[5] : "2";
        URLS.setAccess(access);
        URLS.setResource(resource);
        URLS.setLimit(limit);
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();

        CommandExecuter exec = new CommandExecuter();

        while(!command.equals("exit!")){
            if(!authController.isAuth()){
                if(command.equals("auth")){
                    if(!authController.isAuth())
                        authController.handleAuth();
                }else {
                    authController.accessMessage();
                }
            }else {
                switch (command) {
                    case "featured" -> exec.setStrategy(new FeaturedStrategy());
                    case "new" -> exec.setStrategy(new NewReleasesStrategy());
                    case "categories" -> exec.setStrategy(new CategoryStrategy());
                    case "playlists" -> exec.setStrategy(new PlaylistStrategy(scanner.nextLine()));
                    case "next" -> exec.nextPage();
                    case "prev" -> exec.prevPage();
                    default -> System.out.println("Command not recognized");
                }
                if(exec.hasStrategy()) exec.handle();
            }
            command = scanner.next();
        }
    }
}
