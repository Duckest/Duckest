package ru.duckest.utils.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.entity.QuizLevel;
import ru.duckest.entity.QuizLevelTypePair;
import ru.duckest.entity.QuizType;
import ru.duckest.repository.QuizLevelTypePairs;
import ru.duckest.utils.test.level.QuizLevelSaver;
import ru.duckest.utils.test.level.QuizLevelSelector;
import ru.duckest.utils.test.type.QuizTypeSaver;
import ru.duckest.utils.test.type.QuizTypeSelector;

@Component
@RequiredArgsConstructor
public class TestSaver {

    private final QuizLevelTypePairs quizLevelTypePairs;
    private final QuizLevelSaver quizLevelSaver;
    private final QuizLevelSelector quizLevelSelector;
    private final QuizTypeSaver quizTypeSaver;
    private final QuizTypeSelector quizTypeSelector;

    public QuizLevelTypePair save(QuizLevel level, QuizType type) {
        return quizLevelTypePairs.save(QuizLevelTypePair.builder().quizLevel(level).quizType(type).build());
    }

    public QuizLevelTypePair save(String level, String type) {
        var quizLevel = quizLevelSelector.findBy(level).orElse(quizLevelSaver.save(level));
        var quizType = quizTypeSelector.findBy(type).orElse(quizTypeSaver.save(type));
        return save(quizLevel, quizType);
    }

}
