package com.lgbtqspacey.king.features.composable

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.lgbtqspacey.king.commonMain.composeResources.Res
import com.lgbtqspacey.king.commonMain.composeResources.ic_arrow_back
import com.lgbtqspacey.king.commonMain.composeResources.ic_home
import com.lgbtqspacey.king.helpers.Dimensions
import com.lgbtqspacey.king.helpers.Screens
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.vectorResource

/**
 * Displays two buttons: `Back` and `Home`.
 *
 * @param navigator PreCompose navigator
 * @param previousScreen Route to return
 * @param showHomeButton Display button to navigate to homepage, defaults to `true`.
 *
 * @see Navigator
 * @see Screens
 */
@Composable
fun TopNavBar(
    navigator: Navigator,
    previousScreen: String,
    showHomeButton: Boolean = true
) {
    ConstraintLayout {
        val (back, home) = createRefs()

        Button(
            onClick = { navigator.navigate(previousScreen) },
            modifier = Modifier.constrainAs(back) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        ) {
            Image(vectorResource(Res.drawable.ic_arrow_back), "Voltar")
            Text("Voltar")
        }

        if (showHomeButton) {
            Button(
                onClick = { navigator.navigate(Screens.HOME) },
                modifier = Modifier.constrainAs(home) {
                    start.linkTo(back.end, Dimensions.SIZE_16.dp())
                    top.linkTo(parent.top)
                }
            ) {
                Image(vectorResource(Res.drawable.ic_home), "Início")
                Text("Início")
            }
        }
    }
}
