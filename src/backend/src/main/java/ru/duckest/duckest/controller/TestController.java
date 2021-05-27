package ru.duckest.duckest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.duckest.duckest.dto.TestCreationDto;
import ru.duckest.duckest.dto.TestDto;
import ru.duckest.duckest.dto.TypeLevelPairDto;
import ru.duckest.duckest.service.TestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class TestController {

    private final TestService testService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> createTest(@RequestBody TestCreationDto testCreationDto) {
        testService.save(testCreationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TestDto> getTestBy(@RequestParam("test_type") String testType, @RequestParam("test_level") String testLevel) {
        var typeLevelPair = TypeLevelPairDto.builder().testLevel(testLevel).testType(testType).build();
        TestDto test = testService.getTestBy(typeLevelPair);
        return ResponseEntity.ok(test);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteTestBy(@RequestParam("test_type") String testType,
                                                   @RequestParam("test_level") String testLevel) {

        var typeLevelPair = TypeLevelPairDto.builder().testLevel(testLevel).testType(testType).build();
        testService.delete(typeLevelPair);
        return ResponseEntity.noContent().build();
    }

}
