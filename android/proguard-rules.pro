-keepclassmembers class * { 
    @android.webkit.JavascriptInterface <methods>; 
}
# For removing warnings due to lack of Multi-Window support
-dontwarn android.app.Activity