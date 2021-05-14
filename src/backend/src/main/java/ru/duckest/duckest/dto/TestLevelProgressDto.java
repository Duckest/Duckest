package ru.duckest.duckest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestLevelProgressDto {

    @JsonProperty("test_level")
    private String testLevel;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("is_test_completed")
    private boolean testCompleted;
}