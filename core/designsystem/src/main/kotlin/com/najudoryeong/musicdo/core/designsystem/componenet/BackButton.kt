package com.najudoryeong.musicdo.core.designsystem.componenet

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.najudoryeong.musicdo.core.designsystem.R
import com.najudoryeong.musicdo.core.designsystem.icon.DoIcons

@Composable
internal fun DoBackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            imageVector = DoIcons.ArrowBack.imageVector,
            contentDescription = stringResource(id = R.string.back)
        )
    }
}


