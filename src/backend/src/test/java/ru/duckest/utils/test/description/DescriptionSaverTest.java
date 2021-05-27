package ru.duckest.utils.test.description;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.entity.QuizDescription;
import ru.duckest.repository.Descriptions;
import ru.duckest.utils.test.jpa.QuizDescriptionTestFactory;
import ru.duckest.utils.test.jpa.QuizLevelTypePairTestFactory;

import static org.mockito.Mockito.verify;
import static ru.duckest.utils.test.Constants.DUMMY_TEST_DESCRIPTION;

@SpringBootTest(classes = DescriptionSaver.class)
class DescriptionSaverTest {

    @Autowired
    private DescriptionSaver descriptionSaver;

    @MockBean
    private Descriptions descriptions;

    @Test
    @DisplayName("Сущность описания успешно сохраняется")
    void descriptionEntityCanBeSaved() {
        QuizDescription dummyQuizDescription = QuizDescriptionTestFactory.getDummyQuizDescription();
        dummyQuizDescription.setLevelTypePair(QuizLevelTypePairTestFactory.getDummyTestEntity());

        descriptionSaver.save(dummyQuizDescription);
        verify(descriptions).save(dummyQuizDescription);
    }

    @Test
    @DisplayName("Описание успешно сохраняется, если передана строка")
    void descriptionCanBeSavedWhenStringPassed() {
        QuizDescription dummyQuizDescription = QuizDescriptionTestFactory.getDummyQuizDescription();
        dummyQuizDescription.setLevelTypePair(QuizLevelTypePairTestFactory.getDummyTestEntity());

        descriptionSaver.save(DUMMY_TEST_DESCRIPTION, QuizLevelTypePairTestFactory.getDummyTestEntity());
        verify(descriptions).save(dummyQuizDescription);
    }

}