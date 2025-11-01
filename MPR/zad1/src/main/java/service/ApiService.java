package service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import exception.ApiException;
import model.Employee;
import model.Position;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiService {

    private final HttpClient httpClient;
    private final Gson gson;

    public ApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     * Pobiera listę użytkowników z zewnętrznego API i zamienia na List<Employee>.
     * Każdemu przypisuje stanowisko PROGRAMISTA i bazową stawkę tego stanowiska.
     */
    public List<Employee> fetchEmployeesFromApi(String url) throws ApiException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ApiException("Błąd HTTP podczas pobierania danych: " + e.getMessage(), e);
        }

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new ApiException("Nieprawidłowy status odpowiedzi: " + response.statusCode());
        }

        String body = response.body();
        List<Employee> employees = new ArrayList<>();

        try {
            JsonElement root = gson.fromJson(body, JsonElement.class);
            if (!root.isJsonArray()) {
                throw new ApiException("Oczekiwano tablicy JSON z API");
            }
            JsonArray array = root.getAsJsonArray();
            for (JsonElement el : array) {
                if (!el.isJsonObject()) continue;
                JsonObject obj = el.getAsJsonObject();

                String name = safeGetString(obj, "name");
                String email = safeGetString(obj, "email");
                String company = "";
                if (obj.has("company") && obj.get("company").isJsonObject()) {
                    JsonObject comp = obj.getAsJsonObject("company");
                    company = safeGetString(comp, "name");
                }

                // rozdzielenie imienia i nazwiska (proste)
                String firstName = "";
                String lastName = "";
                if (name != null && !name.isEmpty()) {
                    String[] tokens = name.trim().split("\\s+");
                    if (tokens.length > 0) {
                        firstName = tokens[0];
                    }
                    if (tokens.length > 1) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 1; i < tokens.length; i++) {
                            if (i > 1) sb.append(" ");
                            sb.append(tokens[i]);
                        }
                        lastName = sb.toString();
                    }
                }
                String fullName = (firstName + (lastName.isEmpty() ? "" : " " + lastName)).trim();

                // przypisujemy stanowisko PROGRAMISTA i jego bazową stawkę
                Position position = Position.PROGRAMISTA;
                double salary = position.getBaseSalary();

                Employee e = new Employee(fullName, email, company, position, salary);
                employees.add(e);
            }
        } catch (com.google.gson.JsonSyntaxException ex) {
            throw new ApiException("Błąd parsowania JSON: " + ex.getMessage(), ex);
        }

        return employees;
    }

    private String safeGetString(JsonObject obj, String prop) {
        if (!obj.has(prop) || obj.get(prop).isJsonNull()) return "";
        try {
            return obj.get(prop).getAsString();
        } catch (Exception e) {
            return "";
        }
    }
}
