package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.composables.templates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextIconButton(
    buttonText: String,
    iconContentDescription: String,
    onIconClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(
                start = 15.dp,
                top = 10.dp,
                end=15.dp,
                bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.titleLarge
        )
        Box(
            Modifier.weight(1f, true)
        )
        FilledIconButton(
            onClick = onIconClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = iconContentDescription
            )
        }
    }
}