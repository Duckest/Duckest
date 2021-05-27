package ru.duckest.dto;

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
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {

    @JsonProperty("description")
    private String description;

    @JsonProperty("total_questions")
    private int totalQuestions;

    @JsonProperty("test")
    @Default
    private List<QuestionDto> questions = new ArrayList<>();
}
