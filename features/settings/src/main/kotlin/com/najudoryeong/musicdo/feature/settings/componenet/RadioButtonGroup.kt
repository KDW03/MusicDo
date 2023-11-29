package com.najudoryeong.musicdo.feature.settings.componenet

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.najudoryeong.musicdo.core.designsystem.componenet.SingleLineText
import com.najudoryeong.musicdo.core.designsystem.icon.Icon
import com.najudoryeong.musicdo.core.designsystem.theme.spacing

@Composable
internal fun RadioButtonGroup(
    icon: Icon,
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier.selectableGroup()) {
        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            IconBox(icon = icon, contentDescriptionResource = titleRes)

            SingleLineText(
                text = stringResource(id = titleRes),
                shouldUseMarquee = true,
                style = MaterialTheme.typography.titleMedium
            )
        }

        content()
    }
}
