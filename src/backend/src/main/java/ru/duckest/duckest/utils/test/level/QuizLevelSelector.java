package ru.duckest.duckest.utils.test.level;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.duckest.entity.QuizLevel;
import ru.duckest.duckest.repository.QuizLevels;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuizLevelSelector {

    private final QuizLevels quizLevels;

    public Optional<QuizLevel> findBy(String level) {
        return quizLevels.findById(level);
    }

}
