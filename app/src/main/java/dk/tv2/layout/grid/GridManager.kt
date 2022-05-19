package dk.tv2.layout.grid

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.layout.WindowMetricsCalculator
import dk.tv2.layout.grid.ui.Viewport
import kotlin.math.floor
import kotlin.math.roundToInt

class GridManager(private val activity: Activity) {

    /**
     * Number of columns to separate screen into.
     */
    @Composable
    fun columns(): Int = when (getViewport()) {
        Viewport.XLARGE -> 8
        Viewport.LARGE -> 8
        Viewport.MEDIUM -> 12
        Viewport.SMALL -> 12
    }

    /**
     * Left/start margin of screen content.
     */
    @Composable
    fun marginStart(): Dp = when (getViewport()) {
        Viewport.XLARGE -> 40.dp
        Viewport.LARGE -> 32.dp
        Viewport.MEDIUM -> 16.dp
        Viewport.SMALL -> 12.dp
    }

    /**
     * Right(end margin of screen content.
     */
    @Composable
    fun marginEnd(): Dp = when (getViewport()) {
        Viewport.XLARGE -> 40.dp
        Viewport.LARGE -> 32.dp
        Viewport.MEDIUM -> 16.dp
        Viewport.SMALL -> 16.dp
    }

    /**
     * Space between columns.
     */
    @Composable
    fun gutter(): Dp = when (getViewport()) {
        Viewport.XLARGE -> 16.dp
        Viewport.LARGE -> 16.dp
        Viewport.MEDIUM -> 16.dp
        Viewport.SMALL -> 8.dp
    }

    @Composable
    fun getWindowDpSize(): DpSize {
        val configuration = LocalConfiguration.current
        val windowMetrics = remember(configuration) {
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
        }
        return with(LocalDensity.current) {
            windowMetrics.bounds.toComposeRect().size.toDpSize()
        }
    }

    @Composable
    fun getWindowWidthDp(): Dp = floor(getWindowDpSize().width.value).dp

    @Composable
    fun getWindowHeightDp(): Dp = floor(getWindowDpSize().height.value).dp

    @Composable
    fun getViewport(): Viewport {
        val windowDpSize = getWindowDpSize()
        return when {
            windowDpSize.width > 690.dp -> Viewport.XLARGE
            windowDpSize.width > 840.dp -> Viewport.LARGE
            windowDpSize.width > 600.dp -> Viewport.MEDIUM
            else -> Viewport.SMALL
        }
    }

    @Composable
    fun getColumnSpanWidth(columnSpan: Int): Dp = getColumnWidth()
            .times(columnSpan)
            .plus(getGutterTotal(columnSpan))

    @Composable
    fun getColumnWidth(): Dp = (getWindowWidthDp() - getMarginSize() - getGutterTotal())
        .div(columns())
        .value.roundToInt().dp

    @Composable
    private fun getMarginSize(): Dp = marginStart() + marginEnd()

    @Composable
    private fun getGutterTotal(columnSpan: Int = columns()): Dp = gutter().times(columnSpan - 1)
}
