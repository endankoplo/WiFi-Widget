package com.w2sv.domain.model

import androidx.annotation.StringRes
import com.w2sv.domain.R

enum class WidgetRefreshingParameter(
    @StringRes override val labelRes: Int,
    val defaultIsEnabled: Boolean
) :
    WidgetProperty {
    RefreshPeriodically(R.string.refresh_periodically, true),
    RefreshOnLowBattery(R.string.refresh_on_low_battery, true),
    DisplayLastRefreshDateTime(R.string.display_last_refresh_time, true)
}
