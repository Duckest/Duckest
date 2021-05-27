package ru.duckest.entity;


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
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_description")
public class QuizDescription implements Serializable {

    @Id
    @Column(name = "quiz_level_type_id")
    private UUID levelTypePairId;

    @Column(name = "description")
    private String description;

    @OneToOne(targetEntity = QuizLevelTypePair.class)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "quiz_level_type_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "description_quiz_level_fk"))
    private QuizLevelTypePair levelTypePair;
}
