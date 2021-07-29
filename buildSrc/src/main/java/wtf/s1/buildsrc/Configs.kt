package wtf.s1.buildsrc

object Versions{
    const val pudge = "0.1.4"
    const val pudgeDev = "0.3.0-SNAPSHOT"
    const val asm = "8.0.1"
    const val kotlin = "1.5.0"
    const val ktx = "1.0.0"
    const val coroutines = "1.3.2"
    const val gradlePlugin ="4.1.0"
    const val lifecycle = "2.2.0"
    const val compileSdkVersion = 28
    const val minSdkVersion = 15
    const val targetSdkVersion = 28
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val bytex ="0.3.0"
}

object Plugins{
    const val androidLib = "com.android.library"
}

object Deps{

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val coroutines =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
        const val extension = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
        const val livedata = "androidx.lifecycle:lifecycle-livedata:${Versions.lifecycle}"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

        object Core {
            const val utils = "androidx.legacy:legacy-support-core-utils:1.0.0"
        }
    }

    object ByteX {
        const val common = "com.bytedance.android.byteX:common:${Versions.bytex}"
        const val base = "com.bytedance.android.byteX:base-plugin:${Versions.bytex}"
    }

    object ASM {
        const val asm = "org.ow2.asm:asm:${Versions.asm}"
        const val asmUtil = "org.ow2.asm:asm-util:${Versions.asm}"
        const val asmCommons = "org.ow2.asm:asm-commons:${Versions.asm}"
    }

    object WillFix {
        const val core = "wtf.s1.pudge:will-fix-core:${Versions.pudge}"
        const val standAlonePlugin = "wtf.s1.pudge:will-fix-plugin:${Versions.pudge}"
        const val byteX = "wtf.s1.pudge:will-fix-bytex:${Versions.pudge}"
    }

    object Stick {
        const val core = "wtf.s1.pudge:stick-core:${Versions.pudge}"
        const val byteX = "wtf.s1.pudge:stick-bytex:${Versions.pudge}"
    }

    object Hugo2 {
        const val core = "wtf.s1.pudge:hugo2-core:${Versions.pudge}"
        const val byteX = "wtf.s1.pudge:hugo2-bytex:${Versions.pudge}"
    }

    const val firebasePref = "com.google.firebase:firebase-perf:19.0.9"
}

object ClassPaths {
    const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val gradleApi = "com.android.tools.build:gradle-api:${Versions.gradlePlugin}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val vanniktechMavenPublishPlugin = "com.vanniktech:gradle-maven-publish-plugin:0.14.0"
    const val dokaa = "org.jetbrains.dokka:dokka-gradle-plugin:1.4.10.2"
    const val firebasePref = "com.google.firebase:perf-plugin:1.3.1"
}