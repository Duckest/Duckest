package ru.duckest.service;

import ru.duckest.dto.TestCreationDto;
import ru.duckest.dto.TestDto;
import ru.duckest.dto.TypeLevelPairDto;

public interface TestService {

    TestDto getTestBy(TypeLevelPairDto typeLevelPairDto);

    void save(TestCreationDto test);

    void delete(TypeLevelPairDto typeLevelPairDto);
}
