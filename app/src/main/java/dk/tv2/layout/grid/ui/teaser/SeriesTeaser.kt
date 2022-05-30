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
import dk.tv2.layout.grid.theme.*
import dk.tv2.layout.grid.ui.Viewport

/**
 * Teaser showing episode series.
 */
@Preview
@Composable
fun SeriesTeaser(
    viewport: Viewport = Viewport.SMALL,
    teaserWidthDp: Dp = 100.dp,
    label: String = "Series label",
    title: String = "Series title",
    subtitle: String = "Series subtitle"
) {
    val logoStartMargin: Dp = when (viewport) {
        Viewport.XLARGE -> S4
        Viewport.LARGE -> S4
        Viewport.MEDIUM -> S2
        Viewport.SMALL -> S2
    }
    val horizontalTextMargin: Dp = when (viewport) {
        Viewport.XLARGE -> S4
        Viewport.LARGE -> S4
        Viewport.MEDIUM -> S2
        Viewport.SMALL -> S2
    }
    val verticalTextMargin: Dp = when (viewport) {
        Viewport.XLARGE -> S2
        Viewport.LARGE -> S2
        Viewport.MEDIUM -> S1
        Viewport.SMALL -> S1
    }
    val bottomMargin: Dp = when (viewport) {
        Viewport.XLARGE -> S5
        Viewport.LARGE -> S5
        Viewport.MEDIUM -> S2
        Viewport.SMALL -> S2
    }

    Box(modifier = Modifier
        .width(teaserWidthDp)
        .aspectRatio(16f / 9f, false)
        .background(Color(0xff957dad))
    ) {
        Text(
            text = "Logo",
            modifier = Modifier
                .padding(
                    start = logoStartMargin,
                    top = S2
                )
                .background(BoxHighlight)
                .padding(8.dp)
        )

        Column(
            modifier = Modifier.padding(
                start = horizontalTextMargin,
                end = horizontalTextMargin,
                bottom = bottomMargin
            )
            .align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .background(color = PlayBlue)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .align(Alignment.CenterHorizontally)
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
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .background(BoxHighlight)
                    .fillMaxWidth(),
                style = TextStyle(
                    fontSize = when (viewport) {
                        Viewport.XLARGE -> TypographyLarge.TL
                        Viewport.LARGE -> TypographyLarge.TL
                        Viewport.MEDIUM -> TypographyMedium.TL
                        Viewport.SMALL -> TypographySmall.TL
                    },
                    fontFamily = AlrightSansLtBlack
                )
            )

            Spacer(modifier = Modifier.height(verticalTextMargin))

            Text(
                text = subtitle,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .background(BoxHighlight)
                    .fillMaxWidth(),
                style = TextStyle(
                    fontSize = when (viewport) {
                        Viewport.XLARGE -> TypographyLarge.T1XS
                        Viewport.LARGE -> TypographyLarge.T1XS
                        Viewport.MEDIUM -> TypographyMedium.T1XS
                        Viewport.SMALL -> TypographySmall.T1XS
                    },
                    fontFamily = AlrightSansLtRegular
                )
            )
        }
    }
}
