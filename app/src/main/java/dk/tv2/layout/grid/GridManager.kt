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
import dk.tv2.layout.grid.ui.WindowSizeClass

class GridManager(private val activity: Activity) {

    /**
     * Number of columns to separate screen into.
     */
    val columns: Int = 12

    /**
     * Left/start margin of screen content.
     */
    val marginStart: Dp = 12.dp

    /**
     * Right(end margin of screen content.
     */
    val marginEnd: Dp = 24.dp

    /**
     * Space between columns.
     */
    val gutter: Dp = 8.dp

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
    fun getWindowWidth(): WindowSizeClass {
        val windowDpSize = getWindowDpSize()
        return when {
            windowDpSize.width > 840.dp -> WindowSizeClass.EXPANDED
            windowDpSize.width > 600.dp -> WindowSizeClass.MEDIUM
            else -> WindowSizeClass.COMPACT
        }
    }

    @Composable
    fun getColumnSpanWidth(columnSpan: Int): Dp =
        (getWindowDpSize().width - getMarginSize() - getGutterTotal())
            .div(columns)
            .times(columnSpan)
            .plus(getGutterTotal(columnSpan))

    @Composable
    fun getColumnWidth(): Dp = (getWindowDpSize().width - getMarginSize() - getGutterTotal())
        .div(columns)

    private fun getMarginSize(): Dp = marginStart + marginEnd

    private fun getGutterTotal(columnSpan: Int = columns): Dp = gutter.times(columnSpan - 1)
}
