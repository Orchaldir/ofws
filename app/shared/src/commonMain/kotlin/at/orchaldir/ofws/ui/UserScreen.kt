package at.orchaldir.ofws.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import at.orchaldir.ofws.model.user.User
import at.orchaldir.ofws.network.UserClient
import io.ktor.client.*
import kotlinx.coroutines.launch

@Composable
fun UserScreen(
    client: HttpClient,
) {
    val userClient = remember { UserClient(client) }
    var users by remember { mutableStateOf(emptyList<User>()) }
    val scope = rememberCoroutineScope()
    var currentUser by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        users = userClient.getAllUsers()
    }

    if (currentUser != null) {
        UserUpdateDialog(
            currentUser!!,
            onConfirm = {
                scope.launch {
                    userClient.updateUser(it)
                    users = userClient.getAllUsers()
                }
                currentUser = null
            }
        )
    }

    Column {
        FloatingActionButton(
            onClick = {
                scope.launch {
                    val id = userClient.createUser()
                    users = userClient.getAllUsers()
                    currentUser = users.firstOrNull { it.id == id }
                }
            },
        ) {
            Icon(painter = rememberVectorPainter(Icons.Rounded.Add), "Add User")
        }

        LazyColumn(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize()
        ) {
            items(users) { user ->
                UserCard(
                    user,
                    onDelete = {
                        scope.launch {
                            userClient.deleteUser(it)
                            users = userClient.getAllUsers()
                        }
                    },
                    onUpdate = {
                        currentUser = user
                    }
                )
            }
        }
    }
}