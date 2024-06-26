package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.composables.templates

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

data class DropdownEntry(val id: Int, val name: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    entries: List<DropdownEntry>,
    selectedEntryId: Int?,
    onEntrySelected: (Int?) -> Unit,
    defaultDisplayText: String = "Option"
) {
    var selectedEntry by remember { mutableStateOf(entries.find { it.id == selectedEntryId }) }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedEntry?.name ?: defaultDisplayText,
            onValueChange = { },
            label = { Text(defaultDisplayText) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            entries.drop(1).forEach { entry ->
                DropdownMenuItem(
                    text = { Text(text = entry.name) },
                    onClick = {
                        selectedEntry = entry
                        onEntrySelected(entry.id)
                        expanded = false
                    }
                )
            }
        }
    }
}