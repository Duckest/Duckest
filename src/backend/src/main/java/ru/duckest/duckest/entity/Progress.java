package ru.duckest.duckest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "progress")
@IdClass(ProgressId.class)
public class Progress implements Serializable {

    @Id
    @Column(name = "person_id")
    private UUID userId;

    @Id
    @Column(name = "quiz_level_type_id")
    private UUID levelTypeId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "person_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(targetEntity = QuizLevelTypePair.class)
    @JoinColumn(name = "quiz_level_type_id", referencedColumnName = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "description_quiz_level_fk"))
    private QuizLevelTypePair levelTypePair;

    @Default
    @Column(name = "progress")
    private Double progressValue = 0.;
}
