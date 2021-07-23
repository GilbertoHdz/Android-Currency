
plugins {
    id(Plugins.ANDROID_LIBRARY_PLUGIN)
    id(Plugins.KOTLIN_ANDROID_PLUGIN)
    id(Plugins.KOTLIN_ANDROID_EXTENSIONS_PLUGIN)
    id(Plugins.KOTLIN_KAPT_PLUGIN)
    id(Plugins.DAGGER_HILT_PLUGIN)
}

android {
    compileSdkVersion(AndroidVersion.COMPILE_SDK_VERSION)
    
    defaultConfig {
        minSdkVersion(AndroidVersion.MIN_SDK_VERSION)
        targetSdkVersion(AndroidVersion.TARGET_SDK_VERSION)
        versionCode = AndroidVersion.VERSION_CODE
        versionName = AndroidVersion.VERSION_NAME
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    
    implementation(project(":libraries:data"))
    implementation(project(":libraries:utils"))
    implementation(project(":libraries:interactors"))
    
    implementation(Deps.RX_KOTLIN)
    implementation(Deps.RX_ANDROID)
    implementation(Deps.KOTLIN)
    implementation(Deps.FRAGMENT_KTX)
    implementation(Deps.ANDROIDX_CORE_KTX)
    
    // Dagger Hilt
    implementation(Deps.DAGGER_HILT_ANDROID)
    kapt(Deps.DAGGER_HILT_COMPILER)
    
    // ROOM DB
    implementation(Deps.ROOM_DB)
    implementation(Deps.ROOM_DB_KTX)
    kapt(Deps.ROOM_DB_COMPILER)
    
    // Work manager
    implementation(Deps.WORK_HILT)
    kapt(Deps.WORK_HILT_COMPILER)
    implementation (Deps.WORK_KOTLIN)
    implementation(Deps.WORK_MULTIPROCESS)
}