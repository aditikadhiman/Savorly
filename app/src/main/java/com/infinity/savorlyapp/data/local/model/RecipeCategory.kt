package com.infinity.savorlyapp.data.local.model

enum class RecipeCategory(val displayName: String) {
    All("All"),
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    DESSERT("Dessert");

    companion object {
        fun fromDisplayName(name: String): RecipeCategory {
            return values().find { it.displayName.equals(name, ignoreCase = true) }
                ?: BREAKFAST
        }
    }
}
