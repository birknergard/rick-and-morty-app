plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // Changed KSP version to newest, other was too old
    id("com.google.devtools.ksp") version "2.0.21-1.0.26" apply false
}

