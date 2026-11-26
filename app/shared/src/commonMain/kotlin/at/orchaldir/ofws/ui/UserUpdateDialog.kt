package at.orchaldir.ofws.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import at.orchaldir.ofws.model.user.User
import at.orchaldir.ofws.model.user.UserRole
import at.orchaldir.ofws.model.utils.name.Name
import at.orchaldir.ofws.utils.SelectValue

@Composable
fun UserUpdateDialog(
    user: User,
    onConfirm: (User) -> Unit,
) {
    var name by remember { mutableStateOf(user.name.text) }
    var selectedRole by remember { mutableStateOf(user.role) }
    val colors = TextFieldDefaults.colors(
        focusedTextColor = Color.Blue,
        focusedContainerColor = Color.White,
    )

    Dialog(onDismissRequest = {}) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            shape = RoundedCornerShape(CornerSize(4.dp))
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text("Update ${user.name.text}", fontSize = 20.sp)
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    colors = colors
                )
                SelectValue(UserRole.entries, user.role) { role ->
                    selectedRole = role
                }
                OutlinedButton(onClick = {
                    val newUser = User(
                        user.id,
                        Name.init(name),
                        selectedRole,
                    )
                    onConfirm(newUser)
                }) {
                    Text("Update")
                }
            }
        }
    }
}