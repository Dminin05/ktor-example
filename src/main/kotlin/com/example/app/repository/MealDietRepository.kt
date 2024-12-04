package com.example.app.repository

import com.example.app.dto.food.MealDietDto
import com.example.app.model.Recipe
import java.time.LocalDate

interface MealDietRepository {

    fun saveAll(diet: List<List<MealDietDto>>, userId: Long)

    fun findDietByDate(date: LocalDate, userId: Long): List<Recipe>
}