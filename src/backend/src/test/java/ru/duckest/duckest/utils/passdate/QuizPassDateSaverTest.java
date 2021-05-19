package ru.duckest.duckest.utils.passdate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.duckest.entity.QuizPassDate;
import ru.duckest.duckest.repository.QuizPassDates;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static ru.duckest.duckest.utils.test.jpa.QuizLevelTypePairTestFactory.getDummyTestEntity;
import static ru.duckest.duckest.utils.user.UserEntityTestFactory.getValidUser;

@SpringBootTest(classes = QuizPassDateSaver.class)
class QuizPassDateSaverTest {

    @Autowired
    private QuizPassDateSaver quizPassDateSaver;

    @MockBean
    private QuizPassDates quizPassDates;

    @Test
    @DisplayName("При сохранении даты прохождения выбирается текущий день")
    void passDateIsAlwaysTodayWhenSaving() {
        UUID userId = getValidUser().getId();
        UUID testId = getDummyTestEntity().getId();

        quizPassDateSaver.save(userId, testId);
        verify(quizPassDates).save(any(QuizPassDate.class));
    }

}