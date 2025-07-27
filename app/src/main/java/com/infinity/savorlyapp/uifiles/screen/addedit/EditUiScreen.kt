package com.infinity.savorlyapp.uifiles.screen.addedit

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.infinity.savorlyapp.R
import com.infinity.savorlyapp.data.local.model.RecipeCategory
import com.infinity.savorlyapp.navigation.Screen
import com.infinity.savorlyapp.state.RecipeEvent
import com.infinity.savorlyapp.state.RecipeUiState
import com.infinity.savorlyapp.ui.theme.Primary
import com.infinity.savorlyapp.ui.theme.TxtColor
import com.infinity.savorlyapp.ui.theme.background
import com.infinity.savorlyapp.uifiles.components.*
import com.infinity.savorlyapp.uifiles.screen.home.HomeViewModel

@Composable
fun EditUiScreen(
    navController: NavController,
    recipeId: String
) {


    val viewModel: AddEditViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    var recipeUIState = mutableStateOf(RecipeUiState())
    val recipeState = viewModel.recipeUIState.value

    val navigateToHome = viewModel.navigateToHome.value

    // Load recipe only once
    LaunchedEffect(recipeId) {
        viewModel.loadRecipeById(recipeId)
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri.value = it

            // ✅ Persist permission
            val contentResolver = context.contentResolver
            try {
                contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }

            // ✅ Save URI in ViewModel
            viewModel.onEvent(RecipeEvent.ImageUriChanged(it.toString()))
        }
    }

    LaunchedEffect(navigateToHome) {
        if (navigateToHome) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.EditUiScreen.route) { inclusive = true }
            }
            viewModel.resetNavigation()
        }
    }

    Scaffold(backgroundColor = background) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Heading(value = stringResource(id = R.string.edit_recipe))
            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = { launcher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Primary,
                        contentColor = TxtColor
                    )
                ) {
                    Text("Choose Image")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            subHeading(value = stringResource(id = R.string.title))
            Spacer(modifier = Modifier.height(10.dp))
            CustomOutlinedTextField1(
                initialText = recipeState.title,
                onTextSelected = { viewModel.onEvent(RecipeEvent.TitleChanged(it)) },
                placeholder = "Name of the Dish",
                errorStatus = recipeState.titleError
            )

            Spacer(modifier = Modifier.height(15.dp))
            subHeading(value = stringResource(id = R.string.ingredient))
            Spacer(modifier = Modifier.height(10.dp))
            CustomOutlinedTextField1(
                initialText = recipeState.ingredients,
                onTextSelected = { viewModel.onEvent(RecipeEvent.IngredientsChanged(it)) },
                placeholder = "List of Ingredients used",
                errorStatus = recipeState.ingredientsError
            )

            Spacer(modifier = Modifier.height(15.dp))
            subHeading(value = stringResource(id = R.string.Instructions))
            Spacer(modifier = Modifier.height(10.dp))
            CustomOutlinedTextField1(
                initialText = recipeState.instructions,
                onTextSelected = { viewModel.onEvent(RecipeEvent.InstructionsChanged(it)) },
                placeholder = "Steps for cooking",
                errorStatus = recipeState.instructionsError,
                modifier = Modifier.height(100.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))
            subHeading(value = stringResource(id = R.string.Category))
            Spacer(modifier = Modifier.height(10.dp))

            CategoryDropdown(
                selectedCategory = recipeState.category ?: RecipeCategory.BREAKFAST,
                onCategorySelected = { selected ->
                    viewModel.onEvent(RecipeEvent.CategoryChanged(selected ?: RecipeCategory.BREAKFAST))
                }
            )



            Spacer(modifier = Modifier.height(15.dp))
            subHeading(value = stringResource(id = R.string.ratings))
            Spacer(modifier = Modifier.height(10.dp))
            CustomOutlinedTextField1(
                initialText = recipeState.rating,
                onTextSelected = { viewModel.onEvent(RecipeEvent.RatingChanged(it)) },
                placeholder = "Rate out of 5",
                errorStatus = recipeState.ratingError
            )

            Spacer(modifier = Modifier.height(30.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Buttton1(
                    value = "Save",
                    onClick = {
                        recipeState.id?.let {
                            viewModel.onEvent(RecipeEvent.UpdateRecipeClicked(id = recipeState.id ?: ""))
                        }

                    },
                    isEnabled = viewModel.allValidationPassed.value
                )

                Buttton1(value = "Cancel", onClick = {
                    navController.popBackStack()
                }, isEnabled = true)
            }
        }

        if (viewModel.saveInPrgress.value) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                    color = Primary
                )
            }
        }
    }
}

