package com.example.SpringAIStarter.configs;

import org.springframework.ai.chat.memory.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMemoryConfig {
    
    @Bean
    public ChatMemory ChatMemory(){
        return MessageWindowChatMemory.builder()
                    .maxMessages(10)
                    .build();
    }
}
