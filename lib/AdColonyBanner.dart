import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

enum BannerSizes { BANNER, LEADERBOARD, MEDIUM_RECTANGLE, SKYSCRAPER }
enum BannerEvent { onRequestFilled, onRequestNotFilled }

typedef void BannerListener(BannerEvent event);

class AdColonyBanner extends StatefulWidget {
  final BannerSizes size;
  final String zoneId;
  final BannerListener listener;
  AdColonyBanner(this.zoneId, this.size, this.listener);

  @override
  _AdColonyBannerState createState() => _AdColonyBannerState();
}

class _AdColonyBannerState extends State<AdColonyBanner> {
  final _sizes = {
    BannerSizes.BANNER : 'BANNER',
    BannerSizes.LEADERBOARD: 'LEADERBOARD',
    BannerSizes.MEDIUM_RECTANGLE: 'MEDIUM_RECTANGLE',
    BannerSizes.SKYSCRAPER: 'SKYSCRAPER',
  };
  final _sizesBanner = {
    BannerSizes.BANNER : [50, 320],
    BannerSizes.LEADERBOARD: [90, double.infinity],
    BannerSizes.MEDIUM_RECTANGLE: [250, 300],
    BannerSizes.SKYSCRAPER: [600, 160],
  };
  final Map<String, BannerEvent> _methodEvent = {
    'onRequestFilled': BannerEvent.onRequestFilled,
    'onRequestNotFilled': BannerEvent.onRequestNotFilled,
  };
  @override
  Widget build(BuildContext context) {
    if (defaultTargetPlatform == TargetPlatform.android) {
      return SizedBox(
        width: this._sizesBanner[this.widget.size][1].toDouble(),
        height: this._sizesBanner[this.widget.size][0].toDouble(),
        child: AndroidView(
          viewType: '/BannerAd',
          key: UniqueKey(),
          creationParamsCodec: StandardMessageCodec(),
          onPlatformViewCreated: (int i){
            final channel = MethodChannel('AdColony/Banner$i');
            channel.setMethodCallHandler(this._handleMethod);
          },
          creationParams: <String, String>{
            'ZoneId': this.widget.zoneId,
            'Size': this._sizes[this.widget.size]
          },
        ),
      );
    }else {
      return Container(child: Text('this plugin only supported for android'));
    }
  }
   Future<dynamic> _handleMethod(MethodCall call) async {
    final BannerEvent mobileAdEvent = this._methodEvent[call.method];
    this.widget.listener(mobileAdEvent);
    return Future<dynamic>.value(null);
  }
}
