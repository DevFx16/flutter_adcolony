import 'package:flutter/material.dart';
import 'package:adcolony/AdColony.dart';
import 'package:adcolony/AdColonyBanner.dart';
import 'package:flutter/rendering.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    AdColony.initialize(
        appId: 'app4f4659d279be4554ad', zoneId: ['vz9a841ab586ae4d72b9', 'vz4eacd40a32ad4fd69c', 'vz09f26f8ad3c340c484'], consent: true);
  }

  _requestInterstitial(String zoneId) {
    AdColony.requestInterstitial(
        zoneId: zoneId,
        listener: (AdColonyEvent event) {
          if (AdColonyEvent.onRequestFilled == event)
            AdColony.showAd();
          else if (AdColonyEvent.onRequestNotFilled == event)
            print('Error');
          else
            print('Points');
        });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('AdColony Example'),
        ),
        body: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Padding(
              padding: EdgeInsets.all(10.0),
              child: RaisedButton(
                onPressed: () => _requestInterstitial('vz9a841ab586ae4d72b9'),
                child: Text('Show Interstitial'),
              ),
            ),
            Padding(
              padding: EdgeInsets.all(10.0),
              child: RaisedButton(
                onPressed: () => _requestInterstitial('vz4eacd40a32ad4fd69c'),
                child: Text('Show Interstitial rewarded'),
              ),
            ),
            AdColonyBanner('vz09f26f8ad3c340c484', BannerSizes.BANNER, (BannerEvent event) {}),
            Padding(padding: EdgeInsets.only(top: 10.0)),
            AdColonyBanner('vz09f26f8ad3c340c484', BannerSizes.MEDIUM_RECTANGLE, (BannerEvent event) {}),
          ],
        ),
      ),
    );
  }
}
