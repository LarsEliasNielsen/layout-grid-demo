package dk.tv2.layout.grid.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LabelIcon(openState: MutableState<Boolean>, label: String) {
    IconButton(
        onClick = { openState.value = true }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 12.dp)
        ) {
            Icon(imageVector = Icons.Default.Info, contentDescription = label)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = label)
        }
    }
}

