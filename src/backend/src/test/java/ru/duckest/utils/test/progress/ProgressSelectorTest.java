package ru.duckest.utils.test.progress;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.entity.Progress;
import ru.duckest.entity.ProgressId;
import ru.duckest.repository.Progresses;
import ru.duckest.utils.user.UserEntityTestFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.duckest.utils.test.jpa.QuizLevelTypePairTestFactory.getDummyTestEntity;

@SpringBootTest(classes = ProgressSelector.class)
class ProgressSelectorTest {

    @Autowired
    private ProgressSelector progressSelector;

    @MockBean
    private Progresses progresses;

    @Test
    @DisplayName("Получение списка прогресса по id пользователя")
    void getProgressListByUserId() {
        progressSelector.getProgressBy(UserEntityTestFactory.getValidUser().getId());
        verify(progresses).findAllByUserId(UserEntityTestFactory.getValidUser().getId());
    }

    @Test
    @DisplayName("Получение пустого списка прогресса по id пользователя, если пользователь не проходил тесты раньше")
    void gettingNonExistentProgressByUserId() {
        when(progresses.findAllByUserId(UserEntityTestFactory.getValidUser().getId())).thenReturn(Collections.emptyList());

        List<Progress> actual = progressSelector.getProgressBy(UserEntityTestFactory.getValidUser().getId());
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Получение нового прогресса по id пользователя и id теста, если пользователь не проходил тесты раньше")
    void gettingNonExistentProgressByUserIdAndTestId() {
        Progress expected = Progress.builder().userId(UserEntityTestFactory.getValidUser().getId()).levelTypeId(getDummyTestEntity().getId()).build();

        Progress actual = progressSelector.getProgressByUserAndLevelTypePairOrReturnNew(UserEntityTestFactory.getValidUser().getId(),
                                                                                        getDummyTestEntity().getId());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Получение прогресса по id пользователя и id теста")
    void gettingProgressByUserIdAndTestId() {
        Progress expected = Progress.builder().userId(UserEntityTestFactory.getValidUser().getId()).levelTypeId(getDummyTestEntity().getId()).build();

        when(progresses.findById(new ProgressId(expected.getUserId(), expected.getLevelTypeId()))).thenReturn(Optional.of(expected));

        Progress actual = progressSelector.getProgressByUserAndLevelTypePairOrReturnNew(expected.getUserId(), expected.getLevelTypeId());
        assertThat(actual).isEqualTo(expected);
    }

}