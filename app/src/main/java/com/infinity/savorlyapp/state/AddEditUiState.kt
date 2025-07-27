package com.infinity.savorlyapp.state

import com.infinity.savorlyapp.data.local.model.RecipeCategory

data class RecipeUiState(
    var id: String? = null, // null for new, not-null for editing
    var title: String = "",
    var ingredients: String = "",
    var instructions: String = "",
    var category: RecipeCategory = RecipeCategory.BREAKFAST,
    var rating: String = "",
    var imageUri: String? = null,
    var isFavorite: Boolean = false,

    // Validation Flags
    var titleError: Boolean = false,
    var ingredientsError: Boolean = false,
    var instructionsError: Boolean = false,
    var ratingError: Boolean = false
//    var isSaveSuccessful: Boolean = false,
//    var errorMessage: String? = null
)

