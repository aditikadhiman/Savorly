package com.infinity.savorlyapp.utils

import androidx.room.TypeConverter
import com.infinity.savorlyapp.data.local.model.RecipeCategory

class Converters {

    @TypeConverter
    fun fromCategory(category: RecipeCategory): String = category.name

    @TypeConverter
    fun toCategory(value: String): RecipeCategory = RecipeCategory.valueOf(value)
}
