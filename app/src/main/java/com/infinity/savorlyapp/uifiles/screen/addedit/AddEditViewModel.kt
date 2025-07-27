package com.infinity.savorlyapp.uifiles.screen.addedit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinity.savorlyapp.Validator
import com.infinity.savorlyapp.data.local.model.RecipeCategory
import com.infinity.savorlyapp.data.local.model.RecipeEntity
import com.infinity.savorlyapp.data.repository.RecipeRepository
import com.infinity.savorlyapp.state.RecipeEvent
import com.infinity.savorlyapp.state.RecipeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.UUID

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repository: RecipeRepository
): ViewModel() {

    var recipeUIState = mutableStateOf(RecipeUiState())
    var allValidationPassed = mutableStateOf(false)
    var saveInPrgress = mutableStateOf(false)

    private val _navigateToHome = mutableStateOf(false)
    val navigateToHome: State<Boolean> = _navigateToHome

    private val _toastMessage = mutableStateOf<String?>(null)
    val toastMessage: State<String?> = _toastMessage

    fun onEvent(event: RecipeEvent){
        validateRecipeUIDataWithRules()
        when(event){
            is RecipeEvent.TitleChanged ->{
                recipeUIState.value = recipeUIState.value.copy(
                    title = event.title
                )
            }
            is RecipeEvent.IngredientsChanged ->{
                recipeUIState.value = recipeUIState.value.copy(
                    ingredients = event.ingredients
                )
            }
            is RecipeEvent.InstructionsChanged ->{
                recipeUIState.value=recipeUIState.value.copy(
                    instructions = event.instructions
                )
            }
            is RecipeEvent.ImageUriChanged ->{
                recipeUIState.value = recipeUIState.value.copy(
                    imageUri = event.uri
                )
            }
            is RecipeEvent.CategoryChanged ->{
                recipeUIState.value = recipeUIState.value.copy(
                    category = event.category
                )
            }
            is RecipeEvent.RatingChanged ->{
                recipeUIState.value=recipeUIState.value.copy(
                    rating = event.rating
                )
            }
            is RecipeEvent.UpdateRecipeClicked -> {
                updateRecipe(event.id)
            }
            is RecipeEvent.SaveButtonClicked ->{
                SaveRecipe()
            }
            else -> {}
        }
    }

    private fun validateRecipeUIDataWithRules(){
        val titleResult = Validator.validateTitle(
            fTitle = recipeUIState.value.title
        )

        val ingredientResult = Validator.validateIngredients(
            fIngredient = recipeUIState.value.ingredients
        )

        val instructionResult = Validator.validateInst(
            fInst = recipeUIState.value.instructions
        )

        val ratingResult = Validator.validateRatings(
            ratings = recipeUIState.value.rating
        )

        allValidationPassed.value=titleResult.status && ingredientResult.status && instructionResult.status && ratingResult.status
    }

    private fun SaveRecipe() {
        saveInPrgress.value = true

        // Check if all validation passed before saving
//        if (!allValidationPassed.value) {
//            _toastMessage.value = "Please fill all fields correctly"
//            saveInPrgress.value = false
//            return
//        }

        viewModelScope.launch {
            val recipe = RecipeEntity(
                id =  recipeUIState.value.id ?: UUID.randomUUID().toString(),
                title = recipeUIState.value.title.trim(),
                instructions = recipeUIState.value.instructions.trim(),
                ingredients = recipeUIState.value.ingredients.trim(),
                imageUri = recipeUIState.value.imageUri ?: "",
                isFavorite = false,
                category = recipeUIState.value.category ?: RecipeCategory.BREAKFAST,
                rating = recipeUIState.value.rating
            )

            repository.upsertRecipe(recipe)


            // Mark navigation and show success toast
            _toastMessage.value = "Recipe Saved"
            _navigateToHome.value = true
            saveInPrgress.value = false
        }
    }

    fun resetNavigation() {
        _navigateToHome.value = false
    }


    fun loadRecipeById(id: String) {
        viewModelScope.launch {
            val recipe = repository.getRecipeById(id)
            recipe?.let {
                recipeUIState.value = recipeUIState.value.copy(
                    id =it.id,
                    title = it.title,
                    ingredients = it.ingredients,
                    instructions = it.instructions,
                    rating = it.rating,
                    imageUri = it.imageUri,
                    titleError = false,
                    ingredientsError = false,
                    instructionsError = false,
                    ratingError = false
                )
            }
        }
    }
    private fun updateRecipe(id: String) {
        viewModelScope.launch {
            repository.updateRecipe(
                RecipeEntity(
                    id = id,
                    title = recipeUIState.value.title.trim(),
                    ingredients = recipeUIState.value.ingredients.trim(),
                    instructions = recipeUIState.value.instructions.trim(),
                    rating = recipeUIState.value.rating,
                    imageUri = recipeUIState.value.imageUri ?: "", // retain image
                    category = recipeUIState.value.category ?: RecipeCategory.BREAKFAST, // ✅ include category
                    isFavorite = repository.getRecipeById(id)?.isFavorite ?: false // ✅ retain favorite status
                )
            )
            _navigateToHome.value = true
        }
    }



}

