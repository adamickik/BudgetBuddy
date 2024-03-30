import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.theme.BudgetBuddyTheme
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun InitialBootScreen(onSetupComplete: () -> Unit) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }
    BudgetBuddyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(id = R.string.initialScreen_username)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true,
                isError = username.isNotEmpty() && !isValidUsername(username)
            )
            if (selectedImageUri != null) {
                val imagePainter: Painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context).data(selectedImageUri).build()
                )
                Image(
                    painter = imagePainter,
                    contentDescription = stringResource(id = R.string.initialScreen_picDescription),
                    modifier = Modifier
                        .height(200.dp)
                        .padding(16.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.padding( 16.dp)
            ) {
                Text(stringResource(id = R.string.initialScreen_profilePic))
            }

            Button(
                onClick = {
                    if(username.isNotEmpty() && isValidUsername(username)) {
                        val sharedPreferences =
                            context.getSharedPreferences("BudgetBuddyPrefs", Context.MODE_PRIVATE)
                        selectedImageUri?.let { uri ->
                            val fileName = "profile_picture.png"
                            saveImageFromUriToInternalStorage(context, uri, fileName)

                            with(sharedPreferences.edit()) {
                                putString("profilePicture", fileName)
                                apply()
                            }

                        }
                        with(sharedPreferences.edit()) {
                            putString("username", username)
                            apply()
                        }
                        onSetupComplete()
                    } else {
                        Toast.makeText(context, "Bitte gültigen Namen angeben",
                            Toast.LENGTH_LONG).show()
                    }

                },
                modifier = Modifier.padding( 16.dp)
            ) {
                Text(stringResource(id = R.string.initialScreen_startApp))
            }
        }
    }
}

fun isValidUsername(text: String): Boolean {
    return text.matches(Regex("^[a-zA-Z]{2,16}\$"))
}

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