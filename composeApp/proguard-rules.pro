# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn android.media.LoudnessCodecController$OnLoudnessCodecUpdateListener
-dontwarn android.media.LoudnessCodecController
-keep class com.adapty.** { *; }
-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception
-keep class com.google.firebase.crashlytics.** { *; }
-keep class com.google.firebase.components.** { *; }
-keep class com.google.firebase.inject.** { *; }
-keep class com.google.firebase.platforminfo.** { *; }
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.crashlytics.**
-keepclassmembers class * {
    @com.google.firebase.crashlytics.internal.model.CrashlyticsReport$** <fields>;
}

-keepattributes SourceFile,LineNumberTable
-keep class com.google.protobuf.** { *; }
-keep class androidx.datastore.** { *; }
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite {
    <fields>;
    <methods>;
}
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite$Builder {
    <fields>;
    <methods>;
}