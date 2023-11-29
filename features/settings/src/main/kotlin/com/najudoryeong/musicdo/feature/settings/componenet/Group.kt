package com.najudoryeong.musicdo.feature.settings.componenet

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.najudoryeong.musicdo.core.designsystem.componenet.DoOutlinedBorder
import com.najudoryeong.musicdo.core.designsystem.componenet.SingleLineText
import com.najudoryeong.musicdo.core.designsystem.theme.spacing

internal fun LazyListScope.group(
    @StringRes titleResource: Int,
    content: @Composable ColumnScope.() -> Unit
) = item {
    Group(titleResource = titleResource, content = content)
}

@Composable
private fun Group(
    @StringRes titleResource: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    OutlinedCard(
        modifier = modifier
            .padding(MaterialTheme.spacing.small)
            .fillMaxWidth(),
        border = DoOutlinedBorder
    ) {
        Column(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            SingleLineText(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                text = stringResource(id = titleResource),
                shouldUseMarquee = true,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            content()
        }
    }
}
