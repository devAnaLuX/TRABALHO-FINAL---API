package PF.SerratecFlix.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestClient restClient;

    public GeminiService() {
        this.restClient = RestClient.create();
    }

    public String obterRecomendacoes(String prompt){
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey;

        Map<String, Object> body = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        })
                }
        );

        Map response = restClient.post()
                .uri(url)
                .header("Content-Type", "application/json")
                .body(body)
                .retrieve()
                .body(Map.class);

        var candidates = (java.util.List) response.get("candidates");
        var content = (Map) ((Map) candidates.get(0)).get("content");
        var parts = (java.util.List) content.get("parts");
        return (String) ((Map) parts.get(0)).get("text");
    }
}
