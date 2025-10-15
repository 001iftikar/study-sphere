package com.iftikar.studysphere.presentation.role

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.SubcomposeAsyncImage
import com.iftikar.studysphere.R
import com.iftikar.studysphere.presentation.navigation.Routes
import com.iftikar.studysphere.ui.theme.RoleButtonBackGround
import com.iftikar.studysphere.ui.theme.RoleScreenBackground

@Composable
fun RoleChooseScreen(
    navHostController: NavHostController
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = RoleScreenBackground,
                        end = Offset(0f, Float.POSITIVE_INFINITY),
                        start = Offset(Float.POSITIVE_INFINITY, 0f)
                    )
                )
        ) {
            Text(
                text = "Welcome to Study Sphere",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 144.dp, start = 40.dp),
                fontFamily = FontFamily(Font(R.font.role_screen_welcome)),
                color = Color.White,
                style = MaterialTheme.typography.displayMedium
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
            ) {
                RoleSelectionItem(
                    icon = R.drawable.admin,
                    text = "I am an admin",
                    onClick = {navHostController.navigate(Routes.AdminLoginScreenRoute)}
                )
                RoleSelectionItem(
                    icon = R.drawable.teacher,
                    text = "I am a teacher",
                    onClick = {}
                )
                RoleSelectionItem(
                    icon = R.drawable.student,
                    text = "I am a student",
                    onClick = {}
                )
            }
        }
    }
}


@Composable
private fun RoleSelectionItem(
    icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.linearGradient(RoleButtonBackGround)
            )
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(
            space = Dp(6f),
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(all = 6.dp)
                .size(35.dp),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(6.dp)
        )
    }
}