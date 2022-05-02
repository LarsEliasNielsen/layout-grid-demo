package dk.tv2.layout.grid.ui.teaser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dk.tv2.layout.grid.GridManager

/**
 * Large teaser showing featured content.
 */
@Preview
@Composable
fun HeroTeaser(teaserWidthDp: Dp = 100.dp, heroTitle: String = "HT") {
    Box(
        Modifier
            .width(teaserWidthDp)
            .aspectRatio(2.0f, false)
            .background(Color(0xffffdfd3))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = MaterialTheme.typography.body1,
                text = heroTitle
            )
        }
    }
}

/**
 * Pager showing hero teasers.
 * Center hero teaser is selected as default.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeroTeaserPager(gridManager: GridManager) {
    val pagerState = rememberPagerState(initialPage = 5)

    HorizontalPager(
        count = 10,
        state = pagerState,
        itemSpacing = gridManager.gutter,
        contentPadding = PaddingValues(start = gridManager.marginStart, top = 0.dp, end = gridManager.marginEnd, bottom = 0.dp)
    ) { index ->
        HeroTeaser(gridManager.getWindowDpSize().width, "Hero $index")
    }
}
