package ru.duckest.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.verify
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.duckest.url.controller.UrlController
import ru.duckest.url.dto.UploadImageDto
import ru.duckest.url.service.UrlService
import ru.duckest.utils.test.Constants.DUMMY_IMAGE_URL
import ru.duckest.utils.test.Constants.DUMMY_TEST_TYPE

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = [UrlController::class])
internal class UrlControllerTest {


    private val mapper = jacksonObjectMapper()

    @MockkBean(relaxed = true)
    private lateinit var service: UrlService

    private lateinit var mockMvc: MockMvc

    @BeforeAll
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(UrlController(service)).build()
    }

    @Nested
    @DisplayName("POST /url")
    inner class UploadUrlForLanguage {

        @Test
        @DisplayName("Возвращает 201 код")
        fun `Returns 201 http code`() {
            val uploadImageDto = UploadImageDto(DUMMY_TEST_TYPE, DUMMY_IMAGE_URL)
            val json = mapper.writeValueAsString(uploadImageDto)

            mockMvc.post("/url") {
                contentType = MediaType.APPLICATION_JSON
                content = json
            }.andExpect {
                status { isCreated() }
            }
        }

        @Test
        @DisplayName("Вызывает сервисный слой")
        fun `Calls service layer`() {
            val uploadImageDto = UploadImageDto(DUMMY_TEST_TYPE, DUMMY_IMAGE_URL)
            val json = mapper.writeValueAsString(uploadImageDto)

            mockMvc.post("/url") {
                contentType = MediaType.APPLICATION_JSON
                content = json
            }
            verify { service.save(uploadImageDto) }
        }

    }
}