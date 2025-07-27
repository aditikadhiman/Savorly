package com.infinity.savorlyapp.data.repository

import com.infinity.savorlyapp.data.local.dao.RecipeDao
import com.infinity.savorlyapp.data.local.model.RecipeCategory
import com.infinity.savorlyapp.data.local.model.RecipeEntity
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val dao: RecipeDao) {

    suspend fun upsertRecipe(recipe: RecipeEntity) = dao.upsert(recipe)

    suspend fun deleteRecipe(recipe: RecipeEntity) = dao.delete(recipe)

    fun getAllRecipes(): Flow<List<RecipeEntity>> = dao.getAllRecipes()

    suspend fun getRecipeById(id: String): RecipeEntity? = dao.getRecipeById(id)


     suspend fun updateRecipe(recipe: RecipeEntity) {
        dao.update(recipe)
    }
}
