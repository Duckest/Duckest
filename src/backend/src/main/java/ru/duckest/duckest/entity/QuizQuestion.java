package ru.duckest.duckest.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_question")
public class QuizQuestion implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @Column(name = "question")
    private String question;

    @ManyToOne(targetEntity = QuizLevelTypePair.class)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "quiz_level_type_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "description_quiz_level_fk"))
    private QuizLevelTypePair quizLevelTypePair;

    @Default
    @OneToMany(targetEntity = Answer.class, mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @OneToOne(targetEntity = RightAnswer.class, mappedBy = "question", cascade = CascadeType.ALL)
    private RightAnswer rightAnswer;

}
