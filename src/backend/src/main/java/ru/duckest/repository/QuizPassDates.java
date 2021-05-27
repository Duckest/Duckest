package ru.duckest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.duckest.entity.QuizPassDate;
import ru.duckest.entity.QuizPassDateId;

@Repository
public interface QuizPassDates extends CrudRepository<QuizPassDate, QuizPassDateId> {

}
