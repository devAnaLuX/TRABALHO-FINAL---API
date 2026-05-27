package PF.SerratecFlix.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final RestClient restClient;

    public GroqService() {
        this.restClient = RestClient.create();
    }

    public String obterRecomendacoes(String prompt) {
        Map<String, Object> body = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", new Object[]{
                        Map.of("role", "user", "content", prompt)
                }
        );

        Map response = restClient.post()
                .uri("https://api.groq.com/openai/v1/chat/completions")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .body(body)
                .retrieve()
                .body(Map.class);

        var choices = (List) response.get("choices");
        var message = (Map) ((Map) choices.get(0)).get("message");
        return (String) message.get("content");
    }
}
