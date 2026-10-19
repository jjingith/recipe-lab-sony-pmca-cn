@echo off
setlocal
cd /d %~dp0
REM ---- this checkout's toolchain (workspace-relative: ../../06-toolchain/pmca-toolchain) ----
set TC=%~dp0..\..\06-toolchain\pmca-toolchain
set JAVA_HOME=%TC%\jdk17
set ANDROID_SDK=%TC%\sdk
set ANDROID_NDK=%TC%\ndk
set BUILD_TOOLS=30.0.3
set PLATFORM_JAR=%TC%\sdk\platforms\android-28\android.jar
call build.cmd