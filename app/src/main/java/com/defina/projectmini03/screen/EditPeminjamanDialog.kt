package com.defina.projectmini03.screen

import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.defina.projectmini03.R
import com.defina.projectmini03.ui.theme.Projectmini03Theme

@Composable
fun EditPeminjamanDialog(
    initialNama: String = "",
    initialBitmap: Bitmap?,
    onDismissRequest: () -> Unit,
    onConfirmation: (String, Bitmap) -> Unit
) {
    var nama by remember { mutableStateOf("") }
    var currentBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showImagePicker by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(initialNama, initialBitmap) {
        nama = initialNama
        currentBitmap = initialBitmap
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                currentBitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let { currentBitmap = it }
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                currentBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clickable { showImagePicker = true }
                    )
                }

                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text(text = stringResource(id = R.string.nama)) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(R.string.batal))
                    }
                    OutlinedButton(
                        onClick = {
                            currentBitmap?.let { bitmap ->
                                onConfirmation(nama, bitmap)
                            }
                        },
                        enabled = nama.isNotEmpty() && currentBitmap != null,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(R.string.simpan))
                    }
                }
            }
        }
    }

    // Simple image picker dialog
    if (showImagePicker) {
        AlertDialog(
            onDismissRequest = { showImagePicker = false },
            title = { Text(text = stringResource(R.string.pilihgambar)) },
            text = { Text(text = stringResource(R.string.sumbergambar)) },
            confirmButton = {
                TextButton(onClick = {
                    showImagePicker = false
                    galleryLauncher.launch("image/*")
                }) {
                    Text(text = stringResource(R.string.foto))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showImagePicker = false
                    cameraLauncher.launch(null)
                }) {
                    Text(text = stringResource(R.string.camera))
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun EditBangunRuangDialogPreview() {
    Projectmini03Theme {
        EditPeminjamanDialog(
            initialNama = "Tas",
            initialBitmap = null,
            onDismissRequest = {},
            onConfirmation = { _, _ -> }
        )
    }
}