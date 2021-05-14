package ru.duckest.duckest.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.duckest.duckest.dto.QuestionDto;
import ru.duckest.duckest.entity.QuizQuestion;
import ru.duckest.duckest.utils.test.dto.QuestionDtoTestFactory;
import ru.duckest.duckest.utils.test.jpa.QuizQuestionTestFactory;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = QuestionConverter.class)
class QuestionConverterTest {

    @Autowired
    private QuestionConverter questionConverter;

    @Test
    @DisplayName("Dto вопроса конвертируется в сущность успешно")
    void questionDtoCanBeConvertedToEntity() {
        QuestionDto questionWithThreeAnswersAndFirstRight = QuestionDtoTestFactory.getQuestionWithThreeAnswersAndFirstRight();
        QuizQuestion expected = QuizQuestionTestFactory.getDummyQuizQuestion();

        QuizQuestion actual = questionConverter.convert(questionWithThreeAnswersAndFirstRight);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Dto вопроса конвертируется в сущность успешно")
    void questionEntityCanBeConvertedToDto() {
        QuizQuestion toConvert = QuizQuestionTestFactory.getDummyQuizQuestion();
        QuestionDto expected = QuestionDtoTestFactory.getQuestionWithThreeAnswersAndFirstRight();

        QuestionDto actual = questionConverter.convert(toConvert);
        assertThat(actual).isEqualTo(expected);
    }

}