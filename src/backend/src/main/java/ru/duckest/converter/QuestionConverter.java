package ru.duckest.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.dto.QuestionDto;
import ru.duckest.entity.Answer;
import ru.duckest.entity.QuizQuestion;
import ru.duckest.entity.RightAnswer;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionConverter {

    public QuizQuestion convert(QuestionDto source) {
        var question = QuizQuestion.builder().question(source.getQuestion()).answers(source.getAnswers().stream().map(answerText -> {
            var answer = new Answer();
            answer.setAnswerText(answerText);
            return answer;
        }).collect(Collectors.toList())).build();
        question.getAnswers().forEach(answer -> answer.setQuestion(question));
        question.setRightAnswer(RightAnswer.builder().question(question).rightAnswerIndex(source.getRightAnswerIndex()).build());
        return question;
    }

    public QuestionDto convert(QuizQuestion source) {
        return QuestionDto.builder()
                .question(source.getQuestion())
                .rightAnswerIndex(source.getRightAnswer().getRightAnswerIndex())
                .answers(source.getAnswers().stream().map(Answer::getAnswerText).collect(Collectors.toList()))
                .build();
    }

}
