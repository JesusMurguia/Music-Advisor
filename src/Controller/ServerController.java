package Controller;

import Model.URLS;
import View.View;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerController {
    private static HttpServer server;
    private static AuthController authController;

    public ServerController(){
        authController = new AuthController();
    }

    public void start() throws IOException {
        server = HttpServer.create();
        server.bind(new InetSocketAddress(8080),0);
        server.createContext("/", new AuthHandler());
        server.start();
        View.getInstance().print("waiting for code...");

        while (!authController.isAuth()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        server.stop(1);
    }

    static class AuthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            if (authController.hasCode(query)) {
                View.getInstance().print("code received");
                View.getInstance().print("making http request for access_token...");
                HttpRequest request = HttpRequest.newBuilder()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Authorization", "Basic " + authController.getEncodedString())
                        .uri(URI.create(URLS.getAccess() + "/api/token"))
                        .POST(HttpRequest.BodyPublishers.ofString(query + "&redirect_uri=http://localhost:8080&response_type=code" + "&grant_type=authorization_code"))
                        .build();

                HttpClient httpClient = HttpClient.newHttpClient();

                try {
                    HttpResponse<String> response = httpClient.send(
                            request, HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() == 200) {
                        View.getInstance().print("response:");
                        JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
                        String accessToken = jo.get("access_token").getAsString();
                        authController.setAccessToken(accessToken);
                        View.getInstance().print(response.body());
                        View.getInstance().print("---SUCCESS---");
                        String res = "Got the code. Return back to your program.";
                        exchange.sendResponseHeaders(200, res.length());
                        exchange.getResponseBody().write(res.getBytes());
                        exchange.getResponseBody().close();
                        authController.setAuth(true);
                        server.stop(1);
                    } else {
                        String res = "Could not verify the code. Try again.";
                        exchange.sendResponseHeaders(400, res.length());
                        exchange.getResponseBody().write(res.getBytes());
                        exchange.getResponseBody().close();
                    }
                } catch (Exception e) {
                    View.getInstance().print(e);
                }
            }
            String res = "Authorization code not found. Try again.";
            exchange.sendResponseHeaders(400, res.length());
            exchange.getResponseBody().write(res.getBytes());
            exchange.getResponseBody().close();
        }
    }
}
