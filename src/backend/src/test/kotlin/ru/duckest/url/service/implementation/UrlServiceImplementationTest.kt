package ru.duckest.url.service.implementation

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.duckest.url.dto.UploadImageDto
import ru.duckest.url.service.UrlService
import ru.duckest.url.utils.ImageUrlSaver
import ru.duckest.utils.test.Constants.DUMMY_IMAGE_URL
import ru.duckest.utils.test.Constants.DUMMY_TEST_TYPE
import ru.duckest.utils.test.jpa.QuizTypeTestFactory.getDummyQuizType
import ru.duckest.utils.test.type.QuizTypeSaver
import ru.duckest.utils.test.type.QuizTypeSelector
import java.util.*

@SpringBootTest(classes = [UrlServiceImplementation::class])
internal class UrlServiceImplementationTest @Autowired constructor(private val service: UrlService) {
    private val dummyUploadImageDto = UploadImageDto(DUMMY_TEST_TYPE, DUMMY_IMAGE_URL)

    @MockkBean(relaxed = true)
    private lateinit var quizTypeSaver: QuizTypeSaver

    @MockkBean(relaxed = true)
    private lateinit var quizTypeSelector: QuizTypeSelector

    @MockkBean(relaxed = true)
    private lateinit var imageUrlSaver: ImageUrlSaver

    @Nested
    @DisplayName("Сохранение ссылки на изображение для языка программирования")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class SaveImageUrl {

        @Test
        @DisplayName("Производится поиск языка программирования в бд")
        fun `Searches for test type in database`() {
            every { quizTypeSelector.findBy(dummyUploadImageDto.testType) }.returns(Optional.empty())
            service.save(dummyUploadImageDto)

            verify { quizTypeSelector.findBy(dummyUploadImageDto.testType) }
        }

        @Test
        @DisplayName("Если языка программирования в бд нет, язык сохраняется")
        fun `If test type is missing, it would be saved`() {
            every { quizTypeSelector.findBy(dummyUploadImageDto.testType) }.returns(Optional.empty())

            service.save(dummyUploadImageDto)
            verify { quizTypeSaver.save(dummyUploadImageDto.testType) }
        }

        @Test
        @DisplayName("Сохраняется ссылка на изображение для языка программирования, если до этого ссылки не было")
        fun `Saves image url for test type`() {
            val dummyQuizType = getDummyQuizType()
            dummyQuizType.imageUrl = null
            every { quizTypeSelector.findBy(dummyUploadImageDto.testType) }.returns(Optional.of(dummyQuizType))

            service.save(dummyUploadImageDto)
            verify { imageUrlSaver.save(dummyUploadImageDto.imageUrl!!, dummyQuizType) }
        }

        @Test
        @DisplayName("Обновляется ссылка на изображение для языка программирования")
        fun `Updates image url for test type`() {
            val dummyQuizType = getDummyQuizType()
            every { quizTypeSelector.findBy(dummyUploadImageDto.testType) }.returns(Optional.of(dummyQuizType))

            dummyQuizType.imageUrl.imageUrl = dummyUploadImageDto.imageUrl
            service.save(dummyUploadImageDto)
            verify { quizTypeSaver.save(dummyQuizType) }
        }
    }
}

