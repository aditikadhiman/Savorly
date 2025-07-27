package com.infinity.savorlyapp.uifiles.screen.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.infinity.savorlyapp.R
import com.infinity.savorlyapp.navigation.Screen
import com.infinity.savorlyapp.ui.theme.background
import com.infinity.savorlyapp.uifiles.components.*
import com.infinity.savorlyapp.uifiles.screen.home.HomeViewModel

@Composable
fun FavoriteScreen(navController: NavController) {
    val viewModel: FavoriteViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()

    val favoriteList by viewModel.favorites.collectAsState()

    Scaffold(
        backgroundColor = background,
        bottomBar = {
            BottomBarComponent(
                selectedTab = "Favorites",
                onClickHome = { navController.navigate(Screen.Home.route){
                    popUpTo(Screen.Favorites.route){inclusive=true}
                } },
                onClickFavorite = {  }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {
            Heading(value = stringResource(id = R.string.savorly))
            Spacer(modifier = Modifier.height(25.dp))

            if (favoriteList.isEmpty()) {
                EmptyStateComponent(message = "No recipes found.")
            } else {
                LazyVerticalGrid(
                    modifier = Modifier
                        .weight(1f),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 70.dp)
                ) {

                    items(favoriteList) { favorite ->
                        RecipeCards(
                            recipe = favorite,
                            onClick = {
                                navController.navigate(Screen.RecipeDetails.passRecipeId(favorite.id))
                            },
                            onEdit = {
                                navController.navigate(Screen.EditUiScreen.passRecipeId(favorite.id))
                            },
                            onDelete = {
                                homeViewModel.deleteRecipe(favorite) // You need to implement this in ViewModel
                            },
                            onFav = {
                                homeViewModel.toggleFavorite(favorite) // Optional: if you plan to handle favorites
                            },
                            default_image = painterResource(id = R.drawable.default_image)
                        )
                    }
                }
            }
        }
    }
}

