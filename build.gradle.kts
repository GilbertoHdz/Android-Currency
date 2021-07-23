// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  repositories {
    google()
    jcenter()
  }
  dependencies {
    classpath(Plugins.CLASSPATH_GRADLE)
    classpath(Plugins.CLASSPATH_KOTLIN_GRADLE)
    classpath(Plugins.CLASSPATH_DAGGER_HILT)
    classpath(Plugins.CLASSPATH_NAVIGATION_SAFEARGS)
  }
}

allprojects {
  repositories {
    google()
    jcenter()
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}