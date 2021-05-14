package ru.duckest.duckest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pass_threshold")
public class PassThreshold implements Serializable {

    @Id
    @Column(name = "quiz_level_type_id")
    private UUID levelTypeId;

    @OneToOne(targetEntity = QuizLevelTypePair.class)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @PrimaryKeyJoinColumn(name = "quiz_level_type_id", referencedColumnName = "id")
    private QuizLevelTypePair levelTypePair;

    @Column(name = "threshold")
    private Integer threshold;

}
