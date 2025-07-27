package com.infinity.savorlyapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val instructions: String,
    val ingredients: String,
    val imageUri: String? = null,
    val isFavorite: Boolean = false,
    val category: RecipeCategory =  RecipeCategory.BREAKFAST, // store category as String (RecipeCategory.BREAKFAST.name)
    val rating: String
)

//@Composable
//fun CategoryDropdown(
//    selectedCategory: RecipeCategory,
//    onCategorySelected: (RecipeCategory) -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//
//    Box {
//        OutlinedButton(onClick = { expanded = true }) {
//            Text(text = selectedCategory.displayName)
//        }
//
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            RecipeCategory.values().forEach { category ->
//                DropdownMenuItem(onClick = {
//                    onCategorySelected(category)
//                    expanded = false
//                }) {
//                    Text(text = category.displayName)
//                }
//            }
//        }
//    }
//}
