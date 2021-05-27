package ru.duckest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_type")
public class QuizType implements Serializable {

    @Id
    @Column(name = "type")
    private String type;

    @OneToOne(mappedBy = "quizType", targetEntity = TypeImageUrl.class, cascade = CascadeType.ALL)
    private TypeImageUrl imageUrl;

    public TypeImageUrl getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(TypeImageUrl imageUrl) {
        this.imageUrl = imageUrl;
    }

}
