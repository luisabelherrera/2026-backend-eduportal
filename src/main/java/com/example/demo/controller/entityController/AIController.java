package com.example.demo.controller.entityController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ia")
public class AIController {

    @Value("${app.gemini.api-key}")
    private String geminiApiKey;

    @Value("${app.youtube.api-key}")
    private String youtubeApiKey;

    @Value("${app.gemini.api-url}")
    private String geminiApiUrl;

    @Value("${app.youtube.api-url}")
    private String youtubeApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Proxy para llamadas a Gemini API Frontend envía el prompt, backend maneja
     * la key de forma segura
     */
    @PostMapping("/gemini/generate")
    public Object generateWithGemini(@RequestBody Map<String, Object> request) {
        try {
            String prompt = (String) request.get("prompt");
            String model = (String) request.getOrDefault("model", "gemini-2.0-flash");

            if (prompt == null || prompt.isEmpty()) {
                return Map.of("error", "Prompt is required");
            }

            // Construir URL con API key segura (en backend, no expuesta)
            String safeModel = (model == null || model.isBlank()) ? "gemini-2.0-flash" : model;
            if ("gemini-1.5-flash-latest".equalsIgnoreCase(safeModel)
                    || "gemini-1.5-flash".equalsIgnoreCase(safeModel)) {
                safeModel = "gemini-2.0-flash";
            }
            String baseUrl = geminiApiUrl.replaceAll("/models/[^:]+:generateContent$", "/models/" + safeModel + ":generateContent");
            String url = String.format("%s?key=%s", baseUrl, geminiApiKey);

            // Preparar request a Gemini
            Map<String, Object> geminiRequest = new HashMap<>();
            Map<String, Object> content = new HashMap<>();
            Map<String, String> part = new HashMap<>();
            part.put("text", prompt);
            content.put("parts", new Object[]{part});
            geminiRequest.put("contents", new Object[]{content});

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(geminiRequest, headers);

            return restTemplate.postForObject(url, entity, Object.class);

        } catch (Exception e) {
            return Map.of("error", "Error al conectar con Gemini: " + e.getMessage());
        }
    }

    /**
     * Proxy para búsquedas en YouTube Frontend envía query, backend maneja la
     * key
     */
    @GetMapping("/youtube/search")
    public Object searchYoutube(@RequestParam String q,
            @RequestParam(defaultValue = "5") int maxResults) {
        try {
            if (q == null || q.isEmpty()) {
                return Map.of("error", "Search query is required");
            }

            String url = String.format("%s?key=%s&q=%s&maxResults=%d&part=snippet",
                    youtubeApiUrl, youtubeApiKey, q, maxResults);

            return restTemplate.getForObject(url, Object.class);

        } catch (Exception e) {
            return Map.of("error", "Error al conectar con YouTube: " + e.getMessage());
        }
    }

    /**
     * Health check para verificar que las keys están configuradas
     */
    @GetMapping("/health")
    public Object healthCheck() {
        return Map.of(
                "gemini_configured", geminiApiKey != null && !geminiApiKey.isEmpty(),
                "youtube_configured", youtubeApiKey != null && !youtubeApiKey.isEmpty()
        );
    }
}
