package dk.tv2.layout.grid.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun KeyValueText(key: String = "Key", value: String = "Value", isEven: Boolean = true) {
    Row(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 4.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = key
        )
        Text(
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Black,
            text = value
        )
    }
}
