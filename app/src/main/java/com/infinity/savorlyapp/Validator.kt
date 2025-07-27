package com.infinity.savorlyapp


object Validator {

    fun validateTitle(fTitle: String) :ValidationResult{
        return ValidationResult(
            status=fTitle.length>=1,
            errorMessage = if(fTitle.length<1) "Title is mandatory" else null
        )
    }

    fun validateIngredients(fIngredient: String): ValidationResult{
        return ValidationResult(
            status = fIngredient.length>=1,
            errorMessage = if(fIngredient.length<1) "Can't be empty" else null
        )
    }
    fun validateInst(fInst: String): ValidationResult{
        return ValidationResult(
            status = fInst.length>=1,
            errorMessage = if(fInst.length<1) "Can't be empty" else null
        )
    }

    fun validateRatings(ratings: String): ValidationResult {
        val ratingInt = ratings.toIntOrNull()
        return when {
            ratings.isBlank() -> ValidationResult(
                status = false,
                errorMessage = "Rating is required"
            )
            ratingInt == null -> ValidationResult(
                status = false,
                errorMessage = "Rating must be a number"
            )
            ratingInt !in 1..5 -> ValidationResult(
                status = false,
                errorMessage = "Rating must be between 1 and 5"
            )
            else -> ValidationResult(
                status = true
            )
        }
    }




    data class ValidationResult(
        val status: Boolean,
        val errorMessage: String? = null
    )

}
