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
public class TestCreationDto {

    @JsonProperty("test_type")
    private String testType;

    @JsonProperty("test_level")
    private String testLevel;

    @JsonProperty("description")
    private String description;

    @Default
    @JsonProperty("threshold")
    private Integer threshold = 80;

    @Default
    @JsonProperty("questions")
    private List<QuestionDto> questions = new ArrayList<>();

}
