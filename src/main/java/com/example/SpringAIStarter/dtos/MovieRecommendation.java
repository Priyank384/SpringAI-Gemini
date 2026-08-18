package com.example.SpringAIStarter.dtos;

import java.util.List;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieRecommendation {
    String title;
    String description;
    String genre;
    String rating;
    String year;
    List<String> actors;
}

