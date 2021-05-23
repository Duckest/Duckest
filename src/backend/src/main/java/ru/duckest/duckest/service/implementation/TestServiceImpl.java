package ru.duckest.duckest.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.duckest.duckest.converter.QuestionConverter;
import ru.duckest.duckest.converter.TestConverter;
import ru.duckest.duckest.dto.TestCreationDto;
import ru.duckest.duckest.dto.TestDto;
import ru.duckest.duckest.dto.TypeLevelPairDto;
import ru.duckest.duckest.entity.QuizLevelTypePair;
import ru.duckest.duckest.entity.QuizQuestion;
import ru.duckest.duckest.service.TestService;
import ru.duckest.duckest.utils.test.TestSaver;
import ru.duckest.duckest.utils.test.TestSelector;
import ru.duckest.duckest.utils.test.description.DescriptionSaver;
import ru.duckest.duckest.utils.test.question.QuestionSaver;
import ru.duckest.duckest.utils.test.threshold.PassThresholdSaver;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestSelector testSelector;
    private final TestSaver testSaver;
    private final DescriptionSaver descriptionSaver;
    private final PassThresholdSaver passThresholdSaver;
    private final QuestionSaver questionSaver;
    private final QuestionConverter questionConverter;
    private final TestConverter testConverter;

    @Override
    public TestDto getTestBy(TypeLevelPairDto typeLevelPairDto) {
        QuizLevelTypePair test = testSelector.findByLevelAndTypeOrThrow(typeLevelPairDto.getTestLevel(), typeLevelPairDto.getTestType());
        return testConverter.convert(test);
    }

    @Override
    public void save(TestCreationDto test) {
        var levelTypePair = testSelector.findByLevelAndType(test.getTestLevel(), test.getTestType())
                .orElseGet(() -> testSaver.save(test.getTestLevel(), test.getTestType(), test.getImageUrl()));
        descriptionSaver.save(test.getDescription(), levelTypePair);
        passThresholdSaver.save(test.getThreshold(), levelTypePair);
        List<QuizQuestion> questions = test.getQuestions().stream().map(question -> {
            var converted = questionConverter.convert(question);
            converted.setQuizLevelTypePair(levelTypePair);
            return converted;
        }).collect(Collectors.toList());
        questionSaver.save(questions);
    }
}
