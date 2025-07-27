package com.infinity.savorlyapp.uifiles.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinity.savorlyapp.data.local.model.RecipeCategory
import com.infinity.savorlyapp.data.local.model.RecipeEntity
import com.infinity.savorlyapp.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow<RecipeCategory?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _recipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val recipes: StateFlow<List<RecipeEntity>> = _recipes.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        observeRecipes()

    }

    private fun observeRecipes() {
        viewModelScope.launch {
            combine(
                repository.getAllRecipes(),
                _searchQuery,
                _selectedCategory
            ) { allRecipes, query, category ->
                allRecipes.filter { recipe ->
                    val matchesQuery = recipe.title.contains(query, ignoreCase = true) ||
                            recipe.ingredients.contains(query, ignoreCase = true)
//                    val matchesCategory = category == null || recipe.category == category
                    val matchesCategory = category == null || category == RecipeCategory.All || recipe.category == category
                    matchesQuery && matchesCategory
                }
            }.collect { filtered ->
                _recipes.value = filtered
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun deleteRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
        }
    }

    fun toggleFavorite(recipe: RecipeEntity) {
        viewModelScope.launch {
            val updatedRecipe = recipe.copy(isFavorite = !recipe.isFavorite)
            repository.upsertRecipe(updatedRecipe)
        }
    }

    fun setCategory(category: RecipeCategory?) {
        _selectedCategory.value = category
    }


}


