package ru.duckest.duckest.utils.test.progress;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.duckest.entity.Progress;
import ru.duckest.duckest.repository.Progresses;

import static org.mockito.Mockito.verify;
import static ru.duckest.duckest.utils.test.jpa.ProgressTestFactory.getDummyProgress;

@SpringBootTest(classes = ProgressSaver.class)
class ProgressSaverTest {

    @Autowired
    private ProgressSaver progressSaver;

    @MockBean
    private Progresses progresses;

    @Test
    @DisplayName("Прогресс пользователя успешно сохраняется")
    void userProgressCanBeSaved() {
        Progress expectedToSave = getDummyProgress();

        progressSaver.save(expectedToSave);
        verify(progresses).save(expectedToSave);
    }

}