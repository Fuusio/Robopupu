# Robopupu

<img src="https://github.com/Fuusio/Robopupu/blob/gh-pages/images/robopupu_header_image.png" alt="Robopupu mascot"/>

An Android library that provides a set of APIs for architecting and developing Android applications:

* **Robopupu.MVP**: A Model-View-Presenter (MVP) API.
* **Robopupu.Dependency**: A dependency injection/pulling API.
* **Robopupu.FSM**: A simple library for implementing hierachical Finite State Machines that support most of the UML state diagram features, including: entry points, choice points, and history points.
* **Robopupu.Plugin**: A plugin framework that supports dependency injection (DI) and allows decouples communication between components without requiring the components to explicitly register with one another.
* **Robopupu.Feature**: A flow controller type of architectural design pattern for using components that encapsulate navigation and configuration logic for application features.
* **Robopupu.FRP**: A simple to use API for Functional Reactive Programming (FRP). API is based on constructing a graph of functional nodes to perform a specified computation. Use of Retrolambda for lambda expressions recommend.
* **Robopupu.Util**: A collection of utility classes.

Check out the [Robopupu project website](http://robopupu.com/) for further information.

## Robopupu Compiler
To minimize writing of any boiler plate code, **Robopupu** utilises declarative annotations and a set of API specific annotation processors which generate code for using the APIs. The source codes for Robopupu Compiler and the instructions for getting started are available in Github : [https://github.com/Fuusio/Robopupu-Compiler](https://github.com/Fuusio/Robopupu-Compiler).

## Documentation

* [Robopupu developer documentation](http://robopupu.com/)
* [Javadoc API documentation](http://fuusio.github.io/Robopupu/javadocs/)

## Sample App 
The sample application build from source codes the [app module](https://github.com/Fuusio/Robopupu/tree/master/app) is also available in [Google Play Store](https://play.google.com/store/apps/details?id=com.robopupu).

## Installation
Use the following dependencies into your application module's ```build.gradle```file:

```groovy
dependencies {
	compile 'com.robopupu:robopupu:0.4.7'
	apt 'com.robopupu:robopupu-compiler:0.4.7'	
	apt 'com.squareup:javapoet:1.6.1' 
}
```
In addition, since the Robopupu Compiler utilises annotation processing we need to use [android-apt plugin](https://bitbucket.org/hvisser/android-apt). For this reason, you need to declare the plugin in your into your application module's ```build.gradle```file:

```groovy
apply plugin: 'com.android.application'
apply plugin: 'com.neenbedankt.android-apt'
```
Finally, you need to declare the following ```classpath``` for the android-apt plugin in your project's ```build.gradle```file:

```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.0.0'
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
    }
}
```

## Changelog

### 0.4.7
* Improvements and API changes in Model classes in MVP API.
 
### 0.4.6
* Bux fixes in DependencyScope.

### 0.4.5
* Streamlined PluginBus class: Now all methods are static.
* Extended Dependency class with new getAll(...) methods that can be used to request all dependencies of specified type.
* Updated Javadocs

### 0.4.4
* Fixed a bug in StateEngine class.
* Improvements on Feature API
* Elaborated Javadoc comments
* Updated some libraries versions and took Google Volley 1.0.0 via JCenter into use (in demo app).

### 0.4.3
* A fix for [issue #8](https://github.com/Fuusio/Robopupu/issues/8)

### 0.4.2
* Added support for using non-compatibility Activities and Fragments.
* Improvements for Feature API, e.g., support for removing Views. 
* Minor fixes.

### 0.4.1
* Added basic threading support to [Functional Reactive Programming (FRP) API](https://robopupu.com/robopupu-frp/)

### 0.4.0
* Fix issue with resolving Presenter in View after a configuration change
* Other minor fixes
* Added new Robopupu API: [Functional Reactive Programming (FRP) API](https://robopupu.com/robopupu-frp/)
* Added new Robopupu API : [Light-weight REST API for specifying Google Volley requests](https://robopupu.com/robopupu-rest-api/)

### 0.3.1
* Initial open source release

## License
```
Copyright (C) 2008-2016 Marko Salmela

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at;

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
