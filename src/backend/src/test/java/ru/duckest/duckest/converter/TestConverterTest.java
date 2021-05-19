package ru.duckest.duckest.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.duckest.duckest.dto.TestDto;
import ru.duckest.duckest.entity.QuizLevelTypePair;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static ru.duckest.duckest.utils.test.dto.QuestionDtoTestFactory.getQuestionWithThreeAnswersAndFirstRight;
import static ru.duckest.duckest.utils.test.dto.TestDtoTestFactory.getDummyTestDto;
import static ru.duckest.duckest.utils.test.jpa.QuizDescriptionTestFactory.getDummyQuizDescription;
import static ru.duckest.duckest.utils.test.jpa.QuizLevelTypePairTestFactory.getDummyTestEntity;
import static ru.duckest.duckest.utils.test.jpa.QuizQuestionTestFactory.getDummyQuizQuestion;
import static ru.duckest.duckest.utils.test.jpa.QuizQuestionTestFactory.getTwentyDummyQuestions;

@SpringBootTest(classes = TestConverter.class)
class TestConverterTest {

    @Autowired
    private TestConverter testConverter;

    @MockBean
    private QuestionConverter questionConverter;

    @Test
    @DisplayName("Сущность теста конвертируется в dto")
    void testEntityCanBeConvertedToDto() {
        QuizLevelTypePair dummyTestEntity = getDummyTestEntity();
        dummyTestEntity.setDescription(getDummyQuizDescription());
        dummyTestEntity.setQuestions(getTwentyDummyQuestions());
        TestDto expected = getDummyTestDto();

        when(questionConverter.convert(getDummyQuizQuestion())).thenReturn(getQuestionWithThreeAnswersAndFirstRight());

        TestDto actual = testConverter.convert(dummyTestEntity);
        assertThat(actual).isEqualTo(expected);
    }

}