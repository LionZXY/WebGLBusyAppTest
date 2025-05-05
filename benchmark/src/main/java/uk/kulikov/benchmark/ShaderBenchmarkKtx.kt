package uk.kulikov.benchmark

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MemoryUsageMetric
import androidx.benchmark.macro.MemoryUsageMetric.Mode
import androidx.benchmark.macro.PowerCategory
import androidx.benchmark.macro.PowerCategoryDisplayLevel
import androidx.benchmark.macro.PowerMetric
import androidx.benchmark.macro.StartupTimingMetric

@OptIn(ExperimentalMetricApi::class)
private val categories = PowerCategory.entries
    .associateWith { PowerCategoryDisplayLevel.TOTAL }

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMetricApi::class)
fun metrics() = listOf(
    StartupTimingMetric(),
    MemoryUsageMetric(Mode.Max),
    FrameTimingMetric(),
    PowerMetric(PowerMetric.Type.Energy(categories)),
    PowerMetric(PowerMetric.Type.Power(categories))
)