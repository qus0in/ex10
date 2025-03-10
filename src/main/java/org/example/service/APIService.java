package org.example.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.model.APIParam;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIService {
    private static final APIService instance = new APIService();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Dotenv dotenv = Dotenv.load();
    private final String groqToken;
    private final String togetherToken;

    public static APIService getInstance() {
        return instance;
    }

    private APIService() {
        groqToken = dotenv.get("GROQ_KEY");
        togetherToken = dotenv.get("TOGETHER_KEY");
    }

    public String callAPI(APIParam apiParam) throws Exception {
        String url;
        String token;
        switch (apiParam.model().platform) {
            case GROQ -> {
                url = "https://api.groq.com/openai/v1/chat/completions";
                token = groqToken;
            }
            case TOGETHER -> {
                url = "https://api.together.xyz/v1/chat/completions";
                token = togetherToken;
            }
            default -> throw new Exception("Unsupported platform");
        }

        String body = """
                {
                         "messages": [
                           {
                             "role": "system",
                             "content": "only korean character"
                           },
                           {
                             "role": "user",
                             "content": "%s"
                           }
                         ],
                         "model": "%s"
                       }
                """.formatted(apiParam.prompt(), apiParam.model().name);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Authorization", "Bearer %s".formatted(token))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
