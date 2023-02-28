object Deps {

    // COMPOSE
    private const val activityComposeVersion = "1.6.1"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"

    const val composeCompilerVersion = "1.4.3"

    const val composeVersion = "1.4.0-beta02"
    private const val material3Version = "1.1.0-alpha07"
    const val composeUi = "androidx.compose.ui:ui:$composeVersion"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
    const val composeFoundation = "androidx.compose.foundation:foundation:$composeVersion"
    const val composeMaterial = "androidx.compose.material:material:$composeVersion"
    const val composeIconsExtended = "androidx.compose.material:material-icons-extended:$composeVersion"

    private const val composeNavigationVersion = "2.6.0-alpha06"
    const val composeNavigation = "androidx.navigation:navigation-compose:$composeNavigationVersion"

    private const val coilComposeVersion = "2.2.2"
    const val coilCompose = "io.coil-kt:coil-compose:$coilComposeVersion"

    // KOTLIN DATE TIME
    private const val dateTimeVersion = "0.4.0"
    const val kotlinDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:$dateTimeVersion"

    // KTOR
    private const val ktorVersion = "2.2.3"
    const val ktorCore = "io.ktor:ktor-client-core:$ktorVersion"
    const val ktorSerialization = "io.ktor:ktor-client-content-negotiation:$ktorVersion"
    const val ktorSerializationJson = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"
    const val ktorAndroid = "io.ktor:ktor-client-android:$ktorVersion"
    const val ktorIOS = "io.ktor:ktor-client-ios:$ktorVersion"

    // GRADLE PLUGINS
    const val kotlinVersion = "1.8.10"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

    private const val gradleVersion = "7.4.1"
    const val androidBuildTools = "com.android.tools.build:gradle:$gradleVersion"

    private const val sqlDelightGradleVersion = "2.0.0-alpha05"
    const val sqlDelightGradlePlugin = "app.cash.sqldelight:gradle-plugin:$sqlDelightGradleVersion"


    // SQLDELIGHT
    private const val sqlDelightVersion = "2.0.0-alpha05"
    const val sqlDelightRuntime = "app.cash.sqldelight:runtime:$sqlDelightVersion"
    const val sqlDelightAndroidDriver = "app.cash.sqldelight:android-driver:$sqlDelightVersion"
    const val sqlDelightNativeDriver = "app.cash.sqldelight:native-driver:$sqlDelightVersion"
    const val sqlDelightCoroutinesExtensions = "app.cash.sqldelight:coroutines-extensions:$sqlDelightVersion"

    // TESTING
    private const val assertKVersion = "0.25"
    const val assertK = "com.willowtreeapps.assertk:assertk:$assertKVersion"

    private const val turbineVersion = "0.7.0"
    const val turbine = "app.cash.turbine:turbine:$turbineVersion"

    private const val jUnitVersion = "4.13.2"
    const val jUnit = "junit:junit:$jUnitVersion"

    private const val testRunnerVersion = "1.5.1"
    const val testRunner = "androidx.test:runner:$testRunnerVersion"

    const val composeTesting = "androidx.compose.ui:ui-test-junit4:$composeVersion"
    const val composeTestManifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"

    // Lifecycle
    private const val lifecycleVersion = "2.5.1"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"

    // KMM Shared ViewModel
    private const val kmmViewModelVersion = "1.0.0-ALPHA-4"
    const val kmmViewModel = "com.rickclephas.kmm:kmm-viewmodel-core:$kmmViewModelVersion"

    object Koin {
        private const val koinVersion = "3.3.3"
        const val core = "io.insert-koin:koin-core:$koinVersion"
        const val test = "io.insert-koin:koin-test:$koinVersion"
        const val android = "io.insert-koin:koin-android:$koinVersion"

        private const val koinComposeVersion = "3.4.2"
        const val compose = "io.insert-koin:koin-androidx-compose:$koinComposeVersion"
    }
}