package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R


@Preview
@Composable
fun ProfileCard(){
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top=20.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            // TODO Change with actual name
            text = stringResource(R.string.profile_greeting, "KAROLINA"),
            style = MaterialTheme.typography.headlineLarge,

            modifier = Modifier
                .padding(start = 15.dp,
                    top = 10.dp,
                    end = 10.dp,
                    bottom = 10.dp)
                .weight(1f),
        )
        Image(
            painter = painterResource(R.mipmap.ic_logo_foreground),
            contentDescription = stringResource(R.string.profileImage_description)
        )
    }
}