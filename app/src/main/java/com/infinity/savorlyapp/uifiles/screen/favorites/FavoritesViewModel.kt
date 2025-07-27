package com.infinity.savorlyapp.uifiles.screen.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinity.savorlyapp.data.local.model.RecipeEntity
import com.infinity.savorlyapp.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val favorites: StateFlow<List<RecipeEntity>> = _favorites.asStateFlow()

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            repository.getAllRecipes().collect { allRecipes ->
                _favorites.value = allRecipes.filter { it.isFavorite }
            }

        }
    }


}
