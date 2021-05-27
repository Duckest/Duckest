package ru.duckest.utils.test.level;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.entity.QuizLevel;
import ru.duckest.repository.QuizLevels;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuizLevelSelector {

    private final QuizLevels quizLevels;

    public Optional<QuizLevel> findBy(String level) {
        return quizLevels.findById(level);
    }

}
