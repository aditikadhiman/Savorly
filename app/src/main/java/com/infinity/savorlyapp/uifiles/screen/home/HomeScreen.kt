package com.infinity.savorlyapp.uifiles.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.infinity.savorlyapp.R
import com.infinity.savorlyapp.data.local.model.RecipeCategory
import com.infinity.savorlyapp.navigation.Screen
import com.infinity.savorlyapp.ui.theme.Primary
import com.infinity.savorlyapp.ui.theme.TxtColor
import com.infinity.savorlyapp.ui.theme.background
import com.infinity.savorlyapp.uifiles.components.*


@Composable
fun HomeScreen(navController: NavController) {

    val viewModel: HomeViewModel = hiltViewModel()
    val recipeList by viewModel.recipes.collectAsState()

    var query by remember { mutableStateOf("") }

    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Scaffold(
        backgroundColor = background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditRecipe.route)
                },
                backgroundColor = Primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Section",
                tint =TxtColor)
            }
        },
        bottomBar = {
            BottomBarComponent(
                onClickHome = { /* Already on Home */ },
                onClickFavorite = { navController.navigate(Screen.Favorites.route) }
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
            Spacer(modifier = Modifier.height(15.dp))
            SearchBar(value = stringResource(id = R.string.searchtxt),
                query = query,
                onQueryChanged = {
                    query = it
                    viewModel.setSearchQuery(query = it)
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(RecipeCategory.values()) { category ->
                    Tabs(
                        value = category.displayName,
                        isSelected = selectedCategory == category,
                        onClick = {
                            viewModel.setCategory(category)
                        }
                    )
                }
            }


            Spacer(modifier = Modifier.height(15.dp))
            partition()
            Spacer(modifier = Modifier.height(15.dp))
            if (recipeList.isEmpty()) {
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

                    items(recipeList) { recipe ->
                        RecipeCards(
                            recipe = recipe,
                            onClick = {
                                navController.navigate(Screen.RecipeDetails.passRecipeId(recipe.id))
                            },
                            onEdit = {
                                navController.navigate(Screen.EditUiScreen.passRecipeId(recipe.id))
                            },
                            onDelete = {
                                viewModel.deleteRecipe(recipe) // You need to implement this in ViewModel
                            },
                            onFav = {
                                viewModel.toggleFavorite(recipe) // Optional: if you plan to handle favorites
                            },
                            default_image = painterResource(id = R.drawable.default_image)
                        )
                    }
                }
            }
        }
    }
}