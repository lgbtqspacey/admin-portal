package com.lgbtqspacey.king.features.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.lgbtqspacey.king.copyToClipboard
import com.lgbtqspacey.king.helpers.Dimensions

@Composable
fun ProfileCard(title: String, value: String) {
        Text(
            text = title,
            fontSize = Dimensions.SIZE_12.sp(),
            modifier = Modifier
                .padding(
                    start = Dimensions.SIZE_8.dp(),
                    top = Dimensions.SIZE_8.dp(),
                    end = Dimensions.SIZE_8.dp()
                )
        )

        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable(onClick = { copyToClipboard(value) })
                .padding(
                    start = Dimensions.SIZE_8.dp(),
                    bottom = Dimensions.SIZE_8.dp(),
                    end = Dimensions.SIZE_8.dp()
                )
        )
}
