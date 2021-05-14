package ru.duckest.duckest.utils.test.description;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.duckest.entity.QuizDescription;
import ru.duckest.duckest.entity.QuizLevelTypePair;
import ru.duckest.duckest.repository.Descriptions;

@Component
@RequiredArgsConstructor
public class DescriptionSaver {

    private final Descriptions descriptions;

    public QuizDescription save(QuizDescription description) {
        return descriptions.save(description);
    }

    public QuizDescription save(String description, QuizLevelTypePair levelTypePair) {
        return save(QuizDescription.builder().description(description).levelTypePairId(levelTypePair.getId()).build());
    }

}
