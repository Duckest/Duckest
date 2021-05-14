package ru.duckest.duckest.utils.test.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.duckest.entity.QuizType;
import ru.duckest.duckest.repository.QuizTypes;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuizTypeSelector {

    private final QuizTypes quizTypes;

    public Optional<QuizType> findBy(String type) {
        return quizTypes.findById(type);
    }

}