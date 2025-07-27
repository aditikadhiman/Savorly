package com.infinity.savorlyapp.state

import com.infinity.savorlyapp.data.local.model.RecipeCategory

sealed class RecipeEvent {

    data class TitleChanged(val title: String): RecipeEvent()
    data class IngredientsChanged(val ingredients: String): RecipeEvent()
    data class InstructionsChanged(val instructions: String): RecipeEvent()
    data class CategoryChanged(val category: RecipeCategory): RecipeEvent()
    data class RatingChanged(val rating: String): RecipeEvent()
    data class ImageUriChanged(val uri: String): RecipeEvent()

    object SaveButtonClicked : RecipeEvent()
    data class UpdateRecipeClicked(val id: String) : RecipeEvent()

//    object DeleteRecipe : RecipeEvent()

//    object ToggleFavorite : RecipeEvent()
}
