package com.r00t.becaapi.services;

import com.r00t.becaapi.models.requests.MessageRequestCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class StoryService {
    @Value("${storybot.service.uri}")
    private String storybotServiceURI;
    @Value("${storybot.service.token}")
    private String storybotServiceToken;
    @Autowired
    private RestTemplate restTemplate;

    public String getStoryFromAI(MessageRequestCredentials requestCredentials) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Token " + storybotServiceToken);

        Map<String, Object> map = new HashMap<>();
        map.put("message", requestCredentials.getMessage());
        map.put("size", 100);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(
                storybotServiceURI, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return (String) response.getBody().get("message");
        } else {
            return "pFF... Somethings went wrong... Sorry about that...";
        }
    }
}
