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
public class UserDto {

    @Email(message = "email не соответствует стандартному формату")
    @NotBlank(message = "email не должен быть пуст")
    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String firstName;

    @JsonProperty("surname")
    private String lastName;

    @JsonProperty("patronymic")
    private String middleName;

}
