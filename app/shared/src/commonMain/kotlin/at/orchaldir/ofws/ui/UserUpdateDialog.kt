package at.orchaldir.ofws.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import at.orchaldir.ofws.model.User
import at.orchaldir.ofws.model.UserRole
import at.orchaldir.ofws.model.utils.Name

@Composable
fun UserUpdateDialog(
    user: User,
    onConfirm: (User) -> Unit
) {
    var name by remember { mutableStateOf(user.name.text) }
    var selectedRoleIndex by remember { mutableStateOf(user.role.ordinal) }
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
                SingleChoiceSegmentedButtonRow {
                    UserRole.entries.forEachIndexed { index, r ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = UserRole.entries.size
                            ),
                            onClick = { selectedRoleIndex = index },
                            selected = index == selectedRoleIndex,
                            label = { Text(r.name) }
                        )
                    }
                }
                OutlinedButton(onClick = {
                    val newUser = User(
                        user.id,
                        Name.init(name),
                        UserRole.entries[selectedRoleIndex]
                    )
                    onConfirm(newUser)
                }) {
                    Text("Update")
                }
            }
        }
    }
}