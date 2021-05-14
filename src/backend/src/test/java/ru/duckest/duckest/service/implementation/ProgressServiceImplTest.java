package ru.duckest.duckest.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.duckest.converter.ProgressConverter;
import ru.duckest.duckest.dto.ResultDto;
import ru.duckest.duckest.dto.TestTypeProgressDto;
import ru.duckest.duckest.entity.Progress;
import ru.duckest.duckest.entity.QuizLevelTypePair;
import ru.duckest.duckest.entity.User;
import ru.duckest.duckest.service.ProgressService;
import ru.duckest.duckest.utils.passdate.QuizPassDateSaver;
import ru.duckest.duckest.utils.test.TestSelector;
import ru.duckest.duckest.utils.test.progress.ProgressSaver;
import ru.duckest.duckest.utils.test.progress.ProgressSelector;
import ru.duckest.duckest.utils.user.UserSelector;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.duckest.duckest.utils.test.dto.SaveResultDtoTestFactory.getDummyResultDto;
import static ru.duckest.duckest.utils.test.dto.SaveResultDtoTestFactory.getDummySaveResultDto;
import static ru.duckest.duckest.utils.test.dto.TestTypeProgressDtoTestFactory.getDummyTestTypeProgressDto;
import static ru.duckest.duckest.utils.test.jpa.ProgressTestFactory.getDummyProgress;
import static ru.duckest.duckest.utils.test.jpa.QuizLevelTypePairTestFactory.getDummyTestEntity;
import static ru.duckest.duckest.utils.user.Constants.INVALID_EMAIL;
import static ru.duckest.duckest.utils.user.Constants.VALID_EMAIL;
import static ru.duckest.duckest.utils.user.UserEntityTestFactory.getValidUser;

@SpringBootTest(classes = ProgressServiceImpl.class)
class ProgressServiceImplTest {

    @Autowired
    private ProgressService progressService;

    @MockBean
    private TestSelector testSelector;
    @MockBean
    private UserSelector userSelector;
    @MockBean
    private ProgressSaver progressSaver;
    @MockBean
    private ProgressSelector progressSelector;
    @MockBean
    private ProgressConverter progressConverter;
    @MockBean
    private QuizPassDateSaver quizPassDateSaver;

    @BeforeEach
    void initSelectors() {
        ResultDto dummyResultDto = getDummySaveResultDto();
        QuizLevelTypePair dummyTestEntity = getDummyTestEntity();
        Progress oldProgressWithWorseResult = getDummyProgress();
        oldProgressWithWorseResult.setProgressValue(oldProgressWithWorseResult.getProgressValue() - 1);

        when(userSelector.getUserBy(dummyResultDto.getEmail())).thenReturn(getValidUser());
        when(testSelector.findByLevelAndTypeOrThrow(dummyResultDto.getTestLevel(), dummyResultDto.getTestType())).thenReturn(
                dummyTestEntity);

        when(progressSelector.getProgressByUserAndLevelTypePairOrReturnNew(getValidUser().getId(), dummyTestEntity.getId())).thenReturn(
                oldProgressWithWorseResult);
    }

    @Test
    @DisplayName("Поиск пары языка программирования и уровня вызывает data layer")
    void levelTypePairSearchCallDataLayer() {
        ResultDto dummyResultDto = getDummySaveResultDto();

        progressService.saveResults(dummyResultDto);
        verify(testSelector).findByLevelAndTypeOrThrow(dummyResultDto.getTestLevel(), dummyResultDto.getTestType());
    }

