package com.infinity.savorlyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.infinity.savorlyapp.uifiles.screen.addedit.AddEditRecipeScreen
import com.infinity.savorlyapp.uifiles.screen.addedit.EditUiScreen
import com.infinity.savorlyapp.uifiles.screen.details.RecipeDetailsScreen
import com.infinity.savorlyapp.uifiles.screen.favorites.FavoriteScreen
import com.infinity.savorlyapp.uifiles.screen.home.HomeScreen
import com.infinity.savorlyapp.uifiles.screen.welcome.WelcomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {

        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.AddEditRecipe.route) {
            AddEditRecipeScreen(navController, it)
        }



        composable(
            route = Screen.RecipeDetails.route,
            arguments = listOf(navArgument("recipeId") { type = androidx.navigation.NavType.StringType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
            RecipeDetailsScreen(navController, recipeId)
        }

        composable(Screen.Favorites.route) {
            FavoriteScreen(navController)
        }

        composable(
            route = Screen.EditUiScreen.route + "?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            EditUiScreen(navController = navController, recipeId = id)
        }

    }
}
