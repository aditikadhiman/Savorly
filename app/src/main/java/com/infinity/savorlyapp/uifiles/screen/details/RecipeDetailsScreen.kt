package com.infinity.savorlyapp.uifiles.screen.details

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.infinity.savorlyapp.R
import com.infinity.savorlyapp.ui.theme.Primary
import com.infinity.savorlyapp.ui.theme.TextColor
import com.infinity.savorlyapp.ui.theme.background

@Composable
fun RecipeDetailsScreen(
    navController: NavHostController,
    recipeId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val recipe = viewModel.recipe

    LaunchedEffect(recipeId) {
        viewModel.loadRecipeById(recipeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe Details",
                style = MaterialTheme.typography.h4.copy(fontSize = 20.sp, color = Color.White)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }, backgroundColor = Primary
            )
        },
        backgroundColor = background
    ) { paddingValues ->
        recipe?.let { recipeItem ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Title
                Text(
                    text = recipeItem.title,
                    style = MaterialTheme.typography.h1.copy(color = Primary),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 20.dp)
                )

                if(!recipe.imageUri.isNullOrBlank()){
                    AsyncImage(
                        model = recipeItem.imageUri,
                        contentDescription = "Recipe Image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(20.dp))
                    )
                }else{
                    Image(painter = painterResource(id = R.drawable.default_image), contentDescription = "RecipeImage",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                                .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(20.dp)))
                }
                // Image
               

                Spacer(modifier = Modifier.height(30.dp))

                // Ingredients
                Text("Ingredients Used:", style = MaterialTheme.typography.h2.copy(fontSize = 20.sp, color = Primary))
                Text(
                    text = recipeItem.ingredients,
                    style = MaterialTheme.typography.h4.copy(color = TextColor),
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )

                // Instructions
                Text("Instructions:", style = MaterialTheme.typography.h2.copy(fontSize = 20.sp, color = Primary))
                Text(
                    text = recipeItem.instructions,
                    style = MaterialTheme.typography.h4.copy(color = TextColor),
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )

                // Category
                Text("Category:", style = MaterialTheme.typography.h2.copy(fontSize = 20.sp, color = Primary))
                Text("${recipeItem.category.displayName}", style = MaterialTheme.typography.h4, color = TextColor)

                // Rating
                Text("Rating:", style = MaterialTheme.typography.h2.copy(fontSize = 20.sp, color = Primary))
                Text("${recipeItem.rating} ⭐", style = MaterialTheme.typography.h4.copy(color = TextColor))
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
