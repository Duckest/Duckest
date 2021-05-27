package ru.duckest.duckest.service;

import ru.duckest.duckest.dto.TestCreationDto;
import ru.duckest.duckest.dto.TestDto;
import ru.duckest.duckest.dto.TypeLevelPairDto;

public interface TestService {

    TestDto getTestBy(TypeLevelPairDto typeLevelPairDto);

    void save(TestCreationDto test);

    void delete(TypeLevelPairDto typeLevelPairDto);
}
