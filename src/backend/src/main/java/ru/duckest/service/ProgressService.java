package ru.duckest.service;

import ru.duckest.dto.TestTypeProgressDto;
import ru.duckest.dto.ResultDto;

import java.util.List;

public interface ProgressService {

    void saveResults(ResultDto resultDto);

    List<TestTypeProgressDto> getTestsProgressBy(String email);

    boolean getResult(ResultDto resultDto);
}
