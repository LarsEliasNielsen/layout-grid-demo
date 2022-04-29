package dk.tv2.layout.grid.ui.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import dk.tv2.layout.grid.GridManager

@Composable
fun GridMarginStart(gridManager: GridManager, visible: Boolean) {
    Box(
        modifier = androidx.compose.ui.Modifier
            .width(gridManager.marginStart)
            .fillMaxHeight()
            .alpha(if (visible) 1f else 0f)
            .background(Color(0x331AFF00))
    )
}

@Composable
fun GridMarginEnd(gridManager: GridManager, visible: Boolean) {
    Box(
        modifier = androidx.compose.ui.Modifier
            .width(gridManager.marginEnd)
            .fillMaxHeight()
            .alpha(if (visible) 1f else 0f)
            .background(Color(0x331AFF00))
    )
}
