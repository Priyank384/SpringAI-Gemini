package com.example.SpringAIStarter.controllers;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringAIStarter.dtos.MovieRecommendation;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder, ChatMemory chatMemory){
        this.chatClient = builder
                            .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                            .build();
    }


    @GetMapping("/simple")
    public String simpleChat(@RequestParam String message){
        String response = chatClient.prompt()
                            .user(message)
                            .call()
                            .content();
        return response;
    }

    @GetMapping("/movies")
    public List<MovieRecommendation> getMoviewRecommendation(@RequestParam String genre, 
                                                                @RequestParam int count,
                                                                @RequestParam String conversationId){
        return chatClient.prompt()
                .user(u ->
                        u.text("""
                            Recommend exactly {count} movies in the {genre} genre. For each movie provide 
                            the accurate title, description, genre, rating, year and actors. The rating should
                            be a number between 0 and 10, and the year should be the year of the movie released. 
                            The data should be absolutely accurate. Every time even the genre is same, the movie
                            be different.
                            """).param("genre", genre)
                                .param("count", String.valueOf(count))
                    )
                .advisors(a -> 
                            a.param(ChatMemory.CONVERSATION_ID, conversationId)
                        )
                .call()
                .entity(new ParameterizedTypeReference<List<MovieRecommendation>>(){});
    }
}
