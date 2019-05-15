# android-project-template
An Android project template follows clean architecture guidelines and includes all usualy used dependencies

## Idea
The idea of this repository if to give a backbone of any future Android project to those who are starting a new project. 
It is pretty much ready-to-work-on codebase and you can simply fork it and start to work on your project.

However, there are certain steps that you go through before starting with development:

1. You should change *applicationId* under *defaultConfig* in **app/build.gradle** file.
2. Package structure should be changed in order to match your new **applicationId**.
3. You should change default keystore that is used for debug signing config in *app* module. This is useful when you're developing a project where, for example, a Google API is used. Instead of passing SHA-1 of default *debug.keystore* for each and one of the devlopers working on the project, it's easier to configure the app to be signed by custom debug keystore.

## Language support
This project setup supports both Java and Kotlin. It's planned to crate also Java-only project template for developers who do not need Kotlin in their apps.

## Future work
The intention of this repository is to be regularly updated with newer version of dependencies when they are published and in some cases to introduce new dependency to modules if they are worth adding.

## Readings
https://developer.android.com/jetpack/androidx/migrate