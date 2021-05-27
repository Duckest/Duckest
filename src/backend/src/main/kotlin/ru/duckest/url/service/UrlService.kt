package ru.duckest.url.service

import ru.duckest.url.dto.UploadImageDto

interface UrlService {
    fun save(uploadImageDto: UploadImageDto)
}