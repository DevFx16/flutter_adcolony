# Flutter AdColony

**Note: Currently only Android platform is supported.**

**Note: AndroidX is required.**

## Getting Started

### 1. Initialization

Call `Adcolony.init();` during app initialization.

```dart
AdColony.init(AdColonyOptions('#your_app_id', '0', this.zones));
```
```dart
class AdColonyOptions {
	final String id;
	final String gdpr;
	final List<String> zones;
	AdColonyOptions(this.id, this.gdpr, this.zones);
	Map<String, dynamic> toJson() =>
	{'Id': this.id, 'Gdpr': this.gdpr, 'Zones': this.zones};
}
```
[For more information GDPR](https://github.com/AdColony/AdColony-Android-SDK/wiki/GDPR)
### 2. Request Interstitial Ad and Rewarded Video Ad

```dart
	AdColony.request('#ad_zone', listener);
```
### 3. Show Ad in listener

```dart
listener(AdColonyAdListener event) {
	if (event == AdColonyAdListener.onRequestFilled)
		AdColony.show();
}
```

### 4. Show Ad Banner

```dart
BannerView((AdColonyAdListener event) =>  print(event), BannerSizes.leaderboard, '#ad_zone'),
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

```dart
enum AdColonyAdListener {
	onRequestFilled,
	onRequestNotFilled,
	onReward,
	onOpened,
	onClosed,
	onIAPEvent,
	onExpiring,
	onLeftApplication,
	onClicked
}
```

## Future Work
Implement for iOS platform.