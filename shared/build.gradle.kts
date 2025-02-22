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
    namespace = "com.youdao.kmp"
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
            artifactId = "WechatSdk"
            from(components["release"])  // 添加这行
            
            // 可选：添加更多 POM 信息
            pom {
                name.set("WechatSdk")
                description.set("Wechat SDK KMP Library")
                // 如果需要发布到远程仓库，可以添加以下配置
                // repositories {
                //     maven {
                //         url = uri("你的远程仓库地址")
                //         credentials {
                //             username = "你的用户名"
                //             password = "你的密码"
                //         }
                //     }
                // }
            }
        }
    }
}