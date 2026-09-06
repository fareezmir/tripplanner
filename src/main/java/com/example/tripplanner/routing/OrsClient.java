package com.example.tripplanner.routing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Map;

import java.util.List;

@Component
public class OrsClient {

    private final RestClient restClient;

    public OrsClient(@Value("${ors.api-key}") String apiKey) {
        this.restClient = RestClient.builder()
            .baseUrl("https://api.openrouteservice.org")
            .defaultHeader("Authorization", apiKey)
            .defaultHeader("Content-Type", "application/json")
            .build();
    }

    public double[][] durations(List<double[]> coordinates) {
        Map<String, Object> body = Map.of("locations", coordinates);
    
        OrsMatrixResponse response = restClient.post()
            .uri("/v2/matrix/foot-walking")
            .body(body)
            .retrieve()
            .body(OrsMatrixResponse.class);
    
        return response.durations();
    }
}