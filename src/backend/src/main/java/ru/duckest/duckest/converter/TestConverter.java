package ru.duckest.duckest.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.duckest.dto.TestDto;
import ru.duckest.duckest.entity.QuizLevelTypePair;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TestConverter {

    private final QuestionConverter questionConverter;

    public TestDto convert(QuizLevelTypePair quizLevelTypePair) {
        return TestDto.builder()
                .description(quizLevelTypePair.getDescription().getDescription())
                .questions(quizLevelTypePair.getQuestions().stream().map(questionConverter::convert).collect(Collectors.toList()))
                .totalQuestions(quizLevelTypePair.getQuestions().size())
                .build();
    }

}
