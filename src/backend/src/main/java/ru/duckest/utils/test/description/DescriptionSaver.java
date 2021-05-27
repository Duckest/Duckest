package ru.duckest.utils.test.description;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.entity.QuizDescription;
import ru.duckest.entity.QuizLevelTypePair;
import ru.duckest.repository.Descriptions;

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
