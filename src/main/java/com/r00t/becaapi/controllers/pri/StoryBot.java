package com.r00t.becaapi.controllers.pri;

import com.r00t.becaapi.models.requests.MessageRequestCredentials;
import com.r00t.becaapi.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/story-bot")
public class StoryBot {
    @Autowired
    private StoryService storyService;

    @PostMapping
    public ResponseEntity<?> getStory(
            @Valid @RequestBody MessageRequestCredentials requestCredentials) {
        return ResponseEntity.ok().body(
                Map.of("story", storyService.getStoryFromAI(requestCredentials)));
    }
}
