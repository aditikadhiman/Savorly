package com.infinity.savorlyapp.uifiles.screen.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinity.savorlyapp.data.local.model.RecipeEntity
import com.infinity.savorlyapp.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    var recipe by mutableStateOf<RecipeEntity?>(null)
        private set

    fun loadRecipeById(recipeId: String) {
        viewModelScope.launch {
            recipe = repository.getRecipeById(recipeId)
        }
    }
}
