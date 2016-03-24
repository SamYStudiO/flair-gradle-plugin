# Flair Gradle Plugin [![Build Status](https://travis-ci.org/SamYStudiO/flair-gradle-plugin.svg?branch=master)](https://travis-ci.org/SamYStudiO/flair-gradle-plugin) [![Bintray](https://img.shields.io/bintray/v/samystudio/maven/flair-gradle-plugin.svg)](https://bintray.com/samystudio/maven/flair-gradle-plugin)
Flair is a [gradle](http://gradle.org/) plugin that helps you build **Adobe AIR projects** (ios, android and desktop). Flair comes up with all tasks you may already use with Ant (compile, install, launch, etc...) but also with all files you need to start working on your projects. Last but not least Flair greatly **improves resource management** using an [Android like](http://developer.android.com/guide/topics/resources/providing-resources.html) approach.

#####FEATURES :
* Comes up with latest stable version of **[Starling](https://github.com/Gamua/Starling-Framework)** and **[Feathers](https://github.com/BowlerHatLLC/feathers)** (as actionscript libraries).

___
######WARNING
Actually comes up with beta Starling/Feathers libraries (Starling 2.0 and Feathers 3.0 downloaded from Github on 2016/03/23).You may obviously update these libraries if you need to.

___
* Almost transparent Flair actionscript library to **manage your resources**.
* Once you're set up you'll be able to compile/test a **template project**.
* **Variant builds** (build type and product flavor as Android).
* **[TexturePacker](https://www.codeandweb.com/texturepacker) plugin** to auto generate your atlases when compiling.
* **[IntelliJ IDEA](https://www.jetbrains.com/idea/) plugin** to configure project IDEA files and allow using standard run/debug configurations from IDEA.
* **[FDT](http://fdt.powerflasher.com/) plugin** to configure project FDT files and allow using standard run/debug configurations from FDT.
* And more ([**Flair logger**](https://github.com/SamYStudiO/flair-logger), **asdoc** generation , **R class** auto generated resource class as Android, ...).

===
####QUICK START
Create a gradle project and set up your `build.gradle` file (located at your project root) with the following:
```
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath "com.github.samystudio.flair:flair-gradle-plugin:0.7.0"
  }
}

apply plugin: "flair.ios" // only if your project need to target ios devices
apply plugin: "flair.android" // only if your project need to target android devices
apply plugin: "flair.desktop" // only if your project need to target desktop
apply plugin: "flair.texturepacker" // only if you want to use TexturePacker with your project
apply plugin: "flair.idea" // only if you are using IntelliJ IDEA
apply plugin: "flair.fdt" // only if you are using FDT

flair{
  packageName "com.hello.world" // set your sources root package here (this is required to initialize flair plugin)
}
```
To initialize plugin you'll need as well to set a **valid AIR SDK path** in **local.properties** file from your project root (file should be added automatically when `build.gradle` and project tree are refreshed), after you set your sdk refresh your `gradle.build` and project tree again, then your project structure should be set under `app` folder.

===
####DOCUMENTATION
Check [wiki](https://github.com/SamYStudiO/flair-gradle-plugin/wiki) for more documentation.

===
[Follow me on Twitter](https://twitter.com/SamYStudiO)

