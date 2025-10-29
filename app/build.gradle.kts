plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Plugin necesario para que Room funcione. Procesa las anotaciones como @Entity, @Dao, etc.
    kotlin("kapt")

}

android {
    namespace = "com.example.tpcrud"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.tpcrud"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    // Definimos versiones en variables para mantener la consistencia y facilitar futuras actualizaciones.
    val room_version = "2.6.1"
    val lifecycle_version = "2.8.3"

    // --- Dependencias de Room --- 
    // Es la librería principal de la base de datos. Proporciona la funcionalidad del ORM.
    implementation("androidx.room:room-runtime:$room_version")
    // Proporciona soporte para Coroutines y Flow, facilitando las operaciones asíncronas.
    implementation("androidx.room:room-ktx:$room_version") 
    // Es el procesador de anotaciones de Room. Es el que "entiende" @Entity, @Dao, etc.
    kapt("androidx.room:room-compiler:$room_version")

    // --- Dependencias de ViewModel --- 
    // Proporciona el componente ViewModel y la extensión `viewModelScope` para coroutines.
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    // --- Dependencias estándar de Android ---
    implementation(libs.androidx.core.ktx) // Extensiones de Kotlin para las APIs de Android.
    implementation(libs.androidx.appcompat) // Proporciona compatibilidad hacia atrás para la UI.
    implementation(libs.material) // Componentes de diseño Material Design.
    implementation(libs.androidx.activity) // Manejo del ciclo de vida de las Activities.
    implementation(libs.androidx.constraintlayout) // Para construir layouts complejos.
    implementation(libs.androidx.fragment) // Para manejar fragmentos.
    testImplementation(libs.junit) // Para pruebas unitarias.
    androidTestImplementation(libs.androidx.junit) // Para pruebas de instrumentación.
    androidTestImplementation(libs.androidx.espresso.core) // Para pruebas de UI.
}