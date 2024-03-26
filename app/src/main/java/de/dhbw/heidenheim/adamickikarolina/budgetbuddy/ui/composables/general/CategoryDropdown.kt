package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ChartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropDown(
    selectedCategoryId: Int?,
    onCategorySelected: (Int?)-> Unit
) {
    val chartViewModel = hiltViewModel<ChartViewModel>()

    val categories by chartViewModel.categories.observeAsState(emptyList())
    var selectedCategory by remember { mutableStateOf(categories.find { it.kId == selectedCategoryId }) }

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedCategory?.kName ?: "Kategorie",
            onValueChange = { },
            label = {stringResource(id = R.string.categories_name) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            categories.drop(1).forEach { category ->
                DropdownMenuItem(
                    text = { Text(text = category.kName) },
                    onClick = {
                        selectedCategory = category
                        onCategorySelected(category.kId)
                        expanded = false
                    }
                )
            }
        }
    }
}