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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dk.tv2.layout.grid.theme.GridTheme
import dk.tv2.layout.grid.ui.Viewport
import dk.tv2.layout.grid.ui.grid.GridColumn
import dk.tv2.layout.grid.ui.grid.GridGutter
import dk.tv2.layout.grid.ui.grid.GridMarginEnd
import dk.tv2.layout.grid.ui.grid.GridMarginStart
import dk.tv2.layout.grid.ui.teaser.*
import dk.tv2.layout.grid.ui.view.KeyValueText
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
                    val checkedStateShowColumns = rememberSaveable { mutableStateOf(true) }
                    val checkedStateShowGutters = rememberSaveable { mutableStateOf(false) }
                    val checkedStateShowMargins = rememberSaveable { mutableStateOf(false) }

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
                                        val verticalSpacing = 8.dp
                                        AlertDialog(
                                            onDismissRequest = { openInfoDialog.value = false },
                                            title = { Text(text = "Info") },
                                            text = {
                                                Column {
                                                    KeyValueText(key = "Viewport:", value = gridManager.getViewport().name)
                                                    KeyValueText(key = "Gutter:", value = gridManager.gutter().toString())
                                                    Spacer(modifier = Modifier.height(verticalSpacing))
                                                    KeyValueText(key = "Margin start:", value = gridManager.marginStart().toString())
                                                    KeyValueText(key = "Margin end:", value = gridManager.marginEnd().toString())
                                                    Spacer(modifier = Modifier.height(verticalSpacing))
                                                    KeyValueText(key = "Screen density:", value = LocalDensity.current.density.toString())
                                                    Spacer(modifier = Modifier.height(verticalSpacing))
                                                    KeyValueText(key = "Screen width:", value = gridManager.getScreenWidthDp().toString())
                                                    KeyValueText(key = "Screen height:", value = gridManager.getScreenHeightDp().toString())
                                                    Spacer(modifier = Modifier.height(verticalSpacing))
                                                    KeyValueText(key = "Window width:", value = gridManager.getWindowWidthDp().toString())
                                                    KeyValueText(key = "Window height:", value = gridManager.getWindowHeightDp().toString())
                                                    Spacer(modifier = Modifier.height(verticalSpacing))
                                                    KeyValueText(key = "Column width = ${gridManager.columns()}:", value = gridManager.getColumnSpanWidth(columnSpan = gridManager.columns()).toString())
                                                    KeyValueText(key = "Column width = 1:", value = gridManager.getColumnSpanWidth(columnSpan = 1).toString())
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
                            if (BuildConfig.DEBUG) {
                                Row {
                                    GridMarginStart(
                                        gridManager = gridManager,
                                        visible = checkedStateShowMargins.value
                                    )

                                    for (i in 0 until gridManager.columns()) {
                                        GridColumn(
                                            gridManager = gridManager,
                                            visible = checkedStateShowColumns.value
                                        )

                                        if (i < (gridManager.columns() - 1)) {
                                            GridGutter(
                                                gridManager = gridManager,
                                                visible = checkedStateShowGutters.value
                                            )
                                        }
                                    }

                                    GridMarginEnd(
                                        gridManager = gridManager,
                                        visible = checkedStateShowMargins.value
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
                                                Viewport.LARGE -> 2
                                                Viewport.MEDIUM -> 3
                                                Viewport.SMALL -> 4
                                            }
                                        ),
                                        title = "CPT #$index"
                                    )
                                }

                                // Series teasers.
                                TeaserRow(gridManager, "Popular") { index ->
                                    SeriesTeaser(
                                        teaserWidthDp = gridManager.getColumnSpanWidth(
                                            columnSpan = when (gridManager.getViewport()) {
                                                Viewport.XLARGE -> 4
                                                Viewport.LARGE -> 4
                                                Viewport.MEDIUM -> 6
                                                Viewport.SMALL -> 9
                                            }
                                        ),
                                        title = "ST #$index"
                                    )
                                }

                                // Episode teasers.
                                TeaserRow(gridManager, "Continue watching") { index ->
                                    EpisodeTeaser(
                                        teaserWidthDp = gridManager.getColumnSpanWidth(
                                            columnSpan = when (gridManager.getViewport()) {
                                                Viewport.XLARGE -> 3
                                                Viewport.LARGE -> 3
                                                Viewport.MEDIUM -> 4
                                                Viewport.SMALL -> 6
                                            }
                                        ),
                                        title = "ET #$index"
                                    )
                                }

                                // Movie teasers.
                                TeaserRow(gridManager, "Movies") { index ->
                                    MovieTeaser(
                                        teaserWidthDp = gridManager.getColumnSpanWidth(
                                            columnSpan = when (gridManager.getViewport()) {
                                                Viewport.XLARGE -> 3
                                                Viewport.LARGE -> 3
                                                Viewport.MEDIUM -> 4
                                                Viewport.SMALL -> 4
                                            }
                                        ),
                                        title = "MT #$index"
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
                                TeaserRow(gridManager, "Test 4") { index ->
                                    ContentProviderTeaser(gridManager.getColumnSpanWidth(columnSpan = 4), "T #4.$index")
                                }
                                TeaserRow(gridManager, "Test 5") { index ->
                                    ContentProviderTeaser(gridManager.getColumnSpanWidth(columnSpan = 5), "T #5.$index")
                                }
                                TeaserRow(gridManager, "Test 6") { index ->
                                    ContentProviderTeaser(gridManager.getColumnSpanWidth(columnSpan = 6), "T #6.$index")
                                }
                                TeaserRow(gridManager, "Test 7") { index ->
                                    ContentProviderTeaser(gridManager.getColumnSpanWidth(columnSpan = 7), "T #7.$index")
                                }
                                TeaserRow(gridManager, "Test 8") { index ->
                                    ContentProviderTeaser(gridManager.getColumnSpanWidth(columnSpan = 8), "T #8.$index")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
