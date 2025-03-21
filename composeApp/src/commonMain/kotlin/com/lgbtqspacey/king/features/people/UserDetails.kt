package com.lgbtqspacey.king.features.people

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import com.lgbtqspacey.king.backend.adapter.AdminAdapter
import com.lgbtqspacey.king.backend.model.FilterUser
import com.lgbtqspacey.king.backend.model.User
import com.lgbtqspacey.king.commonMain.composeResources.Res
import com.lgbtqspacey.king.commonMain.composeResources.ic_delete
import com.lgbtqspacey.king.commonMain.composeResources.ic_edit
import com.lgbtqspacey.king.features.composable.ProfileCard
import com.lgbtqspacey.king.features.composable.TopNavBar
import com.lgbtqspacey.king.helpers.Dimensions
import com.lgbtqspacey.king.helpers.Screens
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserDetails(navigator: Navigator, userId: String) {
    val coroutineScope = rememberCoroutineScope()

    var isLoaded by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var errorCode by remember { mutableStateOf("") }
    var userDetails = User()

    coroutineScope.launch {
        val search = AdminAdapter().getUsers(FilterUser(id = userId))

        if (search.isSuccess) {
            search.userDetails?.data?.forEach { user ->
                val details = User(
                    id = user.id ?: "",
                    accessLevel = user.accessLevel ?: "",
                    username = user.username ?: "",
                    email = user.email ?: "",
                    name = user.name ?: "",
                    pronouns = user.pronouns ?: "",
                    dateOfBirth = user.dateOfBirth ?: "",
                    phone = user.phone ?: "",
                    discordId = user.discordId ?: "",
                    teams = user.teams,
                    joinedAt = user.joinedAt ?: "",
                    leftAt = user.leftAt ?: "",
                    createdBy = user.createdBy ?: "",
                )

                userDetails = details
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
            btnEdit,
            btnDelete,
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
                TopNavBar(navigator, Screens.USERS, false)
            }

            /** Buttons **/
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
                modifier = Modifier.constrainAs(btnEdit) {
                    top.linkTo(parent.top, Dimensions.SIZE_16.dp())
                    start.linkTo(navigation.end, Dimensions.SIZE_16.dp())
                }
            ) {
                Image(vectorResource(Res.drawable.ic_edit), "Editar")
                Text("Editar")
            }

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                modifier = Modifier.constrainAs(btnDelete) {
                    top.linkTo(parent.top, Dimensions.SIZE_16.dp())
                    start.linkTo(btnEdit.end, Dimensions.SIZE_16.dp())
                }
            ) {
                Image(vectorResource(Res.drawable.ic_delete), "Excluir")
                Text("Excluir")
            }

            /** Cards **/
            ConstraintLayout(
                modifier = Modifier
                    .constrainAs(mainContainer) {
                        start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                        top.linkTo(navigation.bottom, Dimensions.SIZE_16.dp())
                    }
            ) {
                val (
                    id,
                    accessLevel,
                    username,
                    email,
                    name,
                    pronouns,
                    dateOfBirth,
                    age,
                    phone,
                    discordId,
                    teams,
                    joinedAt,
                    leftAt,
                    createdBy,
                ) = createRefs()

                // id
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(id) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                ) {
                    ProfileCard("ID", userDetails.id ?: "")
                }

                // access level
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(accessLevel) {
                            start.linkTo(id.end, Dimensions.SIZE_16.dp())
                            top.linkTo(parent.top)
                        }
                ) {
                    ProfileCard("Nível de acesso", userDetails.accessLevel ?: "")
                }

                // username
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(username) {
                            start.linkTo(parent.start)
                            top.linkTo(id.bottom, Dimensions.SIZE_16.dp())
                        }
                ) {
                    ProfileCard("Usuário", userDetails.username ?: "")
                }

                // email
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(email) {
                            start.linkTo(username.end, Dimensions.SIZE_16.dp())
                            top.linkTo(id.bottom, Dimensions.SIZE_16.dp())
                        }
                ) {
                    ProfileCard("Email", userDetails.email ?: "")
                }

                // name
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(name) {
                            start.linkTo(parent.start)
                            top.linkTo(username.bottom, Dimensions.SIZE_16.dp())
                        }
                ) {
                    ProfileCard("Nome", userDetails.name ?: "")
                }

                // pronouns
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(pronouns) {
                            start.linkTo(name.end, Dimensions.SIZE_16.dp())
                            top.linkTo(username.bottom, Dimensions.SIZE_16.dp())
                        }
                ) {
                    ProfileCard("Pronomes", userDetails.pronouns ?: "")
                }

                // date of birth
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(dateOfBirth) {
                            start.linkTo(parent.start)
                            top.linkTo(name.bottom, Dimensions.SIZE_16.dp())
                        }
                ) {
                    ProfileCard("Data de nascimento", userDetails.dateOfBirth ?: "")
                }

                // age
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(age) {
                            start.linkTo(dateOfBirth.end, Dimensions.SIZE_16.dp())
                            top.linkTo(name.bottom, Dimensions.SIZE_16.dp())
                        }
                ) {
                    ProfileCard("Idade", "")
                }

                // phone
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(phone) {
                            start.linkTo(parent.start)
                            top.linkTo(dateOfBirth.bottom, Dimensions.SIZE_16.dp())
                        }
                ) {
                    ProfileCard("Telefone", userDetails.phone ?: "")
                }

                // discord id
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(discordId) {
                            start.linkTo(phone.end, Dimensions.SIZE_16.dp())
                            top.linkTo(dateOfBirth.bottom, Dimensions.SIZE_16.dp())
                        }
                ) {
                    ProfileCard("Discord ID", userDetails.discordId ?: "")
                }

                // teams
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(teams) {
                            start.linkTo(parent.start)
                            top.linkTo(discordId.bottom, Dimensions.SIZE_16.dp())
                        }
                ) {
                    ProfileCard("Times", userDetails.teams.toString())
                }

                // joined at
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(joinedAt) {
                            start.linkTo(parent.start)
                            top.linkTo(teams.bottom, Dimensions.SIZE_16.dp())
                        }
                ) {
                    ProfileCard("Entrada", userDetails.joinedAt ?: "")
                }

                // left at
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(leftAt) {
                            start.linkTo(joinedAt.end, Dimensions.SIZE_16.dp())
                            top.linkTo(teams.bottom, Dimensions.SIZE_16.dp())
                        }
                ) {
                    ProfileCard("Saída", userDetails.leftAt ?: "")
                }

                // created by
                Card(
                    elevation = CardDefaults.cardElevation(Dimensions.SIZE_4.dp()),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(createdBy) {
                            start.linkTo(leftAt.end, Dimensions.SIZE_16.dp())
                            top.linkTo(teams.bottom, Dimensions.SIZE_16.dp())
                        }
                ) {
                    ProfileCard("Usuário criado por", userDetails.createdBy ?: "")
                }
            }
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
