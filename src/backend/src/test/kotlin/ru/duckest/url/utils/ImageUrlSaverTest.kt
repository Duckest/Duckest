package ru.duckest.url.utils

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.duckest.entity.TypeImageUrl
import ru.duckest.repository.ImageUrls
import ru.duckest.utils.test.Constants.DUMMY_IMAGE_URL
import ru.duckest.utils.test.jpa.QuizTypeTestFactory.getDummyQuizType

@SpringBootTest(classes = [ImageUrlSaver::class])
internal class ImageUrlSaverTest @Autowired constructor(private val imageUrlSaver: ImageUrlSaver) {

    @MockkBean(relaxed = true)
    private lateinit var imageUrls: ImageUrls

    @Test
    @DisplayName("Ссылка на изображение сохраняется как сущность")
    fun `Image url can be saved as entity`() {
        val dummyQuizType = getDummyQuizType()
        val typeImageUrl = TypeImageUrl()
        typeImageUrl.quizType = dummyQuizType

        every { imageUrls.save(typeImageUrl) }.returns(typeImageUrl)

        imageUrlSaver.save(typeImageUrl)
        verify { imageUrls.save(typeImageUrl) }
    }

    @Test
    @DisplayName("Ссылка на изображение сохраняется по строке и сущности языка программирования")
    fun `Image url can be saved by string and test type entity`() {
        val dummyQuizType = getDummyQuizType()
        val typeImageUrl = TypeImageUrl()
        typeImageUrl.quizType = dummyQuizType
        typeImageUrl.imageUrl = DUMMY_IMAGE_URL

        every { imageUrls.save(typeImageUrl) }.returns(typeImageUrl)

        imageUrlSaver.save(DUMMY_IMAGE_URL, dummyQuizType)
        verify { imageUrls.save(typeImageUrl) }
    }
}