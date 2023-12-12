package com.w2sv.wifiwidget.ui.screens.home.components.widget.configurationdialog.content.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.w2sv.common.utils.dynamicColorsSupported
import com.w2sv.domain.model.Theme
import com.w2sv.domain.model.WidgetColor
import com.w2sv.wifiwidget.R
import com.w2sv.wifiwidget.ui.components.ButtonColor
import com.w2sv.wifiwidget.ui.components.ThemeIndicatorProperties
import com.w2sv.wifiwidget.ui.components.ThemeSelectionRow
import com.w2sv.wifiwidget.ui.components.UseDynamicColorsRow
import com.w2sv.wifiwidget.ui.screens.home.components.widget.configurationdialog.content.components.colors.ColorSelection
import com.w2sv.wifiwidget.ui.utils.EPSILON
import com.w2sv.wifiwidget.ui.utils.circularTrifoldStripeBrush
import com.w2sv.wifiwidget.ui.utils.toColor
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf

@Composable
fun ThemeSelection(
    theme: Theme,
    customThemeSelected: Boolean,
    setTheme: (Theme) -> Unit,
    useDynamicColors: Boolean,
    setUseDynamicColors: (Boolean) -> Unit,
    customColorMap: ImmutableMap<WidgetColor, Int>,
    setCustomColor: (WidgetColor, Color) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val customThemeIndicatorWeight by animateFloatAsState(
            targetValue = if (useDynamicColors) EPSILON else 1f,
            label = "",
        )

        ThemeSelectionRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ((1 - customThemeIndicatorWeight) * 32).dp),
            customThemeIndicatorProperties = ThemeIndicatorProperties(
                theme = Theme.Custom,
                labelRes = R.string.custom,
                buttonColoring = ButtonColor.Gradient(
                    circularTrifoldStripeBrush(
                        customColorMap.getValue(WidgetColor.Background)
                            .toColor(),
                        customColorMap.getValue(WidgetColor.Primary)
                            .toColor(),
                        customColorMap.getValue(WidgetColor.Secondary)
                            .toColor(),
                    ),
                ),
            ),
            selected = theme,
            onSelected = setTheme,
            themeWeights = persistentMapOf(Theme.Custom to customThemeIndicatorWeight),
            themeIndicatorModifier = Modifier
                .padding(horizontal = 12.dp)
                .sizeIn(maxHeight = 92.dp),
        )

        AnimatedVisibility(visible = customThemeSelected) {
            ColorSelection(
                customColors = customColorMap,
                setCustomColor = setCustomColor,
                modifier = Modifier
                    .padding(top = 18.dp),
            )
        }

        if (dynamicColorsSupported) {
            UseDynamicColorsRow(
                useDynamicColors = useDynamicColors,
                onToggleDynamicColors = {
                    setUseDynamicColors(it)
                    if (it && customThemeSelected) {
                        setTheme(Theme.SystemDefault)
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .padding(top = 22.dp),
            )
        }
    }
}
