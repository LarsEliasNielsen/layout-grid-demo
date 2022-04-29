package dk.tv2.layout.grid

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dk.tv2.layout.grid.theme.GridTheme
import dk.tv2.layout.grid.ui.grid.GridColumn
import dk.tv2.layout.grid.ui.grid.GridGutter
import dk.tv2.layout.grid.ui.grid.GridMarginEnd
import dk.tv2.layout.grid.ui.grid.GridMarginStart
import dk.tv2.layout.grid.ui.teaser.ContentProviderTeaser
import dk.tv2.layout.grid.ui.teaser.TeaserRow
import dk.tv2.layout.grid.ui.view.LabelCheckbox

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
                                    Text(text = stringResource(R.string.app_name))
                                },
                                actions = {
                                    val expandedActionMenu = remember { mutableStateOf(false) }

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
                                    }
                                }
                            )
                        },
                        content = {
                            if (BuildConfig.DEBUG) {
                                Row(Modifier.fillMaxSize()) {
                                    GridMarginStart(gridManager, checkedStateShowMargins.value)
                                    for (i in 1..gridManager.columns) {
                                        GridColumn(gridManager, checkedStateShowColumns.value)
                                        if (i != gridManager.columns) {
                                            GridGutter(gridManager, checkedStateShowGutters.value)
                                        }
                                    }
                                    GridMarginEnd(gridManager, checkedStateShowMargins.value)
                                }
                            }

                            Column(
                                modifier = Modifier.verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(32.dp)
                            ) {
                                TeaserRow(gridManager, "Popular 1") { index ->
                                    ContentProviderTeaser(gridManager.getColumnSpanWidth(columnSpan = 1), "CPT #$index")
                                }
                                TeaserRow(gridManager, "Popular 2") { index ->
                                    ContentProviderTeaser(gridManager.getColumnSpanWidth(columnSpan = 2), "CPT #$index")
                                }
                                TeaserRow(gridManager, "Popular 3") { index ->
                                    ContentProviderTeaser(gridManager.getColumnSpanWidth(columnSpan = 3), "CPT #$index")
                                }
                            }

//                            Column {
//                                Text(text = "WindowSizeClass: ${gridManager.getWindowWidth().name}", color = Color.White)
//                                Text(text = "getWindowDpSize.width: ${gridManager.getWindowDpSize().width}", color = Color.White)
//                                Text(text = "getWindowDpSize.height: ${gridManager.getWindowDpSize().height}", color = Color.White)
//                                Text(text = "getWidth = ${gridManager.columns}: ${gridManager.getColumnSpanWidth(columnSpan = gridManager.columns)}", color = Color.White)
//                                Text(text = "getWidth = 1: ${gridManager.getColumnSpanWidth(columnSpan = 1)}", color = Color.White)
//                                Text(text = "getColumnWidth: ${gridManager.getColumnWidth()}", color = Color.White)
//                            }
                        }
                    )
                }
            }
        }
    }
}
