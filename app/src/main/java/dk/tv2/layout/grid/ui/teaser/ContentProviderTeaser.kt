package dk.tv2.layout.grid.ui.teaser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Small teaser for showing content provider.
 */
@Preview
@Composable
fun ContentProviderTeaser(teaserWidthDp: Dp = 100.dp, title: String = "CPT") {
    Box(
        Modifier
            .width(teaserWidthDp)
            .aspectRatio(2.0f, false)
            .background(Color(0xffe0bbe4))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = MaterialTheme.typography.body1,
                text = title
            )
        }
    }
}
