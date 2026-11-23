package at.orchaldir.ofws.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.orchaldir.ofws.model.user.User

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