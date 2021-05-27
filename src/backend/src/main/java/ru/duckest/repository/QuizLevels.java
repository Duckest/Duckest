package ru.duckest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.duckest.entity.QuizLevel;

@Repository
public interface QuizLevels extends CrudRepository<QuizLevel, String> {

}
