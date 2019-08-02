# Flutter AdColony

**Note: Currently only Android platform is supported.**

## Getting Started

### 1. Initialization

Call `Adcolony.initialize();` during app initialization.

```dart
Adcolony.initialize(appid: 'your_app_id', zoneid: ['your_zones_ids']);
```

### 2. Request Interstitial Ad and Rewarded Video Ad

```dart
Adcolony.requestInterstitial(zoneid: 'your_zone_interstitial_id');
```
### 3. Show Ad

```dart
Adcolony.onRequestFilled = () {
    Adcolony.showAd();
};
```

### 4. ProGuard Configuration

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
| OnOpened           | Called when the interstitial ad opens.                                             |
| OnExpiring         | Called when an intersitial expires and is no longer valid for playback.            |
| OnReward           | called when the rewarded video ends successfully.                                  |


## Future Work
Implement for iOS platform.
