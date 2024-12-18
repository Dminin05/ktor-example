package com.example.app.repository

import com.example.app.dto.auth.RegistrationRequest
import com.example.app.dto.user.PersonalDataUpdateDto
import com.example.app.model.PersonalDataDto

interface PersonalDataRepository {

    fun save(registrationRequest: RegistrationRequest, userId: Long): PersonalDataDto

    fun findByUserId(id: Long): PersonalDataDto

    fun updatePersonalData(personalDataUpdateDto: PersonalDataUpdateDto, userId: Long): PersonalDataDto

    fun isPossibleToUpdate(userId: Long): Boolean
}
