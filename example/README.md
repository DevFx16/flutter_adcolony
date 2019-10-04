# Flutter AdColony

**Note: Currently only Android platform is supported.**

**Note: AndroidX is required.**

## Getting Started

### 1. Initialization

Call `Adcolony.initialize();` during app initialization.

```dart
AdColony.initialize(appid: 'your_app_id', zoneid: ['your_zones_ids'], consent: true or flase);
```

### 2. Request Interstitial Ad and Rewarded Video Ad

```dart
AdColony.requestInterstitial(zoneId: zoneId, listener: (AdColonyEvent event) {});
```
### 3. Show Ad in listener

```dart
if (AdColonyEvent.onRequestFilled == event)
    AdColony.showAd();
```

### 4. Show Ad Banner

```dart
AdColonyBanner('vz09f26f8ad3c340c484', BannerSizes.BANNER, (BannerEvent event) {}),
```

### 5. ProGuard Configuration

```
# For communication with AdColony's WebView
-keepclassmembers class * { 
    @android.webkit.JavascriptInterface <methods>; 
}
```
```
# For removing warnings due to lack of Multi-Window support
-dontwarn android.app.Activity
```

## Events

| Event              | Description                                                                        |
|--------------------|------------------------------------------------------------------------------------|
| OnRequestFilled    | Called in response to an ad request when the request has been successfully filled. |
| OnRequestNotFilled | Called in response to an ad request when the request failed to fill.               |
| OnReward           | Called when the rewarded video ends successfully.                                  |


## Future Work
Implement for iOS platform.
