@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Apps.compileSdk)
    defaultConfig {
        applicationId = "n7.myperfectemptyproject"
        minSdkVersion(Apps.minSdk)
        targetSdkVersion(Apps.targetSdk)
//        versionCode = ext.get("gitCommitCount") as? Int
        versionCode = Apps.versionCode
        versionName = Apps.versionName
//        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        setProperty("archivesBaseName", "$applicationId-v$versionName($versionCode)")
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf("room.incremental" to "true")
            }
        }
    }
    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }
    signingConfigs {
        getByName("debug") {
        }
        create("release") {
            keyAlias = "key"
            keyPassword = "111111"
            storePassword = "111111"
            storeFile = file("/Users/i30mb1/Android/Key/keystore")
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-dev"
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
//    productFlavors {
//            create("full") {
//            }
//            create("trial") {
//            }
//    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            noStdlib = true
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            freeCompilerArgs = listOf("-Xallow-result-return-type")
        }
    }
    dataBinding {
        isEnabled = true
    }
    lintOptions {
        isAbortOnError = false
        isIgnoreWarnings = true
        isQuiet = true
    }
    androidExtensions {
        isExperimental = true
    }
    packagingOptions {
        exclude("META-INF/LICENSE")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Starting with Kotlin 1.1.2, the dependencies with group org.jetbrains.kotlin are by default resolved with the version taken from the applied plugin
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.60")
    implementation(kotlin("stdlib-jdk7"))
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("com.google.android.material:material:1.2.0-alpha03")
    //    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // --- Coroutines ---
    val coroutines = "1.3.2"
    val lifecycle_version = "2.2.0-rc03"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
    // для тестирования и ...
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")
    // lifecycleScope + launchWhenResumed
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    // liveData ( LiveData + coroutines)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // viewModelScope
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    kapt("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")
    // Firebase
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutines")

    // --- Dagger ---
    val dagger = "2.25.2"
    implementation("com.google.dagger:dagger:$dagger")
    kapt("com.google.dagger:dagger-compiler:$dagger")
    compileOnly("com.squareup.inject:assisted-inject-annotations-dagger2:0.5.2")
    kapt("com.squareup.inject:assisted-inject-processor-dagger2:0.5.2")

    // --- Coil ---
    val coil = "0.8.0"
    implementation("io.coil-kt:coil:$coil")

    //  --- Navigation ---
    val navigation = "2.2.0-beta01"
    // Fragment.findNavController + Fragment.navArgs
    implementation("androidx.navigation:navigation-fragment-ktx:$navigation")
    // setupActionBarWithNavController + setupWithNavController
    implementation("androidx.navigation:navigation-ui-ktx:$navigation")
    // Activity.findNavController + Activity.navArgs + View.findNavController
    implementation("androidx.navigation:navigation-runtime-ktx:$navigation")
    implementation("androidx.lifecycle:lifecycle-common-java8:$navigation")
    // для легких транзакций + by viewModels()
    implementation("androidx.fragment:fragment-ktx:1.2.0-beta01")
    // on BackPress support for Fragment
    implementation("androidx.activity:activity-ktx:1.1.0-beta01")

    // --- Retrofit ---
    val retrofit = "2.6.2"
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit")
    // --- Moshi ---
    val moshi = "1.9.2"
    implementation("com.squareup.moshi:moshi:$moshi")
    // generate code in compile time instead of using runtime reflection
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshi")

    // --- Room ---
    val room = "2.2.2"
    implementation("androidx.room:room-runtime:$room")
    kapt("androidx.room:room-compiler:$room")
    // kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room")

    // --- SharedPreferences ---
    implementation("androidx.preference:preference-ktx:1.1.0")

    // --- RxJava2 ---
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.12")
}
