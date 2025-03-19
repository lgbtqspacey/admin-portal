package com.lgbtqspacey.king.features.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.lgbtqspacey.king.commonMain.composeResources.Res
import com.lgbtqspacey.king.commonMain.composeResources.ic_arrow_back
import com.lgbtqspacey.king.commonMain.composeResources.ic_home
import com.lgbtqspacey.king.helpers.Screens
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.vectorResource

/**
 * Displays two buttons: `Back` and `Home`.
 *
 * @param navigator PreCompose navigator
 * @param previousScreen Route to return
 *
 * @see Navigator
 * @see Screens
 */
@Composable
fun TopNavBar(navigator: Navigator, previousScreen: String) {
    Row {
        Button(
            onClick = { navigator.navigate(previousScreen) }
        ) {
            Image(vectorResource(Res.drawable.ic_arrow_back), "Voltar")
            Text("Voltar")
        }

        Button(
            onClick = { navigator.navigate(Screens.HOME) }
        ) {
            Image(vectorResource(Res.drawable.ic_home), "Início")
            Text("Início")
        }
    }
}
