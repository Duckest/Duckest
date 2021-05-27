package ru.duckest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {

    @Email(message = "email не соответствует стандартному формату")
    @NotBlank(message = "email не должен быть пуст")
    @JsonProperty("email")
    private String email;

    @JsonProperty("test_type")
    private String testType;

    @JsonProperty("test_level")
    private String testLevel;

    @JsonProperty("result")
    private Double result;

}
