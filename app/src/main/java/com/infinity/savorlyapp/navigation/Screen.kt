package com.infinity.savorlyapp.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Home : Screen("home")
    object AddEditRecipe : Screen("add_edit_recipe"){
        fun passRecipeId(id: String): String = "add_edit_recipe?id=$id"
    }
    object RecipeDetails : Screen("recipe_details/{recipeId}") {
        fun passRecipeId(recipeId: String) = "recipe_details/$recipeId"
    }
    object Favorites : Screen("favorites")
    object EditUiScreen : Screen("edit_screen") {
        fun passRecipeId(id: String): String = "edit_screen?id=$id"
    }

}
