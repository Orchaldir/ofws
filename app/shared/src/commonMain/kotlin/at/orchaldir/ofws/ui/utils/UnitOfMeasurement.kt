package at.orchaldir.ofws.ui.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import at.orchaldir.ofws.model.utils.PrefixedUnit
import at.orchaldir.ofws.model.utils.SuffixedUnit
import at.orchaldir.ofws.model.utils.UnitOfMeasurement
import at.orchaldir.ofws.model.utils.Unitless
import at.orchaldir.ofws.ui.doNothing

@Composable
fun DisplayUnitOfMeasurement(
    unit: UnitOfMeasurement?,
    showUnitless: Boolean = true,
) {
    when (unit) {
        is PrefixedUnit -> Text("Prefix '${unit.prefix.text}'")
        is SuffixedUnit -> Text("Suffix '${unit.suffix.text}'")
        Unitless -> if (showUnitless) {
            Text("Unitless")
        }

        null -> doNothing()
    }
}
