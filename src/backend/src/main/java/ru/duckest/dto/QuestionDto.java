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
public class QuestionDto {

    @JsonProperty("question")
    private String question;

    @Default
    @JsonProperty("answers")
    private List<String> answers = new ArrayList<>();

    @JsonProperty("right_answer_index")
    private int rightAnswerIndex;

}
