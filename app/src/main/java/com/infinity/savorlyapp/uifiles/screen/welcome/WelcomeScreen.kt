package com.infinity.savorlyapp.uifiles.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.infinity.savorlyapp.R
import com.infinity.savorlyapp.navigation.Screen
import com.infinity.savorlyapp.ui.theme.*

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween // So image sticks to bottom
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.savorly),
                    style = MaterialTheme.typography.h1.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 34.sp,
                        color = Primary
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(id = R.string.welcometext),
                    style = MaterialTheme.typography.h3.copy(
                        fontSize = 42.sp,
                        color = TextColor
                    )
                )

                Spacer(modifier = Modifier.height(25.dp))

                // Button centered horizontally
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick= {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Welcome.route) { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .heightIn(44.dp),
                        colors = ButtonDefaults.buttonColors(Primary),
                        shape = RoundedCornerShape(20.dp),
                        elevation = ButtonDefaults.elevation(10.dp),
                        contentPadding = PaddingValues()
                    ) {
                        Text(
                            text = stringResource(id = R.string.getStarted),
                            style = MaterialTheme.typography.h3.copy(
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        )
                    }
                }
            }

            // No padding/margin on image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .clip(RoundedCornerShape(topStart = 200.dp, topEnd = 200.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.welcome_image),
                    contentDescription = "WelcomeScreenImage",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
