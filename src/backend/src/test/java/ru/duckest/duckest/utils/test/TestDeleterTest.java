package ru.duckest.duckest.utils.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.duckest.entity.QuizLevelTypePair;
import ru.duckest.duckest.repository.QuizLevelTypePairs;

import static org.mockito.Mockito.verify;
import static ru.duckest.duckest.utils.test.jpa.QuizLevelTypePairTestFactory.getDummyTestEntity;


@SpringBootTest(classes = TestDeleter.class)
class TestDeleterTest {

    @Autowired
    private TestDeleter testDeleter;

    @MockBean
    private QuizLevelTypePairs levelTypePairs;

    @Test
    @DisplayName("Вызывается удаление теста")
    void testCanBeDeleted() {
        QuizLevelTypePair expected = getDummyTestEntity();
        testDeleter.delete(expected);
        verify(levelTypePairs).delete(expected);
    }
}