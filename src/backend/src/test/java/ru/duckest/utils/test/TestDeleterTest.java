package ru.duckest.utils.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.entity.QuizLevelTypePair;
import ru.duckest.repository.QuizLevelTypePairs;
import ru.duckest.utils.test.jpa.QuizLevelTypePairTestFactory;

import static org.mockito.Mockito.verify;


@SpringBootTest(classes = TestDeleter.class)
class TestDeleterTest {

    @Autowired
    private TestDeleter testDeleter;

    @MockBean
    private QuizLevelTypePairs levelTypePairs;

    @Test
    @DisplayName("Вызывается удаление теста")
    void testCanBeDeleted() {
        QuizLevelTypePair expected = QuizLevelTypePairTestFactory.getDummyTestEntity();
        testDeleter.delete(expected);
        verify(levelTypePairs).delete(expected);
    }
}