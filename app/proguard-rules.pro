# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/marko/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# ==================================================================================================
# Commonly used configurations

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-allowaccessmodification
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-repackageclasses ''

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService


# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# Constructors of custom View classes
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
  public static <fields>;
}

-keepattributes *Annotation*

-keep public class * {
    public protected *;
}

# Keep setters in Views for animations
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# Support libraries
-dontwarn android.support.**

# Build configurations
-dontwarn **BuildConfig

# Optimize logging away from release builds
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** e(...);
    public static *** w(...);
    public static *** wtf(...);
    public static *** isLoggable(...);
    public static *** getStackTraceString(...);
}

# ==================================================================================================
# Configurations for Robopupu API classes

# Flow Framework
-keepclassmembers class * extends com.robopupu.api.feature.AbstractFeature {
    public <init>(java.lang.Class);
    public <init>(java.lang.Class, boolean);
}

-keepnames class * implements com.robopupu.api.feature.Feature
-keepnames interface * extends com.robopupu.api.feature.Feature

# ==================================================================================================
# Configurations for Gson

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

#-keepnames class com.google.gson.reflect.TypeToken

-keep class sun.misc.Unsafe { *; }

-dontwarn com.robopupu.compiler.**

# ==================================================================================================
# Configurations for libraries

-keep public class javax.net.ssl.**
-keepclassmembers public class javax.net.ssl.** {
  *;
}

# Apache HTTP Client
-keep public class org.apache.http.**
-keepclassmembers public class org.apache.http.** {
  *;
}


# Google Volley
-dontwarn com.android.volley.**


# OkHttp
-dontwarn okio.**
-dontwarn com.squareup.**
-dontwarn okhttp3.internal.**
