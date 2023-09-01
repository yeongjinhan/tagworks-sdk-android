<img src="https://capsule-render.vercel.app/api?type=Waving&color=E4405F&height=150&section=header&text=TagWorks-SDK-Android&fontSize=45" />

![Generic badge](https://img.shields.io/badge/version-1.0.1-green.svg)
![Generic badge](https://img.shields.io/badge/license-ApacheLicense2.0-blue.svg)
![Generic badge](https://img.shields.io/badge/Platform-Android-red.svg)

## Requirements

* 최소 SDK 버전 : 19
* 최소 JDK 버전 : jdk1.8

## Installation

* Project 레벨 `settings.gradle` 에서 `maven { url 'https://jitpack.io' }`  및 ` mavenCentral()` 추가

  ```kotlin
  pluginManagement {
      repositories {
          google()
          mavenCentral()
          gradlePluginPortal()
      }
  }
  
  dependencyResolutionManagement {
      repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
      repositories {
          google()
          mavenCentral()
          maven { url 'https://jitpack.io' }
      }
  }
  ```

  

* Module 레벨 `budild.gralde` 추가

  ```kotlin
  dependencies {
    implementation 'com.obzen.support.Obzen:TagWorks-SDK-Android:<latest-version>'
  }
  ```

  

## Author

hanyj96@obzen.com

## License

Apache License 2.0
