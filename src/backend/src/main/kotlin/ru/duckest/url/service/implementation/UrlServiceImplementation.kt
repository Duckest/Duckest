package ru.duckest.url.service.implementation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.duckest.url.dto.UploadImageDto
import ru.duckest.url.service.UrlService
import ru.duckest.url.utils.ImageUrlSaver
import ru.duckest.utils.test.type.QuizTypeSaver
import ru.duckest.utils.test.type.QuizTypeSelector

@Service
class UrlServiceImplementation @Autowired constructor(
    private val quizTypeSaver: QuizTypeSaver,
    private val quizTypeSelector: QuizTypeSelector,
    private val imageUrlSaver: ImageUrlSaver
) : UrlService {

    override fun save(uploadImageDto: UploadImageDto) {
        val testType = quizTypeSelector.findBy(uploadImageDto.testType).orElseGet { quizTypeSaver.save(uploadImageDto.testType) }
        if (testType.imageUrl != null) {
            testType.imageUrl.imageUrl = uploadImageDto.imageUrl
            quizTypeSaver.save(testType)
        } else {
            imageUrlSaver.save(uploadImageDto.imageUrl!!, testType)
        }
    }

}