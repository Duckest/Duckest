package ru.duckest.duckest.service;

import ru.duckest.duckest.dto.ResultDto;
import ru.duckest.duckest.dto.TestTypeProgressDto;

import java.util.List;

public interface ProgressService {

    void saveResults(ResultDto resultDto);

    List<TestTypeProgressDto> getTestsProgressBy(String email);

    boolean getResult(ResultDto resultDto);
}
