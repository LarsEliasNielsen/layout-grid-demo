package dk.tv2.layout.grid.ui.teaser

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dk.tv2.layout.grid.GridManager

/**
 * Content row (panel) for showing teasers in horizontal scroll view.
 */
@Composable
fun TeaserRow(gridManager: GridManager, rowTitle: String? = null, content: @Composable (Int) -> Unit) {
    Column {
        if (rowTitle != null) {
            Box(
                modifier = Modifier.padding(
                    start = gridManager.marginStart(),
                    bottom = 12.dp
                )
            ) {
                Text(
                    style = MaterialTheme.typography.h2,
                    text = rowTitle
                )
            }
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(
                gridManager.gutter()
            ),
            contentPadding = PaddingValues(
                horizontal = gridManager.marginStart()
            )
        ) {
            items(15) { index ->
                content(index)
            }
        }
    }
}
