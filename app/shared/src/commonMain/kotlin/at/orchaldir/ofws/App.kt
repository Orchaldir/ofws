package at.orchaldir.ofws

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import at.orchaldir.ofws.model.User
import at.orchaldir.ofws.network.UserApi
import at.orchaldir.ofws.network.createHttpClient
import at.orchaldir.ofws.ui.UserCard
import at.orchaldir.ofws.ui.UserUpdateDialog
import kotlinx.coroutines.launch

@Composable
fun App() {
    MaterialTheme {
        val httpClient = createHttpClient()
        val userApi = remember { UserApi(httpClient) }
        var users by remember { mutableStateOf(emptyList<User>()) }
        val scope = rememberCoroutineScope()
        var currentUser by remember { mutableStateOf<User?>(null) }

        LaunchedEffect(Unit) {
            users = userApi.getAllUsers()
        }

        if (currentUser != null) {
            UserUpdateDialog(
                currentUser!!,
                onConfirm = {
                    scope.launch {
                        userApi.updateUser(it)
                        users = userApi.getAllUsers()
                    }
                    currentUser = null
                }
            )
        }

        FloatingActionButton(
            onClick = {
                scope.launch {
                    val id = userApi.createUser()
                    users = userApi.getAllUsers()
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
                            userApi.deleteUser(it)
                            users = userApi.getAllUsers()
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
