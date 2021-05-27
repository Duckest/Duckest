package ru.duckest.utils.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.entity.QuizLevelTypePair;
import ru.duckest.repository.QuizLevelTypePairs;
import ru.duckest.utils.test.level.QuizLevelSelector;
import ru.duckest.utils.test.type.QuizTypeSelector;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TestSelector {

    private final QuizLevelTypePairs quizLevelTypePairs;
    private final QuizLevelSelector quizLevelSelector;
    private final QuizTypeSelector quizTypeSelector;

    public QuizLevelTypePair findByLevelAndTypeOrThrow(String level, String type) {
        var quizLevel = quizLevelSelector.findBy(level).orElseThrow();
        var quizType = quizTypeSelector.findBy(type).orElseThrow();
        return quizLevelTypePairs.findByQuizLevelAndQuizType(quizLevel, quizType).orElseThrow();
    }


    public Optional<QuizLevelTypePair> findByLevelAndType(String level, String type) {
        var quizLevel = quizLevelSelector.findBy(level);
        var quizType = quizTypeSelector.findBy(type);
        if (quizLevel.isEmpty() || quizType.isEmpty()) {
            return Optional.empty();
        }
        return quizLevelTypePairs.findByQuizLevelAndQuizType(quizLevel.get(), quizType.get());
    }

}
