package ru.duckest.duckest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.duckest.duckest.entity.QuizLevel;
import ru.duckest.duckest.entity.QuizLevelTypePair;
import ru.duckest.duckest.entity.QuizType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuizLevelTypePairs extends CrudRepository<QuizLevelTypePair, UUID> {

    Optional<QuizLevelTypePair> findByQuizLevelAndQuizType(QuizLevel level, QuizType type);

}
