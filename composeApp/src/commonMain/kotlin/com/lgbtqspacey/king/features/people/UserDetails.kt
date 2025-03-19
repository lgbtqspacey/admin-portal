package com.lgbtqspacey.king.features.people

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import com.lgbtqspacey.king.backend.adapter.AdminAdapter
import com.lgbtqspacey.king.backend.model.FilterUser
import com.lgbtqspacey.king.backend.model.User
import com.lgbtqspacey.king.features.composable.TopNavBar
import com.lgbtqspacey.king.helpers.Dimensions
import com.lgbtqspacey.king.helpers.Screens
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun UserDetails(navigator: Navigator, userId: String) {
    val coroutineScope = rememberCoroutineScope()

    var isLoaded by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var errorCode by remember { mutableStateOf("") }
    val userDetails = mutableListOf<User>()

    coroutineScope.launch {
        val search = AdminAdapter().getUsers(FilterUser(id = userId))

        if (search.isSuccess) {
            search.userDetails?.data?.forEach { user ->
                val details = User(
                    id = user.id ?: "",
                    pronouns = user.pronouns ?: "",
                    accessLevel = user.accessLevel ?: "",
                    email = user.email ?: "",
                    phone = user.phone ?: "",
                    discordId = user.discordId ?: "",
                    username = user.username ?: "",
                    leftAt = user.leftAt ?: "",
                    joinedAt = user.joinedAt ?: "",
                    createdBy = user.createdBy ?: "",
                    dateOfBirth = user.dateOfBirth ?: "",
                    name = user.name ?: "",
                    teams = user.teams
                )

                userDetails.add(details)
            }
        } else {
            isError = true
            errorCode = search.errorCode.toString()
            errorMessage = search.errorMessage
        }

        isLoaded = true
    }

    /** UI **/
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        val (
            loading,
            navigation,
            mainContainer,
            errorContainer
        ) = createRefs()

        /** Animated view to show loading circle while the application fetch the data **/
        AnimatedVisibility(
            visible = !isLoaded,
            modifier = Modifier
                .constrainAs(loading) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }

        if (isLoaded) {
            Box(
                modifier = Modifier.constrainAs(navigation) {
                    start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                    top.linkTo(parent.top, Dimensions.SIZE_16.dp())
                }
            ) {
                TopNavBar(navigator, Screens.USERS)
            }

            ConstraintLayout(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.errorContainer,
                        RoundedCornerShape(Dimensions.SIZE_1.dp())
                    )
                    .constrainAs(mainContainer) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                val (
                    cardName,
                    cardId,
                    cardPronouns,
                    cardAccessLevel,
                ) = createRefs()
            }

            /** Error container **/
            if (isError) {
                Box(
                    modifier = Modifier
                        .constrainAs(errorContainer) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .border(
                            color = MaterialTheme.colorScheme.error,
                            shape = RoundedCornerShape(Dimensions.SIZE_4.dp()),
                            width = Dimensions.SIZE_1.dp()
                        )
                ) {
                    Text(
                        text = "$errorCode - $errorMessage",
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(Dimensions.SIZE_16.dp())
                    )
                }
            }
        }
    }
}
