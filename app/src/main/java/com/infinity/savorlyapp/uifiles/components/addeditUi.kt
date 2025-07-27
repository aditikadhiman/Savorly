package com.infinity.savorlyapp.uifiles.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infinity.savorlyapp.data.local.model.RecipeCategory
import com.infinity.savorlyapp.ui.theme.Primary
import com.infinity.savorlyapp.ui.theme.TextColor
import com.infinity.savorlyapp.ui.theme.TxtColor
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import com.infinity.savorlyapp.ui.theme.MutedText


@Composable
fun subHeading(value:String){
    Text(text = value,
    style = MaterialTheme.typography.h4.copy(fontSize = 16.sp, color = Primary))
}

@Composable
fun CustomOutlinedTextField(
    onTextSelected: (String) ->Unit,
    placeholder: String,
    errorStatus: Boolean = false,
    modifier: Modifier = Modifier
) {
    val textValue = rememberSaveable {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = textValue.value,
        onValueChange = {
                        textValue.value=it
            onTextSelected(it)
                        } ,
        placeholder = { Text(placeholder, color = TextColor) },
        isError = !errorStatus,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors=TextFieldDefaults.outlinedTextFieldColors(
            textColor = TextColor,
            cursorColor = Primary,
            backgroundColor = Color.White.copy(alpha = 0.5f),
            focusedBorderColor = Primary,
            unfocusedBorderColor = Primary
        ),
        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
}

@Composable
fun Buttton1(value:String,onClick:()-> Unit,isEnabled: Boolean = false){
    Button(onClick = { onClick.invoke()},
    modifier = Modifier
        .width(152.dp)
        .height(44.dp),
    shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Primary
        ),
        enabled = isEnabled
    ) {
        Text(text = value,
        style = MaterialTheme.typography.h3.copy(fontSize = 20.sp, color = if (isEnabled) TextColor else MutedText))
    }
}

@Composable
fun CategoryDropdown(
    selectedCategory: RecipeCategory?,
    onCategorySelected: (RecipeCategory?) -> Unit
) {
    var expanded = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded.value = !expanded.value }
                .background(Color.White.copy(alpha = 0.5f))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selectedCategory?.name ?: "Select Category",
                color = Primary
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Icon"
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.clip(RoundedCornerShape(20.dp))
        ) {
            DropdownMenuItem(onClick = {
                onCategorySelected(null) // Clear filter
                    expanded.value = false
            }) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("All Categories")
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                }
            }

            RecipeCategory.values().forEach { category ->
                DropdownMenuItem(onClick = {
                    onCategorySelected(category)
                    expanded.value = false
                }) {
                    Text(text = category.name)
                }
            }
        }
    }
}

@Composable
fun CustomOutlinedTextField1(
    initialText:String ="",
    onTextSelected: (String) ->Unit,
    placeholder: String,
    errorStatus: Boolean = false,
    modifier: Modifier = Modifier
) {
    val textValue = rememberSaveable (initialText){
        mutableStateOf(initialText)
    }

    OutlinedTextField(
        value = textValue.value,
        onValueChange = {
            textValue.value=it
            onTextSelected(it)
        } ,
        placeholder = { Text(placeholder, color = TextColor) },
        isError = !errorStatus,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors=TextFieldDefaults.outlinedTextFieldColors(
            textColor = TextColor,
            cursorColor = Primary,
            backgroundColor = Color.White.copy(alpha = 0.5f),
            focusedBorderColor = Primary,
            unfocusedBorderColor = Primary
        ),
        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
    )
}

//CustomOutlinedTextField(
//    value = title,
//    onValueChange = { title = it },
//    label = "Recipe Title",
//    placeholder = "Enter title",
//    isError = !Validator.validateNotEmpty(title)
//)
//
//Spacer(modifier = Modifier.height(16.dp))
//
//CustomOutlinedTextField(
//    value = rating,
//    onValueChange = { rating = it },
//    label = "Rating",
//    placeholder = "Enter rating from 1 to 5",
//    isError = !Validator.validateRating(rating)
//)

//
//val context = LocalContext.current
//val imageUri = remember { mutableStateOf<Uri?>(null) }
//
//val launcher = rememberLauncherForActivityResult(
//    contract = ActivityResultContracts.GetContent()
//) { uri: Uri? ->
//    imageUri.value = uri
//}
//
//Button(onClick = { launcher.launch("image/*") }) {
//    Text("Choose Image")
//}


//package com.infinity.savorly.utils
//
//object Validator {
//
//    // Validates that a string is not blank or only whitespace
//    fun validateNotEmpty(input: String): Boolean {
//        return input.trim().isNotEmpty()
//    }
//
//    // Validates rating is a number from 1 to 5 (inclusive)
//    fun validateRating(input: String): Boolean {
//        val rating = input.toIntOrNull()
//        return rating != null && rating in 1..5
//    }
//}

//val isTitleValid = Validator.validateNotEmpty(titleInput)
//val isRatingValid = Validator.validateRating(ratingInput)
//
//if (!isTitleValid) {
//    _uiState.value = uiState.value.copy(errorMessage = "Title cannot be empty")
//} else if (!isRatingValid) {
//    _uiState.value = uiState.value.copy(errorMessage = "Rating must be between 1 and 5")
//}