package at.orchaldir.ofws

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.orchaldir.ofws.model.app.User
import at.orchaldir.ofws.network.UserApi
import at.orchaldir.ofws.network.createHttpClient
import kotlinx.coroutines.launch

@Composable
fun App() {
    MaterialTheme {
        val httpClient = createHttpClient()
        val userApi = remember { UserApi(httpClient) }
        var users by remember { mutableStateOf(emptyList<User>()) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            users = userApi.getAllUsers()
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
                    }
                )
            }
        }
    }
}

@Composable
fun UserCard(
    user: User,
    onDelete: (User) -> Unit,
    onUpdate: (User) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        shape = RoundedCornerShape(CornerSize(4.dp))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                "${user.name.text}: ${user.role}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Row {
                OutlinedButton(onClick = { onDelete(user) }) {
                    Text("Delete")
                }
                Spacer(Modifier.width(8.dp))
                OutlinedButton(onClick = { onUpdate(user) }) {
                    Text("Update")
                }
            }
        }
    }
}