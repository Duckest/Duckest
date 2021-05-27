package ru.duckest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_level_type_pair")
public class QuizLevelTypePair implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @OneToOne(targetEntity = QuizLevel.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_level", referencedColumnName = "level", foreignKey = @ForeignKey(name = "question_quiz_level_fk"))
    private QuizLevel quizLevel;

    @OneToOne(targetEntity = QuizType.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_type", referencedColumnName = "type", foreignKey = @ForeignKey(name = "question_quiz_type_fk"))
    private QuizType quizType;

    @OneToOne(mappedBy = "levelTypePair", targetEntity = QuizDescription.class, cascade = CascadeType.ALL)
    private QuizDescription description;

    @OneToMany(mappedBy = "quizLevelTypePair", targetEntity = QuizQuestion.class, cascade = CascadeType.ALL)
    private List<QuizQuestion> questions;

    @OneToOne(mappedBy = "levelTypePair", targetEntity = PassThreshold.class, cascade = CascadeType.ALL)
    private PassThreshold passThreshold;

    @OneToMany(mappedBy = "levelTypePair", targetEntity = Progress.class, cascade = CascadeType.ALL)
    private List<Progress> progresses;

    @OneToMany(mappedBy = "levelTypePair", targetEntity = QuizPassDate.class, cascade = CascadeType.ALL)
    private List<QuizPassDate> passDates;
}
