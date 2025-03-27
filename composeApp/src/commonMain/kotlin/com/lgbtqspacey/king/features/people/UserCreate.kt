package com.lgbtqspacey.king.features.people

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.KeyboardType
import androidx.constraintlayout.compose.ConstraintLayout
import com.lgbtqspacey.king.backend.model.User
import com.lgbtqspacey.king.commonMain.composeResources.*
import com.lgbtqspacey.king.database.dao.UserDao
import com.lgbtqspacey.king.features.composable.InputField
import com.lgbtqspacey.king.helpers.Dimensions
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.vectorResource

/**
 * 1. Fill data
 * 2. Send request
 * 3. Retrieve ID
 * 4. Navigate to UserDetails
 */
@Composable
fun UserCreate(navigator: Navigator, user: User? = null) {
    val coroutineScope = rememberCoroutineScope()
    var currentUser by remember { mutableStateOf("") }

    var levelExpanded by remember { mutableStateOf(false) }
    var levelLabel by remember { mutableStateOf("Selecione o nível de acesso") }

    var accessLevel by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var pronouns by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var discordId by remember { mutableStateOf("") }
    var teams by remember { mutableStateOf("") }
    var joinedAt by remember { mutableStateOf("") }
    var leftAt by remember { mutableStateOf("") }

    /**
     * Send api request to create or edit user
     */
    val send: () -> Unit = {
        coroutineScope.launch {
            // todo
        }
    }

    /**
     * Retrieve current user's username from local database
     */
    val getCurrentUser: () -> Unit = {
        coroutineScope.launch {
            currentUser = UserDao().getUser().name
        }
    }

    /** UI **/
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
    ) {
        val (
            inputAccessLevel,
            inputUsername,
            inputPassword,
            inputEmail,
            inputName,
            inputPronouns,
            inputDateOfBirth,
            inputPhone,
            inputDiscordId,
            inputTeams,
            inputJoinedAt,
            inputLeftAt
        ) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(inputAccessLevel) {
                    start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                    top.linkTo(parent.top, Dimensions.SIZE_16.dp())
                }
        ) {
            OutlinedButton(
                onClick = { levelExpanded = !levelExpanded }
            ) {
                if (accessLevel.isNotEmpty()) {
                    Image(
                        vectorResource(
                            when (accessLevel) {
                                "admin" -> Res.drawable.ic_admin
                                "editor" -> Res.drawable.ic_editor
                                "user" -> Res.drawable.ic_person
                                else -> Res.drawable.ic_close
                            }
                        ),
                        null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                    )
                }

                Text(levelLabel)
            }

            DropdownMenu(
                expanded = levelExpanded,
                onDismissRequest = { levelExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Admin") },
                    onClick = {
                        levelLabel = "Admin"
                        accessLevel = "admin"
                        levelExpanded = false
                    },
                    leadingIcon = {
                        Image(
                            imageVector = vectorResource(Res.drawable.ic_admin),
                            contentDescription = "admin",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                )

                DropdownMenuItem(
                    text = { Text("Editore") },
                    onClick = {
                        levelLabel = "Editore"
                        accessLevel = "editor"
                        levelExpanded = false
                    },
                    leadingIcon = {
                        Image(
                            imageVector = vectorResource(Res.drawable.ic_editor),
                            contentDescription = "editore",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                )

                DropdownMenuItem(
                    text = { Text("Usuárie") },
                    onClick = {
                        levelLabel = "Usuárie"
                        accessLevel = "user"
                        levelExpanded = false
                    },
                    leadingIcon = {
                        Image(
                            imageVector = vectorResource(Res.drawable.ic_person),
                            contentDescription = "usuárie",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                )

                DropdownMenuItem(
                    text = { Text("Nenhum") },
                    onClick = {
                        levelLabel = "Nenhum"
                        accessLevel = "none"
                        levelExpanded = false
                    },
                    leadingIcon = {
                        Image(
                            imageVector = vectorResource(Res.drawable.ic_close),
                            contentDescription = "nenhum",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                )
            }
        }

        username = InputField(
            label = "Nome de usuário",
            modifier = Modifier.constrainAs(inputUsername) {
                start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                top.linkTo(inputAccessLevel.bottom)
            }
        )

        password = InputField(
            label = "Senha",
            modifier = Modifier.constrainAs(inputPassword) {
                start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                top.linkTo(inputUsername.bottom)
            }
        )

        email = InputField(
            label = "Email",
            modifier = Modifier.constrainAs(inputEmail) {
                start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                top.linkTo(inputPassword.bottom)
            },
            keyboardType = KeyboardType.Email
        )

        name = InputField(
            label = "Nome",
            modifier = Modifier.constrainAs(inputName) {
                start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                top.linkTo(inputEmail.bottom)
            }
        )

        pronouns = InputField(
            label = "Pronomes",
            modifier = Modifier.constrainAs(inputPronouns) {
                start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                top.linkTo(inputName.bottom)
            }
        )

        dateOfBirth = InputField(
            isDate = true,
            label = "Data de Nascimento",
            modifier = Modifier
                .constrainAs(inputDateOfBirth) {
                    start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                    top.linkTo(inputPronouns.bottom)
                }
        )


        phone = InputField(
            label = "Telefone",
            modifier = Modifier.constrainAs(inputPhone) {
                start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                top.linkTo(inputDateOfBirth.bottom)
            },
            keyboardType = KeyboardType.Phone
        )

        discordId = InputField(
            label = "Discord ID",
            modifier = Modifier.constrainAs(inputDiscordId) {
                start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                top.linkTo(inputPhone.bottom)
            }
        )

        teams = InputField(
            label = "Equipes",
            modifier = Modifier.constrainAs(inputTeams) {
                start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                top.linkTo(inputDiscordId.bottom)
            }
        )

        joinedAt = InputField(
            isDate = true,
            label = "Data de entrada",
            modifier = Modifier.constrainAs(inputJoinedAt) {
                start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                top.linkTo(inputTeams.bottom)
            }
        )

        leftAt = InputField(
            isDate = true,
            label = "Data de saída",
            modifier = Modifier.constrainAs(inputLeftAt) {
                start.linkTo(parent.start, Dimensions.SIZE_16.dp())
                top.linkTo(inputJoinedAt.bottom)
            }
        )
    }
}
