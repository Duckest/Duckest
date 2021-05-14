package ru.duckest.duckest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.duckest.duckest.entity.QuizType;

@Repository
public interface QuizTypes extends CrudRepository<QuizType, String> {

}
