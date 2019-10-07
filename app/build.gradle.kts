plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.plugow.shoppingapp"
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf("room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true")
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    dataBinding {
        isEnabled = true
    }
}


androidExtensions {
    isExperimental = true
}

object Versions {
    const val anko_version = "0.10.8"
    const val dbflow_version = "4.2.4"
    const val rxjava_version = "2.1.16"
    const val rxandroid_version = "2.0.2"
    const val rxkotlin_version = "2.2.0"
    const val dagger_version = "2.24"
    const val android_binding = "2.2.0-alpha01"
    const val mockito_version = "2.27.0"
    const val kotlin_mockito_version = "2.1.0"
    const val mockk_version = "1.9.2"
    const val room_version = "2.2.0-rc01"
    const val rxbindings_version = "3.0.0"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(embeddedKotlin("stdlib-jdk8"))
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.activity:activity-ktx:1.0.0")
    implementation("androidx.fragment:fragment-ktx:1.1.0")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.vectordrawable:vectordrawable:1.0.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.1.1")
    androidTestImplementation("androidx.test:rules:1.1.1")
    implementation("androidx.cardview:cardview:1.0.0")

    //mockito
    testImplementation("org.mockito:mockito-core:${Versions.mockito_version}")
    testImplementation("org.mockito:mockito-inline:${Versions.mockito_version}")
    androidTestImplementation("org.mockito:mockito-android:${Versions.mockito_version}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.kotlin_mockito_version}")
    androidTestImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.kotlin_mockito_version}")
    androidTestImplementation("io.mockk:mockk-android:${Versions.mockk_version}")

    //dagger
    implementation("com.google.dagger:dagger-android:${Versions.dagger_version}")
    implementation("com.google.dagger:dagger-android-support:${Versions.dagger_version}")
    kapt("com.google.dagger:dagger-android-processor:${Versions.dagger_version}")
    implementation("com.google.dagger:dagger:${Versions.dagger_version}")
    kapt("com.google.dagger:dagger-compiler:${Versions.dagger_version}")

    //rxjava
    implementation("io.reactivex.rxjava2:rxandroid:${Versions.rxandroid_version}")
    implementation("io.reactivex.rxjava2:rxjava:${Versions.rxjava_version}")
    implementation("io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin_version}")
    implementation("com.jakewharton.rxbinding3:rxbinding:${Versions.rxbindings_version}")

    //anko
    implementation("org.jetbrains.anko:anko:${Versions.anko_version}")
    implementation("org.jetbrains.anko:anko-design:${Versions.anko_version}")

    // android data binding
    implementation("androidx.lifecycle:lifecycle-extensions:${Versions.android_binding}")
    implementation("androidx.lifecycle:lifecycle-viewmodel:${Versions.android_binding}")
    testImplementation("android.arch.core:core-testing:${Versions.android_binding}")
    androidTestImplementation("android.arch.core:core-testing:${Versions.android_binding}")

    //room
    implementation("androidx.room:room-runtime:${Versions.room_version}")
    implementation("androidx.room:room-rxjava2:${Versions.room_version}")
    kapt("androidx.room:room-compiler:${Versions.room_version}")

}
