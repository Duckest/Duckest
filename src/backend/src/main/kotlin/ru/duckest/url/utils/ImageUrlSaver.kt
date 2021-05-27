package ru.duckest.url.utils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.duckest.entity.QuizType
import ru.duckest.entity.TypeImageUrl
import ru.duckest.repository.ImageUrls

@Component
class ImageUrlSaver @Autowired constructor(private val imageUrls: ImageUrls) {

    fun save(imageUrl: TypeImageUrl): TypeImageUrl {
        return imageUrls.save(imageUrl)
    }

    fun save(imageUrl: String, testType: QuizType?): TypeImageUrl {
        val typeImageUrl = TypeImageUrl()
        typeImageUrl.imageUrl = imageUrl
        typeImageUrl.quizType = testType
        return save(typeImageUrl)
    }
}