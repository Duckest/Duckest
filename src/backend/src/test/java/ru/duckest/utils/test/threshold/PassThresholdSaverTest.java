package ru.duckest.utils.test.threshold;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.entity.PassThreshold;
import ru.duckest.entity.QuizLevelTypePair;
import ru.duckest.repository.PassThresholds;
import ru.duckest.utils.test.jpa.PassThresholdTestFactory;
import ru.duckest.utils.test.jpa.QuizLevelTypePairTestFactory;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = PassThresholdSaver.class)
class PassThresholdSaverTest {

    @Autowired
    private PassThresholdSaver passThresholdSaver;

    @MockBean
    private PassThresholds passThresholds;

    @Test
    @DisplayName("Сущность порога прохождения успешно сохраняется")
    void passThresholdCanBeSaved() {
        PassThreshold dummyPassThreshold = PassThresholdTestFactory.getDummyPassThreshold();

        passThresholdSaver.save(dummyPassThreshold);
        verify(passThresholds).save(dummyPassThreshold);
    }

    @Test
    @DisplayName("Сущность порога прохождения успешно сохраняется, если передать значение")
    void passThresholdCanBeSavedWhenIntegerIsPassed() {
        QuizLevelTypePair dummyTestEntity = QuizLevelTypePairTestFactory.getDummyTestEntity();
        PassThreshold dummyPassThreshold = PassThresholdTestFactory.getDummyPassThreshold();
        dummyPassThreshold.setLevelTypePair(dummyTestEntity);

        passThresholdSaver.save(dummyPassThreshold.getThreshold(), dummyTestEntity);
        verify(passThresholds).save(dummyPassThreshold);
    }
}