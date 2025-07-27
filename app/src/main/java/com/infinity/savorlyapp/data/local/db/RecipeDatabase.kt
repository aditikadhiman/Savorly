package com.infinity.savorlyapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.infinity.savorlyapp.data.local.dao.RecipeDao
import com.infinity.savorlyapp.data.local.model.RecipeEntity
import com.infinity.savorlyapp.utils.Converters

@Database(entities = [RecipeEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
