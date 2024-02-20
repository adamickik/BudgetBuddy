package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.shapes.WavyShape

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topbar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .shadow(
                elevation = 10.dp,
                shape = WavyShape(period = 900.dp, amplitude = 10.dp),
                clip = false
            )
            .clip(WavyShape(period=900.dp, amplitude=10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(WavyShape(period=500.dp, amplitude=10.dp))
                .background(MaterialTheme.colorScheme.primary),
            title = {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "BUDGET BUDDY")
                }
            },
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.mipmap.ic_logo_foreground),
                    contentDescription = "Main Menu",
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
        )
    }
}