package com.infinity.savorlyapp.di

import android.app.Application
import androidx.room.Room
import com.infinity.savorlyapp.data.local.dao.RecipeDao
import com.infinity.savorlyapp.data.local.db.RecipeDatabase
import com.infinity.savorlyapp.data.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecipeDatabase(app: Application): RecipeDatabase {
        return Room.databaseBuilder(
            app,
            RecipeDatabase::class.java,
            "recipe_db"
        ).fallbackToDestructiveMigration() // ⚠️ This will delete old data on version change
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeDao(db: RecipeDatabase): RecipeDao = db.recipeDao()

    @Provides
    @Singleton
    fun provideRecipeRepository(dao: RecipeDao): RecipeRepository = RecipeRepository(dao)
}
