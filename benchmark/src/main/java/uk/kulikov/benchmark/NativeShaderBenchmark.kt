package uk.kulikov.benchmark

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.MemoryUsageMetric
import androidx.benchmark.macro.MemoryUsageMetric.Mode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import junit.framework.TestCase.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class NativeShaderBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @RequiresApi(Build.VERSION_CODES.Q)
    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "uk.kulikov.webgl",
        metrics = metrics(),
        iterations = 5,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
        }
    ) {
        startActivityAndWait()
        val btn = By.text("Native")
        if (!device.wait(Until.hasObject(btn), 5000L)) {
            fail("Could not find resource in time")
        }

        device.findObject(btn).click()

        val rootView = By.res("uk.kulikov.webgl:id/root_webgl_view")
        if (!device.wait(Until.hasObject(rootView), 5000L)) {
            fail("Could not find resource in time")
        }
    }
}