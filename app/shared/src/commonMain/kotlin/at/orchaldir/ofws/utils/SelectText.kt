package at.orchaldir.ofws.utils

import androidx.compose.material3.*
import androidx.compose.runtime.*
import at.orchaldir.ofws.model.utils.name.Name
import at.orchaldir.ofws.model.utils.name.NotEmptyString

@Composable
fun SelectNotEmptyString(
    label: String,
    text: NotEmptyString,
    onChange: (NotEmptyString) -> Unit,
) = SelectText(label, text.text) {
    onChange(NotEmptyString.init(it))
}

@Composable
fun SelectName(
    name: Name,
    onChange: (Name) -> Unit,
) = SelectText("Name", name.text) {
    onChange(Name.init(it))
}

@Composable
fun SelectText(
    label: String,
    text: String,
    onChange: (String) -> Unit,
) {
    var current by remember { mutableStateOf(text) }

    TextField(
        value = current,
        onValueChange = {
            current = it
            onChange(it)
        },
        label = { Text("label") },
        singleLine = true,
    )
}