package ru.duckest.utils.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.entity.QuizLevelTypePair;
import ru.duckest.repository.QuizLevelTypePairs;

@Component
@RequiredArgsConstructor
public class TestDeleter {

    private final QuizLevelTypePairs levelTypePairs;

    public void delete(QuizLevelTypePair test) {
        levelTypePairs.delete(test);
    }
}
