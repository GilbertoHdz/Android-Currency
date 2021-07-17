object Plugins {
    // Project Level
    const val CLASSPATH_GRADLE = "com.android.tools.build:gradle:${PluginVersion.GRADLE_VERSION}"
    const val CLASSPATH_KOTLIN_GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginVersion.KOTLIN_VERSION}"
    const val CLASSPATH_DAGGER_HILT = "com.google.dagger:hilt-android-gradle-plugin:${Version.DAGGER_HILT_VERSION}"

    // Module Level
    const val ANDROID_APPLICATION_PLUGIN = "com.android.application"
    const val ANDROID_LIBRARY_PLUGIN = "com.android.library"
    const val JAVA_LIBRARY_PLUGIN = "java-library"
    const val KOTLIN_ANDROID_PLUGIN = "kotlin-android"
    const val KOTLIN_KAPT_PLUGIN = "kotlin-kapt"
    const val KOTLIN_PLUGIN = "kotlin"
    const val KOTLIN_ANDROID_EXTENSIONS_PLUGIN = "kotlin-android-extensions"
    const val DAGGER_HILT_PLUGIN = "dagger.hilt.android.plugin"
}