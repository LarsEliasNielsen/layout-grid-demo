package dk.tv2.layout.grid

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dk.tv2.layout.grid.theme.GridTheme
import dk.tv2.layout.grid.ui.Viewport
import dk.tv2.layout.grid.ui.grid.GridColumn
import dk.tv2.layout.grid.ui.grid.GridGutter
import dk.tv2.layout.grid.ui.grid.GridMarginEnd
import dk.tv2.layout.grid.ui.grid.GridMarginStart
import dk.tv2.layout.grid.ui.teaser.*
import dk.tv2.layout.grid.ui.view.LabelCheckbox
import dk.tv2.layout.grid.ui.view.LabelIcon

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gridManager = GridManager(this)

        setContent {
            GridTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val checkedStateShowColumns = remember { mutableStateOf(true) }
                    val checkedStateShowGutters = remember { mutableStateOf(false) }
                    val checkedStateShowMargins = remember { mutableStateOf(false) }

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "${stringResource(R.string.app_name)} (viewport: ${gridManager.getViewport().name})")
                                },
                                actions = {
                                    val expandedActionMenu = remember { mutableStateOf(false) }
                                    val openInfoDialog = remember { mutableStateOf(false)  }

                                    IconButton(onClick = { expandedActionMenu.value = true }) {
                                        Icon(
                                            imageVector = Icons.Default.Settings,
                                            contentDescription = stringResource(R.string.menu_action_settings)
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = expandedActionMenu.value,
                                        onDismissRequest = { expandedActionMenu.value = false }
                                    ) {
                                        LabelCheckbox(checkedStateShowColumns, stringResource(R.string.menu_action_settings_show_columns))
                                        LabelCheckbox(checkedStateShowGutters, stringResource(R.string.menu_action_settings_show_gutters))
                                        LabelCheckbox(checkedStateShowMargins, stringResource(R.string.menu_action_settings_show_margins))
                                        LabelIcon(openInfoDialog, stringResource(R.string.menu_action_settings_show_info))
                                    }

                                    if (openInfoDialog.value) {
                                        AlertDialog(
                                            onDismissRequest = { openInfoDialog.value = false },
                                            title = { Text(text = "Info") },
                                            text = {
                                                Column {
                                                    Text(text = "WindowSizeClass: ${gridManager.getViewport().name}")
                                                    Text(text = "getWindowDpSize.width: ${gridManager.getWindowDpSize().width}")
                                                    Text(text = "getWindowDpSize.height: ${gridManager.getWindowDpSize().height}")
                                                    Text(text = "getWidth = ${gridManager.columns}: ${gridManager.getColumnSpanWidth(columnSpan = gridManager.columns)}")
                                                    Text(text = "getWidth = 1: ${gridManager.getColumnSpanWidth(columnSpan = 1)}")
                                                    Text(text = "getColumnWidth: ${gridManager.getColumnWidth()}")
                                                }
                                            },
                                            confirmButton = {
                                                Button(onClick = { openInfoDialog.value = false }) {
                                                    Text(text = "OK")
                                                }
                                            }
                                        )
                                    }
                                }
                            )
                        },
                        content = {
                            // Layout grid for debugging.
                            @OptIn(ExperimentalComposeUiApi::class)
                            if (BuildConfig.DEBUG) {
                                Box(Modifier.fillMaxSize()) {
                                    GridMarginStart(
                                        gridManager = gridManager,
                                        visible = checkedStateShowMargins.value,
                                        modifier = Modifier.offset(x = 0.dp)
                                    )

                                    for (i in 0..11) {
                                        GridColumn(
                                            gridManager = gridManager,
                                            visible = checkedStateShowColumns.value,
                                            modifier = Modifier.offset(x = gridManager.marginStart() + (gridManager.getColumnWidth() * i) + (gridManager.gutter() * i))
                                        )
                                    }

                                    for (i in 0..10) {
                                        GridGutter(
                                            gridManager = gridManager,
                                            visible = checkedStateShowGutters.value,
                                            modifier = Modifier.offset(x = gridManager.marginStart() + (gridManager.getColumnWidth() * (i + 1)) + (gridManager.gutter() * i))
                                        )
                                    }

                                    GridMarginEnd(
                                        gridManager = gridManager,
                                        visible = checkedStateShowMargins.value,
                                        modifier = Modifier.offset(x = gridManager.getWindowDpSize().width - gridManager.marginEnd())
                                    )
                                }
                            }

                            // Teaser content.
                            Column(
                                modifier = Modifier.verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(32.dp)
                            ) {
                                // Hero teasers.
                                HeroTeaserPager(gridManager)

                                // Content provider teasers.
                                TeaserRow(gridManager) { index ->
                                    ContentProviderTeaser(
                                        teaserWidthDp = gridManager.getColumnSpanWidth(
                                            columnSpan = when (gridManager.getViewport()) {
                                                Viewport.XLARGE -> 2
                                                Viewport.LARGE -> 3
                                                Viewport.SMALL -> 4
                                            }
                                        ),
                                        title = "CPT #$index"
                                    )
                                }

                                // Series teasers.
                                TeaserRow(gridManager, "Popular") {
                                    SeriesTeaser(
                                        teaserWidthDp = gridManager.getColumnSpanWidth(
                                            columnSpan = when (gridManager.getViewport()) {
                                                Viewport.XLARGE -> 4
                                                Viewport.LARGE -> 6
                                                Viewport.SMALL -> 10
                                            }
                                        )
                                    )
                                }

                                // Episode teasers.
                                TeaserRow(gridManager, "Continue watching") {
                                    EpisodeTeaser(
                                        teaserWidthDp = gridManager.getColumnSpanWidth(
                                            columnSpan = when (gridManager.getViewport()) {
                                                Viewport.XLARGE -> 3
                                                Viewport.LARGE -> 4
                                                Viewport.SMALL -> 6
                                            }
                                        )
                                    )
                                }

                                // Movie teasers.
                                TeaserRow(gridManager, "Movies") {
                                    MovieTeaser(
                                        teaserWidthDp = gridManager.getColumnSpanWidth(
                                            columnSpan = when (gridManager.getViewport()) {
                                                Viewport.XLARGE -> 2
                                                Viewport.LARGE -> 3
                                                Viewport.SMALL -> 4
                                            }
                                        )
                                    )
                                }

                                // Test teasers.
                                TeaserRow(gridManager, "Test 1") { index ->
                                    ContentProviderTeaser(gridManager.getColumnSpanWidth(columnSpan = 1), "T #1.$index")
                                }
                                TeaserRow(gridManager, "Test 2") { index ->
                                    ContentProviderTeaser(gridManager.getColumnSpanWidth(columnSpan = 2), "T #2.$index")
                                }
                                TeaserRow(gridManager, "Test 3") { index ->
                                    ContentProviderTeaser(gridManager.getColumnSpanWidth(columnSpan = 3), "T #3.$index")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
