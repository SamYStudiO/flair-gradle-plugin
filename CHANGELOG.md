# Flair Change Logs

### 0.8.1 | 2017-02-17
* Fixed ANEs containing multiple extension.xml generate multiple extensions node from app descriptor with same id.

### 0.8.0 | 2016-10-29
* Added better error information when using TexturePacker and its command line tool is missing
* Added appVersionLabel flair property and renamed appVersion to appVersionNumber to be able to set both versionLabel and versionNumber from app descriptor xml
* Changed autoGenerateVariantDirectories flair property default value to false
* Don't display icons/signing/splash_screens/atlases folders as excluded folder anymore (from IntelliJ)
* [Scaffold] Added Starling skipUnchangedFrames to true by default from AMain class
* Updated Starling library to 2.1.0 (as SWC for now on)
* Updated Feathers library to 3.1.0 (as SWC for now on)

### 0.7.3 | 2016-09-18
* Updated Starling library to 2.0.1
* Updated Feathers library to 3.0.4
* Typos

### 0.7.2 | 2016-04-19
* Updated Feathers library to early 3.0 beta

### 0.7.1 | 2016-04-19
* Updated Starling library to 2.0 final
* Updated Feathers library to 3.0 beta

### 0.7.0 | 2016-03-23
* Added flair.fdt plugin to support FDT IDE
* Added tasks descriptions
* Added assembleAll task execution when running gradle without task request
* Added Android ability to load additional splash screens matching actual resolution
* Fixed potential error when generating structures with multiple projects
* Fixed variants directories default source paths
* Optimized tasks execution (now generate/update structures only when gradle is executed without a task request)
* Improved error messages shown when missing AIR SDK path or packageName property
* Updated Starling/Feathers
* Other minor improvements and fixes

### 0.6.3 | 2016-02-23
* Initial public beta

