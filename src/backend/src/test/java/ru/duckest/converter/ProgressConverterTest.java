package ru.duckest.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.duckest.dto.TestLevelProgressDto;
import ru.duckest.dto.TestTypeProgressDto;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.duckest.utils.test.Constants.DUMMY_IMAGE_URL;
import static ru.duckest.utils.test.Constants.DUMMY_TEST_LEVEL;
import static ru.duckest.utils.test.Constants.DUMMY_TEST_TYPE;
import static ru.duckest.utils.test.jpa.ProgressTestFactory.getDummyProgress;

@SpringBootTest(classes = ProgressConverter.class)
class ProgressConverterTest {

    @Autowired
    private ProgressConverter progressConverter;

    @Test
    @DisplayName("Пустой список конвертируется в пустой список")
    void emptyListConvertsToEmptyList() {
        List<TestTypeProgressDto> actual = progressConverter.convert(Collections.emptyList());
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Непустой список конвертируется в dto")
    void progressListCanBeConvertedToProgressDto() {
        List<TestTypeProgressDto> expected = List.of(TestTypeProgressDto.builder()
                                                             .levelCompleted(true)
                                                             .testType(DUMMY_TEST_TYPE)
                                                             .imageUrl(DUMMY_IMAGE_URL)
                                                             .testLevelProgressDtos(List.of(TestLevelProgressDto.builder()
                                                                                                    .testLevel(DUMMY_TEST_LEVEL)
                                                                                                    .progress(80.)
                                                                                                    .testCompleted(true)
                                                                                                    .build()))
                                                             .build());

        List<TestTypeProgressDto> actual = progressConverter.convert(List.of(getDummyProgress()));
        assertThat(actual).isEqualTo(expected);
    }
}