package extensions

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler


/**
 * Adds dependencies to libraries module
 */
fun DependencyHandler.addCoreLibraryModuleDependencies() {
    implementation(Deps.KOTLIN)
    implementation(Deps.ANDROIDX_CORE_KTX)
    implementation(Deps.RX_KOTLIN)
    implementation(Deps.RX_ANDROID)
    
    // Support and Widgets
    implementation(Deps.APPCOMPAT)
    implementation(Deps.MATERIAL)
    implementation(Deps.CONSTRAINT_LAYOUT)
    implementation(Deps.FRAGMENT_KTX)

    // Lifecycle, LiveData, ViewModel
    implementation(Deps.LIFECYCLE_LIVEDATA_KTX)
    implementation(Deps.LIFECYCLE_VIEWMODEL_KTX)
    
    // Navigation Components
    implementation(Deps.NAVIGATION_FRAGMENT)
    implementation(Deps.NAVIGATION_UI)
    
    // Dagger Hilt
    implementation(Deps.DAGGER_HILT_ANDROID)
    kapt(Deps.DAGGER_HILT_COMPILER)
}


/**
 * Adds Test dependencies to libraries module
 */
fun DependencyHandler.addTestLibraryModuleDependencies() {
    testImplementation(TestDep.JUNIT)
    testImplementation(TestDep.MOCKITO)
    testImplementation(TestDep.MOCKK)
    testImplementation(TestDep.HAMCREST)
    testImplementation(TestDep.HAMCREST_LIBRARY)
}

/*
 * These extensions mimic the extensions that are generated on the fly by Gradle.
 * They are used here to provide above dependency syntax that mimics Gradle Kotlin DSL
 * syntax in module\build.gradle.kts files.
 */
@Suppress("detekt.UnusedPrivateMember")
private fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

@Suppress("detekt.UnusedPrivateMember")
private fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

@Suppress("detekt.UnusedPrivateMember")
private fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)
