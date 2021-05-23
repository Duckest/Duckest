package ru.duckest.duckest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestTypeProgressDto {

    @JsonProperty("test_type")
    private String testType;

    @Default
    @JsonProperty("test_levels")
    private List<TestLevelProgressDto> testLevelProgressDtos = new ArrayList<>();

    @JsonProperty("is_level_completed")
    private boolean levelCompleted;

    @JsonProperty("image_url")
    private String imageUrl;

}
