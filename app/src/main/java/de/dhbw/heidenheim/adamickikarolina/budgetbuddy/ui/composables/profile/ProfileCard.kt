package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.profile

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import java.io.File
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp



@Preview
@Composable
fun ProfileCard(){
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("BudgetBuddyPrefs", Context.MODE_PRIVATE)
    val username = prefs.getString("username", null).toString().uppercase()
    val profilePicture = prefs.getString("profilePicture", null).toString()
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top=20.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            // TODO Change with actual name
            text = stringResource(R.string.profile_greeting, username),
            style = MaterialTheme.typography.headlineLarge,

            modifier = Modifier
                .padding(start = 15.dp,
                    top = 10.dp,
                    end = 10.dp,
                    bottom = 10.dp)
                .weight(1f),
        )
        if (profilePicture != "null") { // Änderung: Prüfen Sie auf den String "null"
            Image(
                painter = rememberAsyncImagePainter(Uri.fromFile(File(context.filesDir, profilePicture))),
                contentDescription = stringResource(R.string.profileImage_description),
                modifier = Modifier
                    .size(100.dp) // Setzt die Größe des Bildes
                    .clip(CircleShape) // Macht das Bild rund
            )
        } else {
            // Zeige ein Standardbild, wenn kein Bild ausgewählt ist
            Image(
                painter = painterResource(R.mipmap.ic_logo_foreground),
                contentDescription = stringResource(R.string.profileImage_description),
                modifier = Modifier
                    .size(100.dp) // Setzt die Größe des Bildes
                    .clip(CircleShape) // Macht das Bild rund
            )
        }
    }
}