package com.infinity.savorlyapp.uifiles.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.infinity.savorlyapp.R
import com.infinity.savorlyapp.data.local.model.RecipeEntity
import com.infinity.savorlyapp.ui.theme.MutedText
import com.infinity.savorlyapp.ui.theme.Primary
import com.infinity.savorlyapp.ui.theme.TextColor
import com.infinity.savorlyapp.ui.theme.TxtColor
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder

@Composable
fun Heading(value: String) {
    Text(
        text = value,
        style = MaterialTheme.typography.h1.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 34.sp,
            color = Primary
        )
    )
}

@Composable
fun SearchBar(
    value: String,
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .width(359.dp)
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
//        Icon(imageVector = Icons.Default.Search, contentDescription = "search")
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChanged,
            placeholder = {
                Text(
                    value,
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 16.sp,
                        color = MutedText,
                        textAlign = TextAlign.Center
                    )
                )
            },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.Black.copy(alpha = 0.5f),
                cursorColor = Color.White,
                textColor = Color.White,
                focusedBorderColor = Primary
            )
        )
    }
}

@Composable
fun Tabs(value: String,isSelected: Boolean =false, onClick: () -> Unit) {
    val colorcustom = if(isSelected) Primary else TxtColor
    TextButton(modifier = Modifier
        .width(75.dp)
        .height(37.dp),
        colors = ButtonDefaults.textButtonColors(backgroundColor = colorcustom, contentColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        onClick = { onClick() }) {
        Text(
            text = value,
            style = MaterialTheme.typography.h3.copy(fontSize = 13.sp, color = TextColor)
        )
    }
}

@Composable
fun partition() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(13.dp)
            .background(color = Primary)
    )
}

@Composable
fun RecipeCards(
    recipe: RecipeEntity,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onFav: () ->Unit,
    default_image: Painter
) {
    Card(modifier = Modifier
        .width(173.dp)
        .heightIn(263.dp)
        .clickable { onClick() },
    elevation =8.dp,
    shape = RoundedCornerShape(20.dp),
        backgroundColor = TxtColor
    ){
        Column (verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(5.dp)
        ){
            //Image
            if(!recipe.imageUri.isNullOrBlank()){
                AsyncImage(model =  Uri.parse(recipe.imageUri), contentDescription = "RecipeImage",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(159.dp)
                    .height(119.dp)
                    .clip(RoundedCornerShape(20.dp)))
            }else{
                Image(painter = default_image, contentDescription = "RecipeImage",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(159.dp)
                        .height(119.dp)
                        .clip(RoundedCornerShape(20.dp)))
            }
            Spacer(modifier = Modifier.height(5.dp))

            Text(text = recipe.title,
            style=MaterialTheme.typography.h2.copy(fontSize = 24.sp, color = TextColor),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Category: ${recipe.category}",
            style = MaterialTheme.typography.h4.copy(fontSize = 12.sp,color= TextColor))

            Text(text = "Rating: ${recipe.rating}/5",
                style = MaterialTheme.typography.h4.copy(fontSize = 12.sp,color= TextColor))


            // Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onFav) {
                    Icon(
                        imageVector =  if (recipe.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (recipe.isFavorite) "Unfavorite" else "Favorite",
                        tint = if(recipe.isFavorite) Primary else Color.Gray
                    )
                }
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }
    }
}


@Composable
fun BottomBarComponent(
    selectedTab: String = "Home",
    onClickHome: () -> Unit,
    onClickFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        backgroundColor = Primary,
        elevation = 12.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Half
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                BottomBarItem(
                    icon = Icons.Default.Home,
                    label = "Home",
                    isSelected = selectedTab == "Home",
                    onClick = onClickHome
                )
            }

            // Right Half
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                BottomBarItem(
                    icon = Icons.Default.Favorite,
                    label = "Favorites",
                    isSelected = selectedTab == "Favorites",
                    onClick = onClickFavorite
                )
            }
        }
    }
}

@Composable
fun BottomBarItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val contentColor = if (isSelected) TxtColor else TextColor

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(64.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onClick() }) // 👈 No ripple, no effect
            }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.caption.copy(
                fontSize = 12.sp,
                color = contentColor
            )
        )
    }
}

@Composable
fun EmptyStateComponent(
    message: String = "No recipes found",
    imageRes: Int = R.drawable.default_image // your placeholder image (e.g., an empty box)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Empty State",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Primary
        )
    }
}