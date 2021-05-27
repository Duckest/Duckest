package ru.duckest.utils.passdate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.duckest.entity.QuizPassDate;
import ru.duckest.repository.QuizPassDates;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QuizPassDateSaver {

    private final QuizPassDates quizPassDates;

    public QuizPassDate save(UUID userId, UUID levelTypeId) {
        return quizPassDates.save(QuizPassDate.builder().userId(userId).leveTypeId(levelTypeId).finishDate(LocalDate.now()).build());
    }

}
