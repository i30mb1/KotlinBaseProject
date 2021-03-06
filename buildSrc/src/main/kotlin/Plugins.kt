import org.gradle.kotlin.dsl.kotlin
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

fun PluginDependenciesSpec.androidLibrary(): PluginDependencySpec = id("com.android.library")

fun PluginDependenciesSpec.benchmark(): PluginDependencySpec = id("androidx.benchmark")

fun PluginDependenciesSpec.hilt(): PluginDependencySpec = id("dagger.hilt.android.plugin")

fun PluginDependenciesSpec.dynamicFeature(): PluginDependencySpec = id("com.android.dynamic-feature")

fun PluginDependenciesSpec.androidApp(): PluginDependencySpec = id("com.android.application")

fun PluginDependenciesSpec.library(): PluginDependencySpec = id("com.android.library")

fun PluginDependenciesSpec.safeargs(): PluginDependencySpec = id("androidx.navigation.safeargs.kotlin")

fun PluginDependenciesSpec.junit5(): PluginDependencySpec = id("de.mannodermaus.android-junit5")

fun PluginDependenciesSpec.kotlinAndroid(): PluginDependencySpec = kotlin("android")

fun PluginDependenciesSpec.kotlinAndroidExt(): PluginDependencySpec = kotlin("android.extensions") // kotlin synthetics

fun PluginDependenciesSpec.kotlinKapt(): PluginDependencySpec = kotlin("kapt")