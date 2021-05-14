package ru.duckest.duckest.utils.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.duckest.entity.QuizLevelTypePair;
import ru.duckest.duckest.repository.QuizLevelTypePairs;
import ru.duckest.duckest.utils.test.level.QuizLevelSaver;
import ru.duckest.duckest.utils.test.level.QuizLevelSelector;
import ru.duckest.duckest.utils.test.type.QuizTypeSaver;
import ru.duckest.duckest.utils.test.type.QuizTypeSelector;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TestSelector {

    private final QuizLevelTypePairs quizLevelTypePairs;
    private final QuizLevelSaver quizLevelSaver;
    private final QuizLevelSelector quizLevelSelector;
    private final QuizTypeSaver quizTypeSaver;
    private final QuizTypeSelector quizTypeSelector;

    public QuizLevelTypePair findByLevelAndTypeOrThrow(String level, String type) {
        var quizLevel = quizLevelSelector.findBy(level).orElseThrow();
        var quizType = quizTypeSelector.findBy(type).orElseThrow();
        return quizLevelTypePairs.findByQuizLevelAndQuizType(quizLevel, quizType).orElseThrow();
    }


    public Optional<QuizLevelTypePair> findByLevelAndType(String level, String type) {
        var quizLevel = quizLevelSelector.findBy(level).orElse(quizLevelSaver.save(level));
        var quizType = quizTypeSelector.findBy(type).orElse(quizTypeSaver.save(type));
        return quizLevelTypePairs.findByQuizLevelAndQuizType(quizLevel, quizType);
    }

}
