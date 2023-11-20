package com.najudoryeong.musicdo.core.ui.component

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.najudoryeong.musicdo.core.designsystem.componenet.DoImage
import com.najudoryeong.musicdo.core.designsystem.icon.DoIcons

@Composable
fun DoArtworkImage(
    artworkUri: Uri,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(containerColor = Color.Transparent),
    placeholder: @Composable () -> Unit = {
        Card(
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = DoIcons.Music.resourceId),
                contentDescription = contentDescription
            )
        }
    },
    contentScale: ContentScale = ContentScale.Crop
) {
    DoImage(
        modifier = modifier,
        model = artworkUri,
        contentDescription = contentDescription,
        shape = shape,
        colors = colors,
        loading = { placeholder() },
        error = { placeholder() },
        contentScale = contentScale
    )
}
