package ru.duckest.url.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.duckest.url.dto.UploadImageDto
import ru.duckest.url.service.UrlService

@RestController
@RequestMapping("url")
class UrlController @Autowired constructor(private val urlService: UrlService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun uploadUrlForProgrammingLanguage(@RequestBody uploadImageDto: UploadImageDto) = urlService.save(uploadImageDto)
}