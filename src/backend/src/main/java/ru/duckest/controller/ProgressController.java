package ru.duckest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.duckest.dto.ResultDto;
import ru.duckest.dto.TestTypeProgressDto;
import ru.duckest.service.ProgressService;

import java.util.List;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("progress")
public class ProgressController {

    private final ProgressService progressService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TestTypeProgressDto>> getTestsProgressBy(@RequestParam("email") String email) {
        List<TestTypeProgressDto> testsProgress = progressService.getTestsProgressBy(email);
        return ResponseEntity.ok(testsProgress);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> saveTestResultsAndGetPassedFlag(@Valid @RequestBody ResultDto resultDto) {
        progressService.saveResults(resultDto);
        boolean result = progressService.getResult(resultDto);
        return ResponseEntity.ok(result);
    }
}
