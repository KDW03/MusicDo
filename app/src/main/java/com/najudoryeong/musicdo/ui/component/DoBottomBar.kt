package com.najudoryeong.musicdo.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.najudoryeong.musicdo.core.designsystem.componenet.DoNavigationBar
import com.najudoryeong.musicdo.core.designsystem.componenet.DoNavigationBarItem
import com.najudoryeong.musicdo.navigation.TopLevelDestination


@Composable
fun DoBottomBar(
    destinations: Array<TopLevelDestination>,
    currentDestination: NavDestination?,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    DoNavigationBar(modifier = modifier) {
        destinations.forEach { destination ->
            DoNavigationBarItem(
                selected = currentDestination.isTopLevelDestinationInHierarchy(destination),
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.icon.imageVector,
                        contentDescription = stringResource(id = destination.titleResource)
                    )
                },
                label = { Text(text = stringResource(id = destination.titleResource)) }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any { it.route == destination.route } == true
