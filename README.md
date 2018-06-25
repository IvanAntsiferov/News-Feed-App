[![Build Status](https://travis-ci.org/IvanAntsiferov/News-Feed-App.svg?branch=master)](https://travis-ci.org/IvanAntsiferov/News-Feed-App)
[![codecov](https://codecov.io/gh/IvanAntsiferov/News-Feed-App/branch/master/graph/badge.svg)](https://codecov.io/gh/IvanAntsiferov/News-Feed-App)

# News Feed App
Simple Android app, displaying list of latest news from chosen news sources. Main features:

- 70 news sources in 9 categories
- Share an article function
- Support both portrait and landscape screen orientation
- Two pane mode for tablets

## Screenshots
![Screenshot](https://raw.githubusercontent.com/IvanAntsiferov/News-Feed-App/master/docs/images/Screenshot1.png)
![Screenshot](https://raw.githubusercontent.com/IvanAntsiferov/News-Feed-App/master/docs/images/Screenshot2.png)
![Screenshot](https://raw.githubusercontent.com/IvanAntsiferov/News-Feed-App/master/docs/images/Screenshot3.png)

## Technical Details
App built with MVI architecture.

Presentation layer is done a bit different from classic MVP. There is [Model](https://github.com/IvanAntsiferov/News-Feed-App/blob/master/app/src/main/java/com/voltek/newsfeed/presentation/base/BaseModel.kt) class instance in Presenter, that holds current View state. Presenter does not send any commands to view, instead of this, presenter change Model properties, and than View `render` changes. View input [events](https://github.com/IvanAntsiferov/News-Feed-App/blob/master/app/src/main/java/com/voltek/newsfeed/presentation/base/Event.kt) sent to presenter through `notify` method.

Domain layer works much more like MVP + Clean Architecture approach. There is a domain layer, that's represented by use cases and repositories, it works with data layer and mapping entities from `RAW` to `UI` versions. Domain layer knows nothing about Android SDK.

Data layer works directly with Android specific tools: network calls, DB queries and stuff like that.

## How To Build Project
This app uses [News API](https://newsapi.org/) to retrieve data. You must provide your own api key in order to build the app. Paste it to project [gradle.properties](https://raw.githubusercontent.com/IvanAntsiferov/News-Feed-App/master/gradle.properties) in variable named ```ApiKey```.

## Libraries

* [Moxy](https://github.com/Arello-Mobile/Moxy)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [RxBinding](https://github.com/JakeWharton/RxBinding)
* [RxKotlin](https://github.com/ReactiveX/RxKotlin)
* [Realm](https://github.com/realm/realm-java)
* [Kotlin Realm Extensions](https://github.com/vicpinm/Kotlin-Realm-Extensions)
* [Retrofit](https://github.com/square/retrofit)
* [Dagger 2](https://github.com/google/dagger)
* [Glide](https://github.com/bumptech/glide)
* [Hawk](https://github.com/orhanobut/hawk)
* [Parceler](https://github.com/johncarl81/parceler)
* [Calligraphy](https://github.com/chrisjenx/Calligraphy)
* [Timber](https://github.com/JakeWharton/timber)
* [RecyclerView Animators](https://github.com/wasabeef/recyclerview-animators)
* [Mockito](https://github.com/mockito/mockito)
* [Mockito-Kotlin](https://github.com/nhaarman/mockito-kotlin)

### License

```
Copyright 2017-2018 Ivan Antsiferov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
