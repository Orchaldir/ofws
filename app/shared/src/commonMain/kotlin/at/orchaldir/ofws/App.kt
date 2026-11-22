package at.orchaldir.ofws

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import at.orchaldir.ofws.model.app.User
import at.orchaldir.ofws.network.UserApi
import at.orchaldir.ofws.network.createHttpClient

@Composable
fun App() {
    MaterialTheme {
        val httpClient = createHttpClient()
        val userApi = remember { UserApi(httpClient) }
        val users = remember { mutableStateOf(emptyList<User>()) }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = {
                scope.launch {
                    users.value = userApi.getAllUsers()
                }
            }) {
                Text("Fetch Tasks")
            }
            for (task in users.value) {
                Text(task.name.text)
            }
        }
    }
}