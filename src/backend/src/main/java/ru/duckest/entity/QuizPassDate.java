package ru.duckest.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "quiz_pass_date")
@IdClass(QuizPassDateId.class)
public class QuizPassDate implements Serializable {

    @Id
    @Column(name = "person_id")
    private UUID userId;

    @Id
    @Column(name = "quiz_level_type_id")
    private UUID leveTypeId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "person_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ToString.Exclude
    @ManyToOne(targetEntity = QuizLevelTypePair.class)
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "quiz_level_type_id", referencedColumnName = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "description_quiz_level_fk"))
    private QuizLevelTypePair levelTypePair;

    @Column(name = "finish_date")
    private LocalDate finishDate;

}
