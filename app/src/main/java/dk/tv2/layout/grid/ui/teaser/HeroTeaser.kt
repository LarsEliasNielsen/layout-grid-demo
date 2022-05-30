package dk.tv2.layout.grid.ui.teaser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dk.tv2.layout.grid.GridManager
import dk.tv2.layout.grid.theme.*
import dk.tv2.layout.grid.ui.Viewport

/**
 * Large teaser showing featured content.
 */
@Preview
@Composable
fun HeroTeaser(
    viewport: Viewport = Viewport.SMALL,
    teaserWidthDp: Dp = 100.dp,
    label: String = "Hero label",
    title: String = "Hero title",
    subtitle: String = "Hero subtitle"
) {
    val horizontalTextMargin: Dp = when (viewport) {
        Viewport.XLARGE -> S4
        Viewport.LARGE -> S4
        Viewport.MEDIUM -> S3
        Viewport.SMALL -> S3
    }
    val verticalTextMargin: Dp = when (viewport) {
        Viewport.XLARGE -> S2
        Viewport.LARGE -> S2
        Viewport.MEDIUM -> S1
        Viewport.SMALL -> S1
    }

    Box(
        Modifier
            .width(teaserWidthDp)
            .aspectRatio(2.0f, false)
            .background(Color(0xffffdfd3))
    ) {
        Text(
            text = "Logo",
            modifier = Modifier
                .padding(
                    start = horizontalTextMargin,
                    top = S4
                )
                .background(BoxHighlight)
                .padding(8.dp)
        )

        Column(modifier = Modifier.padding(
                start = horizontalTextMargin,
                end = horizontalTextMargin,
                bottom = S4
            )
            .align(Alignment.BottomStart)
        ) {
            Box(
                modifier = Modifier
                    .background(color = PlayBlue)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .align(Alignment.Start)
            ) {
                Text(
                    color = Color.White,
                    fontFamily = AlrightSansLtBoldItalic,
                    fontSize = 10.sp,
                    text = label.uppercase()
                )
            }

            Spacer(modifier = Modifier.height(verticalTextMargin))

            Text(
                text = title,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .background(BoxHighlight)
                    .fillMaxWidth(),
                style = TextStyle(
                    fontSize = when (viewport) {
                        Viewport.XLARGE -> TypographyLarge.T2XL
                        Viewport.LARGE -> TypographyLarge.T2XL
                        Viewport.MEDIUM -> TypographyMedium.T2XL
                        Viewport.SMALL -> TypographySmall.T2XL
                    },
                    fontFamily = AlrightSansLtBlack
                )
            )

            Spacer(modifier = Modifier.height(verticalTextMargin))

            Text(
                text = subtitle,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .background(BoxHighlight)
                    .fillMaxWidth(),
                style = TextStyle(
                    fontSize = when (viewport) {
                        Viewport.XLARGE -> TypographyLarge.TS
                        Viewport.LARGE -> TypographyLarge.TS
                        Viewport.MEDIUM -> TypographyMedium.TS
                        Viewport.SMALL -> TypographySmall.TS
                    },
                    fontFamily = AlrightSansLtRegular
                )
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
    val columnSpan = gridManager.columns()
    val marginCenteredTeaser = (gridManager.getScreenWidthDp() - gridManager.getColumnSpanWidth(columnSpan.toFloat())) / 2

    HorizontalPager(
        count = 10,
        state = pagerState,
        itemSpacing = gridManager.gutter(),
        contentPadding = PaddingValues(
            start = marginCenteredTeaser, top = 0.dp,
            end = marginCenteredTeaser, bottom = 0.dp
        )
    ) { index ->
        HeroTeaser(
            teaserWidthDp = gridManager.getColumnSpanWidth(
                columnSpan = gridManager.columns().toFloat()
            ),
            title = "Hero $index"
        )
    }
}
