package com.lgbtqspacey.king.features.composable

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

/**
 * @param label Input field description
 * @param modifier Compose UI Modifier
 * @param keyboardType Type of keyboard to be displayed by the Mobile OS.
 * Has no effect on desktop.
 *
 * @see Modifier
 */
@Composable
fun InputField(
    label: String,
    modifier: Modifier,
    keyboardType: KeyboardType = KeyboardType.Unspecified
): String {
    var value by remember { mutableStateOf("") }

    OutlinedTextField(
        value = value,
        onValueChange = { value = it },
        singleLine = true,
        label = { Text(label) },
        colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.primary),
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )

    return value
}
