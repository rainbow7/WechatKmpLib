import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    id("maven-publish")
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        framework {
            baseName = "wechat"
            isStatic = true
        }

        pod("WechatOpenSDK-XCFramework") {
            version = "2.0.4"
            headers = "WXApi.h"
        }

    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        androidMain.dependencies {
            api(libs.wechat.sdk.android)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "cn.kmplib.wechat"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

group = "cn.kmplib"
version = "0.0.1"

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "cn.kmplib"
            artifactId = "WechatSdk"
            version = "0.0.3"
            
            // 移除 from(components["java"])，因为这是 KMP 项目
            from(components["kotlin"])
            
            pom {
                name.set("WechatSdk")
                description.set("Wechat SDK KMP Library")
                url.set("https://github.com/rainbow7/WechatKMPLib")
            }
        }
    }
}