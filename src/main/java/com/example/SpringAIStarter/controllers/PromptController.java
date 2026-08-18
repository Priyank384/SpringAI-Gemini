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

    @GetMapping("/few-shot")
    public String fewShot(@RequestParam String message){
        String result = chatClient.prompt()
                            .user(u -> 
                                u.text("""
                                    Tell me the time complexity of the algorithm based on the algorithm 
                                    name given. Only respond with the time complexity and nothing else.

                                    Examples:
                                    Text: Merge Sort
                                    Time Complexity: O(n log n)
                                    Text: Quick Sort
                                    Time Complexity: O(n log n)
                                    Text: Bubble Sort
                                    Time Complexity: O(n²)
                                    Text: Binary Search
                                    Time Complexity: O(log n)
                                    Text: Insertion Sort
                                    Time Complexity: O(n²)


                                    Text:{algorithmName}
                                    """)
                                .param("algorithmName", message))
                            .call()
                            .content();

        return result;
    }

}
