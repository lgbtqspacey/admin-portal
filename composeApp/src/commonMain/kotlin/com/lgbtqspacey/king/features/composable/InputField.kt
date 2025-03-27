package com.lgbtqspacey.king.features.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.KeyboardType
import com.lgbtqspacey.king.commonMain.composeResources.Res
import com.lgbtqspacey.king.commonMain.composeResources.ic_calendar
import com.lgbtqspacey.king.helpers.DateHelper
import org.jetbrains.compose.resources.vectorResource

/**
 * @param label Input field description
 * @param modifier Compose UI Modifier
 * @param keyboardType Type of keyboard to be displayed by the Mobile OS.
 * @param placeholder describes the requested input
 * Has no effect on desktop.
 * @param isDate if the field is a date input.
 * @param datePlaceholder describes the date format that is been used.
 *
 * @see Modifier
 */
@Composable
fun InputField(
    label: String,
    modifier: Modifier,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    placeholder: String = "",
    isDate: Boolean = false,
    datePlaceholder: String = "DD/MM/AAAA",
): String {
    var value by remember { mutableStateOf("") }
    var datePickerExpanded by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { value = it },
        singleLine = true,
        label = { Text(label) },
        colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.primary),
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        placeholder = { if (isDate) Text(datePlaceholder) else Text(placeholder) },
        trailingIcon = {
            if (isDate) {
                Image(
                    imageVector = vectorResource(Res.drawable.ic_calendar),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier.clickable(onClick = { datePickerExpanded = true })
                )
            }
        },
    )

    DatePickerModal(
        onDateSelected = {
            value = DateHelper.convertMillisToDate(it ?: 0L)
            datePickerExpanded = false
        },
        onDismiss = {
            datePickerExpanded = false
        },
        visible = datePickerExpanded
    )

    return value
}
