package com.lgbtqspacey.king.features.people

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
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
import com.lgbtqspacey.king.features.composable.InfoCard
import com.lgbtqspacey.king.features.composable.TopNavBar
import com.lgbtqspacey.king.helpers.Dimensions
import com.lgbtqspacey.king.helpers.Screens
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.vectorResource

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
            .verticalScroll(rememberScrollState())
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

                InfoCard(
                    title = "ID",
                    value = userDetails.id ?: "",
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(id) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        })

                InfoCard(
                    title = "Nível de acesso",
                    value = userDetails.accessLevel ?: "",
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(accessLevel) {
                            start.linkTo(id.end, Dimensions.SIZE_16.dp())
                            top.linkTo(parent.top)
                        })

                InfoCard(
                    title = "Usuário",
                    value = userDetails.username ?: "",
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(username) {
                            start.linkTo(parent.start)
                            top.linkTo(id.bottom, Dimensions.SIZE_16.dp())
                        })

                InfoCard(
                    title = "Email",
                    value = userDetails.email ?: "",
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(email) {
                            start.linkTo(username.end, Dimensions.SIZE_16.dp())
                            top.linkTo(id.bottom, Dimensions.SIZE_16.dp())
                        })

                InfoCard(
                    title = "Nome",
                    value = userDetails.name ?: "",
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(name) {
                            start.linkTo(parent.start)
                            top.linkTo(username.bottom, Dimensions.SIZE_16.dp())
                        })

                InfoCard(
                    title = "Pronomes",
                    value = userDetails.pronouns ?: "",
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(pronouns) {
                            start.linkTo(name.end, Dimensions.SIZE_16.dp())
                            top.linkTo(username.bottom, Dimensions.SIZE_16.dp())
                        })

                InfoCard(
                    title = "Data de nascimento",
                    value = userDetails.dateOfBirth ?: "",
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(dateOfBirth) {
                            start.linkTo(parent.start)
                            top.linkTo(name.bottom, Dimensions.SIZE_16.dp())
                        })

                InfoCard(
                    title = "Idade",
                    value = "",
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(age) {
                            start.linkTo(dateOfBirth.end, Dimensions.SIZE_16.dp())
                            top.linkTo(name.bottom, Dimensions.SIZE_16.dp())
                        })

                InfoCard(
                    title = "Telefone",
                    value = userDetails.phone ?: "",
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(phone) {
                            start.linkTo(parent.start)
                            top.linkTo(dateOfBirth.bottom, Dimensions.SIZE_16.dp())
                        })


                InfoCard(
                    title = "Discord ID",
                    value = userDetails.discordId ?: "",
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(discordId) {
                            start.linkTo(phone.end, Dimensions.SIZE_16.dp())
                            top.linkTo(dateOfBirth.bottom, Dimensions.SIZE_16.dp())
                        })

                InfoCard(
                    title = "Equipes",
                    value = userDetails.teams.toString(),
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(teams) {
                            start.linkTo(parent.start)
                            top.linkTo(discordId.bottom, Dimensions.SIZE_16.dp())
                        })

                InfoCard(
                    title = "Entrada",
                    value = userDetails.joinedAt ?: "",
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(joinedAt) {
                            start.linkTo(parent.start)
                            top.linkTo(teams.bottom, Dimensions.SIZE_16.dp())
                        })


                InfoCard(
                    title = "Saída",
                    value = userDetails.leftAt ?: "",
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(leftAt) {
                            start.linkTo(joinedAt.end, Dimensions.SIZE_16.dp())
                            top.linkTo(teams.bottom, Dimensions.SIZE_16.dp())
                        })

                InfoCard(
                    title = "Usuário criado por",
                    value = userDetails.createdBy ?: "",
                    modifier = Modifier
                        .widthIn(min = Dimensions.SIZE_224.dp())
                        .constrainAs(createdBy) {
                            start.linkTo(leftAt.end, Dimensions.SIZE_16.dp())
                            top.linkTo(teams.bottom, Dimensions.SIZE_16.dp())
                        })
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
