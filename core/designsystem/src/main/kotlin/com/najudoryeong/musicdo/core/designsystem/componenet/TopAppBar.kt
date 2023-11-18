package com.najudoryeong.musicdo.core.designsystem.componenet

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.najudoryeong.musicdo.core.designsystem.theme.DoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoTopAppBar(
    @StringRes titleResource: Int,
    shouldShowBackButton: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {
        AnimatedVisibility(
            visible = shouldShowBackButton,
            enter = BackButtonEnterTransition,
            exit = BackButtonExitTransition
        ) {
            DoBackButton(onClick = onBackClick)
        }
    },
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(TopAppBarElevation)
    )
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = titleResource)) },
        navigationIcon = navigationIcon,
        colors = colors
    )
}

private val TopAppBarElevation = 3.dp
private val BackButtonEnterTransition = fadeIn() + expandHorizontally()
private val BackButtonExitTransition = shrinkHorizontally() + fadeOut()


@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
fun DoTopAppBarPreview() {
    DoTheme {
        DoTopAppBar(
            titleResource =  android.R.string.untitled,
            shouldShowBackButton = true,
            onBackClick = { },
            modifier = Modifier
        )
    }
}

