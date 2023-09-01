<img src="https://capsule-render.vercel.app/api?type=Waving&color=E4405F&height=150&section=header&text=TagWorks-SDK-Android&fontSize=45" />

![Generic badge](https://img.shields.io/badge/version-1.0.1-green.svg)
![Generic badge](https://img.shields.io/badge/license-ApacheLicense2.0-blue.svg)
![Generic badge](https://img.shields.io/badge/Platform-Android-red.svg)
![Generic badge](https://img.shields.io/badge/support-java-yellow.svg)
![Generic badge](https://img.shields.io/badge/support-kotlin-yellow.svg)

## Requirements

* 최소 Android Sdk 버전 : 19
* 최소 Jdk 버전 : 1.8

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
  
## Getting started
### 1. TagWorks 인스턴스 초기화

* TagWorks SDK 인스턴스를 초기화 하기 위해서는 `Application Context` 객체 전달이 필요합니다.
* TagWorks SDK 인스턴스는 한번만 초기화하면 싱글톤 인스턴스로 유지됩니다.

```java
// TagWorksConfig Builder 클래스를 이용한 초기화
TagWorksConfig config = new TagWorksConfig.Builder()
    .setBaseUrl("<https://host:port>")
    .setSiteId("61,YbIxGr9e")
    .build();

// initializeSdk 메서드 호출
TagWorks.initializeSdk(appContext, config);
```

```java
// initializeSdk 메서드 호출로 즉시 초기화
TagWorks.initializeSdk(appContext, "<https://host:port>", "61,YbIxGr9e");
```


## Author

hanyj96@obzen.com

## License

Apache License 2.0
