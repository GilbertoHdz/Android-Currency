plugins {
    id(Plugins.JAVA_LIBRARY_PLUGIN)
    id(Plugins.KOTLIN_PLUGIN)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Deps.KOTLIN)
    implementation(Deps.RX_KOTLIN)
    implementation(Deps.RETROFIT)
}