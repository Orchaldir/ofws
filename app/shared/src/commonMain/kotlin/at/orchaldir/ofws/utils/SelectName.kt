package at.orchaldir.ofws.utils

import androidx.compose.material3.*
import androidx.compose.runtime.*
import at.orchaldir.ofws.model.utils.name.Name

@Composable
fun SelectName(
    name: Name,
    onChange: (Name) -> Unit,
) {
    var current by remember { mutableStateOf(name.text) }

    TextField(
        value = current,
        onValueChange = {
            current = it
            onChange(Name.init(it))
        },
        label = { Text("Name") },
        singleLine = true,
    )
}