    @Test
    @DisplayName("Если в базе данных нет языка программирования, типа теста или их пары, то бросается исключение")
    void typeOrLevelOrTypeLevelPairAbsenceCauseException() {
        ResultDto dummyResultDto = getDummySaveResultDto();

        when(testSelector.findByLevelAndTypeOrThrow(dummyResultDto.getTestLevel(), dummyResultDto.getTestType())).thenThrow(
                NoSuchElementException.class);

        Throwable throwable = catchThrowable(() -> progressService.saveResults(dummyResultDto));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Поиск пользователя вызывает data layer")
    void userSearchCallDataLayer() {
        ResultDto dummyResultDto = getDummySaveResultDto();

        progressService.saveResults(dummyResultDto);
        verify(userSelector).getUserBy(dummyResultDto.getEmail());
    }

    @Test
    @DisplayName("Поиск пользователя по несуществующему в бд email провоцирует исключение")
    void userSearchByNonExistentEmailCauseException() {
        ResultDto dummyResultDto = getDummySaveResultDto();

        when(userSelector.getUserBy(dummyResultDto.getEmail())).thenThrow(NoSuchElementException.class);

        Throwable throwable = catchThrowable(() -> progressService.saveResults(dummyResultDto));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Сохранение результата не вызывает data layer вызывает получение старого результата")
    void resultSavingCallsOldResultObtaining() {
        ResultDto dummyResultDto = getDummySaveResultDto();
        QuizLevelTypePair dummyTestEntity = getDummyTestEntity();
        User user = getValidUser();

        progressService.saveResults(dummyResultDto);
        verify(progressSelector).getProgressByUserAndLevelTypePairOrReturnNew(user.getId(), dummyTestEntity.getId());
    }

    @Test
    @DisplayName("Результат не сохраняется, если он хуже предыдущего")
    void resultDoesntSavingIfNewResultIsWorseOrEqual() {
        ResultDto dummyResultDto = getDummySaveResultDto();
        QuizLevelTypePair dummyTestEntity = getDummyTestEntity();
        User user = getValidUser();
        Progress newProgress = getDummyProgress();
        Progress oldProgressWithBetterResult = getDummyProgress();
        oldProgressWithBetterResult.setProgressValue(newProgress.getProgressValue() + 1);

        when(progressSelector.getProgressByUserAndLevelTypePairOrReturnNew(user.getId(), dummyTestEntity.getId())).thenReturn(
                oldProgressWithBetterResult);

        progressService.saveResults(dummyResultDto);
        verify(progressSaver, times(0)).save(newProgress);
    }

    @Test
    @DisplayName("Результат сохраняется, если он лучше предыдущего")
    void resultSavingIfNewResultIsBetter() {
        ResultDto dummyResultDto = getDummySaveResultDto();
        Progress newProgress = getDummyProgress();
        newProgress.setLevelTypePair(null);

        progressService.saveResults(dummyResultDto);
        verify(progressSaver).save(newProgress);
    }

    @Test
    @DisplayName("Если результат хуже предыдущего, то дата результата не обновляется")
    void quizPassDateWontUpdateIfOldResultIsBetter() {
        ResultDto dummyResultDto = getDummySaveResultDto();
        QuizLevelTypePair dummyTestEntity = getDummyTestEntity();
        User user = getValidUser();
        Progress newProgress = getDummyProgress();
        Progress oldProgressWithBetterResult = getDummyProgress();
        oldProgressWithBetterResult.setProgressValue(newProgress.getProgressValue() + 1);

        when(progressSelector.getProgressByUserAndLevelTypePairOrReturnNew(user.getId(), dummyTestEntity.getId())).thenReturn(
                oldProgressWithBetterResult);

        progressService.saveResults(dummyResultDto);
        verify(quizPassDateSaver, times(0)).save(user.getId(), dummyTestEntity.getId());
    }

    @Test
    @DisplayName("Если результат равен предыдущему, то дата результата не обновляется")
    void quizPassDateWontUpdateIfOldResultAndNewResultAreEqual() {
        ResultDto dummyResultDto = getDummySaveResultDto();
        QuizLevelTypePair dummyTestEntity = getDummyTestEntity();
        User user = getValidUser();
        Progress oldProgressWithBetterResult = getDummyProgress();

        when(progressSelector.getProgressByUserAndLevelTypePairOrReturnNew(user.getId(), dummyTestEntity.getId())).thenReturn(
                oldProgressWithBetterResult);

        progressService.saveResults(dummyResultDto);
        verify(quizPassDateSaver, times(0)).save(user.getId(), dummyTestEntity.getId());
    }

    @Test
    @DisplayName("Если результат лучше предыдущего, то дата результата обновляется")
    void quizPassDateWillBeUpdatedIfNewResultIsBetter() {
        ResultDto dummyResultDto = getDummySaveResultDto();
        QuizLevelTypePair dummyTestEntity = getDummyTestEntity();
        User user = getValidUser();

        progressService.saveResults(dummyResultDto);
        verify(quizPassDateSaver).save(user.getId(), dummyTestEntity.getId());
    }

    @Test
    @DisplayName("Получение прогресса пользователя вызывает data layer для получение пользователя")
    void resultGettingCallDataLayerForUserSearch() {
        User user = getValidUser();
        when(userSelector.getUserBy(VALID_EMAIL)).thenReturn(user);

        progressService.getTestsProgressBy(VALID_EMAIL);
        verify(userSelector).getUserBy(VALID_EMAIL);
    }

    @Test
    @DisplayName("Получение прогресса несуществующего пользователя провоцирует исключение")
    void resultGettingWithInvalidEmailCallDataLayer() {
        when(userSelector.getUserBy(INVALID_EMAIL)).thenThrow(NoSuchElementException.class);

        Throwable throwable = catchThrowable(() -> progressService.getTestsProgressBy(INVALID_EMAIL));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Получение прогресса возвращает вызывает data layer для поиска прогресса")
    void resultGettingCallDataLayerForProgressSearch() {
        User user = getValidUser();

        progressService.getTestsProgressBy(VALID_EMAIL);
        verify(progressSelector).getProgressBy(user.getId());
    }


    @Test
    @DisplayName("Получение прогресса задействует конвертирование прогресса в Dto")
    void resultGettingCallProgressToDtoConversion() {
        User user = getValidUser();
        Progress dummyProgress = getDummyProgress();
        List<Progress> expectedToConvert = List.of(dummyProgress);

        when(progressSelector.getProgressBy(user.getId())).thenReturn(expectedToConvert);

        progressService.getTestsProgressBy(VALID_EMAIL);
        verify(progressConverter).convert(expectedToConvert);
    }

    @Test
    @DisplayName("Получение прогресса возвращает прогресс по имеющимся тестам и флаг прохождения")
    void resultGettingReturnsProgressDto() {
        User user = getValidUser();
        Progress dummyProgress = getDummyProgress();
        List<Progress> expectedToConvert = List.of(dummyProgress);
        List<TestTypeProgressDto> expected = List.of(getDummyTestTypeProgressDto());

        when(progressSelector.getProgressBy(user.getId())).thenReturn(expectedToConvert);
        when(progressConverter.convert(expectedToConvert)).thenReturn(expected);

        List<TestTypeProgressDto> actual = progressService.getTestsProgressBy(VALID_EMAIL);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Если в базе данных нет языка программирования, типа теста или их пары, то бросается исключение")
    void typeOrLevelOrTypeLevelPairAbsenceCauseExceptionWhenGettingResultForTest() {
        ResultDto dummyResultDto = getDummyResultDto();

        when(testSelector.findByLevelAndTypeOrThrow(dummyResultDto.getTestLevel(), dummyResultDto.getTestType())).thenThrow(
                NoSuchElementException.class);

        Throwable throwable = catchThrowable(() -> progressService.getResult(dummyResultDto));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Если пользователь не прошёл конкретный тест, то возвращается false")
    void resultGettingForTestGetsFalseIfUserHaveNotPassedTest() {
        ResultDto dummyResultDto = getDummyResultDto();
        dummyResultDto.setResult(10.);

        boolean actual = progressService.getResult(dummyResultDto);
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("Если пользователь прошёл конкретный тест, то возвращается true")
    void resultGettingForTestGetsTrueIfUserPassedTest() {
        ResultDto dummyResultDto = getDummyResultDto();

        boolean actual = progressService.getResult(dummyResultDto);
        assertThat(actual).isTrue();
    }

}