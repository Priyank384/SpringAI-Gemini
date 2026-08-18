package com.example.SpringAIStarter.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prompt")
public class PromptController {
    private final ChatClient chatClient;

    public PromptController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @GetMapping("/zero-shot")
    public String zeroShot(@RequestParam String message){
        String result = chatClient.prompt()
                            .user(u -> 
                                u.text("""
                                    Classify the sentiment of the following text as exactly one of:
                                    positive, negative, or neutral. Only respond with the sentiment label.
                                    Nothing else.

                                    Text:{message}
                                    """)
                                .param("message", message))
                            .call()
                            .content();

        return result;
    }
}
