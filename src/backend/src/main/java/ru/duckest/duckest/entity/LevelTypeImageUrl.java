package ru.duckest.duckest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "level_type_image_url")
public class LevelTypeImageUrl implements Serializable {

    @Id
    @Column(name = "quiz_level_type_id")
    private UUID levelTypeId;

    @OneToOne(targetEntity = QuizLevelTypePair.class)
    @JoinColumn(name = "quiz_level_type_id", referencedColumnName = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "description_quiz_level_fk"))
    private QuizLevelTypePair levelTypePair;

    @Column(name = "image_url")
    private String imageUrl;
}
