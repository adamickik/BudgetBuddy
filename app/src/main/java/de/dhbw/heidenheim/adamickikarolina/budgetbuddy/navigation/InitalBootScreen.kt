import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberImagePainter
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.theme.BudgetBuddyTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.io.FileOutputStream
import java.io.InputStream

fun saveImageFromUriToInternalStorage(context: Context, imageUri: Uri, fileName: String) {
    val contentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
    val fileOutputStream: FileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)

    inputStream?.use { input ->
        fileOutputStream.use { output ->
            input.copyTo(output)
        }
    }
}

@Composable
fun InitialBootScreen(onSetupComplete: () -> Unit) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Erstelle einen Launcher für die Bildauswahl-Aktivität
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri // Aktualisiere den URI nach Auswahl
    }
    BudgetBuddyTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Benutzername") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            if (selectedImageUri != null) {
                Image(
                    painter = rememberImagePainter(selectedImageUri),
                    contentDescription = "Ausgewähltes Bild",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Button(
                onClick = { launcher.launch("image/*") }, // Starte die Galerie-Auswahl
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Bild auswählen")
            }

            Button(
                onClick = {
                    selectedImageUri?.let { uri ->
                        val fileName =
                            "profile_picture.png"
                        saveImageFromUriToInternalStorage(context, uri, fileName)

                        val sharedPreferences =
                            context.getSharedPreferences("BudgetBuddyPrefs", Context.MODE_PRIVATE)
                        with(sharedPreferences.edit()) {
                            putString("username", username)
                            putString("profilePicture", fileName)
                            apply()
                        }
                        onSetupComplete()
                    }
                }
            ) {
                Text("Budget Buddy Starten")
            }
        }
    }
}