package ad2.n7.benchmark

import android.content.Context
import android.util.Log
import android.util.TimingLogger
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.system.measureNanoTime

/**
 * measureNanoTime {  }
 */

/**
 * We can take info in profiler via :
 * Trace.beginSection("onBind")
 * ...
 * Trace.endSection()
 */

/**
 * Also we can use Kotlin exteinsion to measure time
 * val time = measureNanoTime { ... }
 */

/**
 * Benchmark, which will execute on an Android device.
 *
 * The body of [BenchmarkRule.measureRepeated] is measured in a loop, and Studio will
 * output the result. Modify your code to see how it affects performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()
    val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun log() {
        benchmarkRule.measureRepeated {
            Log.d("LogBenchmark", "the cost of writing this log method will be measured")
        }
    }
}
