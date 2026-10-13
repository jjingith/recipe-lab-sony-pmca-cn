@echo off
setlocal
cd /d %~dp0
REM ---- this checkout's toolchain ----
set JAVA_HOME=E:\pmca-toolchain\jdk17
set ANDROID_SDK=E:\pmca-toolchain\sdk
set ANDROID_NDK=E:\pmca-toolchain\ndk
set BUILD_TOOLS=30.0.3
set PLATFORM_JAR=E:\pmca-toolchain\sdk\platforms\android-28\android.jar
call build.cmd