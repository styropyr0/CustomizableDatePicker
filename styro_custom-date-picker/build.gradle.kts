plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish") // Ensure the plugin is applied
}

android {
    namespace = "com.matrix.styro_custom_date_picker"
    compileSdk = 35

    defaultConfig {
        minSdk = 30
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.styropyr0"
                artifactId = "styro-custom-date-picker"
                version = "1.0.1"

                pom {
                    name.set("Customizable Date Picker")
                    description.set("A custom date picker library for Android.")
                    url.set("https://github.com/styropyr0/CustomizableDatePicker")
                    licenses {
                        license {
                            name.set("Apache License 2.0")
                            url.set("https://opensource.org/licenses/Apache-2.0")
                        }
                    }
                    developers {
                        developer {
                            id.set("styropyr0")
                            name.set("Saurav Sajeev")
                            email.set("sauravsajeev202@gmail.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:github.com/styropyr0/CustomizableDatePicker.git")
                        developerConnection.set("scm:git:ssh://github.com/styropyr0/CustomizableDatePicker.git")
                        url.set("https://github.com/styropyr0/CustomizableDatePicker")
                    }
                }
            }
        }
    }
}
