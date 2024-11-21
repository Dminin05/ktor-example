package com.example.app.model

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object MealTime : LongIdTable("nutrify.meail_time") {
    val recipeId = reference("recipe_id", Recipes)
    val type = enumerationByName<MealType>("type", 15)
}

class MealTimeDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<MealTimeDao>(MealTime)
    var recipeId by MealTime.recipeId
    var type by MealTime.type

    val recipe by RecipeDao referencedOn MealTime.recipeId
}

enum class MealType(val percentage: Int) {
    BREAKFAST(25), LAUNCH(30), DINNER(25), PART_MEAL(15)
}
