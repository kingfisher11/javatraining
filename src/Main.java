// Import Library Section
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        // Main method
        // Create an HTTP server running on port 8080
        // InetSocketAddress(8080) → binds the server to localhost:8080
        // The second parameter (0) means default backlog (max waiting connections)
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/grade", new GradeHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server running at http://localhost:8080");

    }

    static class GradeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // CORS Headers
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");

            // Handle pre-flight OPTIONS request (sent automatically by browser)
            // If browser sends OPTIONS, respond with empty 204 and return
            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }


            // ==============================================================
            // PROCESS GET REQUEST (Browser calls this when fetching grade)
            // ==============================================================

            if ("GET".equals(exchange.getRequestMethod())) {

                String query = exchange.getRequestURI().getQuery(); // name=ALIAH&score=70

                if (query == null) {
                    String error = "{ \"error\": \"Missing parameters\" }";
                    exchange.sendResponseHeaders(400, error.length());
                    exchange.getResponseBody().write(error.getBytes());
                    exchange.close();
                    return;
                }

                // Split parameter by '&', eg: grade?name=John&score=85
                // ["name=John", "score=85"]
                String[] parts = query.split("&");

                // Extract "John" → parts[0] = "name=John"
                String name = parts[0].split("=")[1];
                int score = Integer.parseInt(parts[1].split("=")[1]);

                String grade;
                if (score >= 80 && score <= 100) grade = "A";
                else if (score >= 60 && score < 80) grade = "B";
                else if (score >= 40 && score < 60) grade = "C";
                else grade = "Fail";

                String json = "{ \"name\": \"" + name + "\", \"grade\": \"" + grade + "\" }";

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, json.length());

                OutputStream os = exchange.getResponseBody();
                os.write(json.getBytes());
                os.close();
            }
        }
    }
}
