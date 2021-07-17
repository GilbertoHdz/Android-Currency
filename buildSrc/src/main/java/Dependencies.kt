object Deps {

    // Kotlin
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib:${PluginVersion.KOTLIN_VERSION}"

    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Version.CORE_KTX_VERSION}"

    // Support Libraries and material
    // AppCompat
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Version.APPCOMPAT_VERSION}"

    // Material
    const val MATERIAL = "com.google.android.material:material:${Version.MATERIAL_VERSION}"

    // Fragment Ktx
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Version.FRAGMENT_KTX_VERSION}"

    // ConstraintLayout
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT_VERSION}"

    // ViewModel
    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.LIFECYCLE_VERSION}"

    // LiveData
    const val LIFECYCLE_LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.LIFECYCLE_VERSION}"
    
    // Navigation
    const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${Version.NAVIGATION_VERSION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Version.NAVIGATION_VERSION}"

    // Dagger Hilt
    const val DAGGER_HILT_ANDROID = "com.google.dagger:hilt-android:${Version.DAGGER_HILT_VERSION}"
    const val DAGGER_HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Version.DAGGER_HILT_VERSION}"

    // Gson
    const val GSON = "com.google.code.gson:gson:${Version.GSON_VERSION}"

    // Retrofit
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Version.RETROFIT_VERSION}"
    const val RETROFIT_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Version.RETROFIT_VERSION}"
    const val RETROFIT_RX_ADAPTER = "com.squareup.retrofit2:adapter-rxjava2:${Version.RETROFIT_VERSION}"

    // OkHttp
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Version.OKHTTP_VERSION}"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Version.OKHTTP_VERSION}"

    // RxKotlin
    const val RX_KOTLIN = "io.reactivex.rxjava2:rxkotlin:${Version.RX_KOTLIN_VERSION}"
    
    // RxAndroid
    const val RX_ANDROID = "io.reactivex.rxjava2:rxandroid:${Version.RX_ANDROID_VERSION}"
}

object TestDep {
    const val JUNIT = "junit:junit:${TestVersion.JUNIT_VERSION}"
    const val MOCKITO = "org.mockito:mockito-core:${TestVersion.MOCKITO_VERSION}"
    const val MOCKK = "io.mockk:mockk:${TestVersion.MOCKK_VERSION}"
    const val HAMCREST = "org.hamcrest:hamcrest:${TestVersion.HAMCREST_VERSION}"
    const val HAMCREST_LIBRARY = "org.hamcrest:hamcrest-library:${TestVersion.HAMCREST_VERSION}"
}