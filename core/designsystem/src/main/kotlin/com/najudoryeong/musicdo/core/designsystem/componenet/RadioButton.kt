package com.najudoryeong.musicdo.core.designsystem.componenet

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import com.najudoryeong.musicdo.core.designsystem.theme.spacing

@Composable
fun RadioButtonText(
    @StringRes textRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: RadioButtonColors = RadioButtonDefaults.colors()
) {
    Row(
        modifier = modifier
            .selectable(
                selected = isSelected,
                role = Role.RadioButton,
                onClick = onClick
            )
            .padding(MaterialTheme.spacing.smallMedium)
            .fillMaxWidth()
    ) {
        RadioButton(selected = isSelected, onClick = null, colors = colors)
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        SingleLineText(text = stringResource(id = textRes), shouldUseMarquee = true)
    }
}
