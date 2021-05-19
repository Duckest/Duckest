package ru.duckest.duckest.utils.test.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.duckest.entity.QuizType;
import ru.duckest.duckest.repository.QuizTypes;

@Component
@RequiredArgsConstructor
public class QuizTypeSaver {

    private final QuizTypes quizTypes;

    public QuizType save(QuizType quizType) {
        return quizTypes.save(quizType);
    }

    public QuizType save(String type) {
        return save(QuizType.builder().type(type).build());
    }

}
