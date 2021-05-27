package ru.duckest.utils.test.level;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.entity.QuizLevel;
import ru.duckest.repository.QuizLevels;

@Component
@RequiredArgsConstructor
public class QuizLevelSaver {

    private final QuizLevels quizLevels;

    public QuizLevel save(QuizLevel level) {
        return quizLevels.save(level);
    }

    public QuizLevel save(String level) {
        return save(QuizLevel.builder().level(level).build());
    }

}
