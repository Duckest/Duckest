package ru.duckest.duckest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeLevelPairDto {

    @JsonProperty("test_level")
    private String testLevel;

    @JsonProperty("test_type")
    private String testType;
}