//@HiltViewModel
//class AddEditViewModel @Inject constructor(
//    private val repository: RecipeRepository
//) : ViewModel() {
//
//    private val _title = MutableStateFlow("")
//    val title: StateFlow<String> = _title
//
//    private val _description = MutableStateFlow("")
//    val description: StateFlow<String> = _description
//
//    private val _ingredients = MutableStateFlow("")
//    val ingredients: StateFlow<String> = _ingredients
//
//    private val _imageUri = MutableStateFlow<String?>(null)
//    val imageUri: StateFlow<String?> = _imageUri
//
//    private val _category = MutableStateFlow<RecipeCategory?>(null)
//    val category: StateFlow<RecipeCategory?> = _category
//
////    private val _rating = MutableStateFlow(0)
////    val rating: StateFlow<Int> = _rating
//
//    private val _rating = MutableStateFlow("")
//    val rating: StateFlow<String> = _rating
//
//    fun onRatingChange(newRating: String) {
//        _rating.value = newRating
//    }
//
//
//    private var currentRecipeId: String? = null
//
//    fun onTitleChange(newTitle: String) {
//        _title.value = newTitle
//    }
//
//    fun onDescriptionChange(newDescription: String) {
//        _description.value = newDescription
//    }
//
//    fun onIngredientsChange(newIngredients: String) {
//        _ingredients.value = newIngredients
//    }
//
//    fun onImageUriChange(newUri: String?) {
//        _imageUri.value = newUri
//    }
//
//    fun onCategoryChange(newCategory: RecipeCategory?) {
//        _category.value = newCategory
//    }
//
////    fun onRatingChange(newRating: Int) {
////        _rating.value = newRating
////    }
//
////    fun loadRecipeForEdit(recipeId: String) {
////        viewModelScope.launch {
////            val recipe = repository.getRecipeById(recipeId)
////            recipe?.let {
////                currentRecipeId = it.id
////                _title.value = it.title
////                _description.value = it.instructions
////                _ingredients.value = it.ingredients
////                _imageUri.value = it.imageUri
////                _category.value = it.category
////                _rating.value = it.rating
////            }
////        }
////    }
//
////    fun saveRecipe(onDone: () -> Unit) {
////        viewModelScope.launch {
////            val recipe = RecipeEntity(
////                id = currentRecipeId ?: UUID.randomUUID().toString(),
////                title = title.value.trim(),
////                instructions = description.value.trim(),
////                ingredients = ingredients.value.trim(),
////                imageUri = imageUri.value,
////                isFavorite = false, // Optional: update if you allow setting favorite in add/edit
////                category = category.value ?: RecipeCategory.BREAKFAST,
////                rating = rating.value
////            )
////            repository.upsertRecipe(recipe)
////            onDone()
////        }
////    }

//}
