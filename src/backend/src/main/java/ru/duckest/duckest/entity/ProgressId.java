package ru.duckest.duckest.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProgressId implements Serializable {

    @Id
    @Column(name = "person_id")
    private UUID userId;

    @Id
    @Column(name = "quiz_level_type_id")
    private UUID levelTypeId;
}
