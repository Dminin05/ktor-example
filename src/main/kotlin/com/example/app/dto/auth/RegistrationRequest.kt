package com.example.app.dto.auth

import com.example.app.model.Activity
import com.example.app.model.Sex
import com.example.app.model.Target
import java.math.BigDecimal

data class RegistrationRequest(
    val name: String,
    val age: Int,
    val height: BigDecimal,
    val weight: BigDecimal,
    val sex: Sex,
    val target: Target,
    val activity: Activity,
    val email: String,
    val password: String
)
