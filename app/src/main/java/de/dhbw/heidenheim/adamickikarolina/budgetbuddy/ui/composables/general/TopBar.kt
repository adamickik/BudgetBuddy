package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.shapes.WavyShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topbar(onDialogButtonClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .shadow(
                elevation = 10.dp,
                shape = WavyShape(period = 800.dp, amplitude = 10.dp),
                clip = false
            )
            .clip(WavyShape(period = 800.dp, amplitude = 10.dp)),
        title = {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id=R.string.topBar_name))
            }
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable(onClick = onDialogButtonClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.ic_logo_foreground),
                    contentDescription = stringResource(id = R.string.topBar_description)
                )
            }
        },
        // Change color of TopBar, otherwise surface -> same as Cards
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}