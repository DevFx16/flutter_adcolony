import 'package:adcolony/adcolony.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

enum BannerSizes { banner, leaderboard, skyscraper, medium }
typedef void BannerCreatedCallback(BannerController controller);

class BannerView extends StatefulWidget {
  final AdColonyListener listener;
  final BannerSizes size;
  final String id;
  final BannerCreatedCallback onCreated;
  BannerView(this.listener, this.size, this.id, {Key key, this.onCreated}) : super(key: key);
  @override
  _BannerViewState createState() => _BannerViewState();
}

class _BannerViewState extends State<BannerView> {
  final Map<BannerSizes, BannerType> sizes = {
    BannerSizes.banner: BannerType(300, 50, 'BANNER'),
    BannerSizes.leaderboard: BannerType(320, 50, 'LEADERBOARD'),
    BannerSizes.medium: BannerType(300, 250, 'MEDIUM_RECTANGLE'),
    BannerSizes.skyscraper: BannerType(160, 600, 'SKYSCRAPER')
  };

  @override
  Widget build(BuildContext context) {
    return Container(
      width: this.sizes[this.widget.size].width,
      height: this.sizes[this.widget.size].height,
      child: AndroidView(
        viewType: '/Banner',
        key: UniqueKey(),
        creationParams: {'Size': this.sizes[this.widget.size].type, 'Id': this.widget.id},
        creationParamsCodec: StandardMessageCodec(),
        onPlatformViewCreated: this._onPlatformViewCreated,
      ),
    );
  }

  void _onPlatformViewCreated(int id) {
    BannerController controller = new BannerController._(id);
    controller._channel.setMethodCallHandler(
      (MethodCall call) async => this.handleMethod(call),
    );
    controller.loadAd();
    if (widget.onCreated == null) {
      return;
    }
    widget.onCreated(controller);
  }

  Future<void> handleMethod(MethodCall call) async {
    if (this.widget.listener != null)
      this.widget.listener(AdColony.adColonyAdListener[call.method]);
  }
}

class BannerType {
  final double width;
  final double height;
  final String type;

  BannerType(this.width, this.height, this.type);
}

class BannerController {
  BannerController._(int id) : _channel = new MethodChannel('Banner_$id');

  final MethodChannel _channel;

  Future<void> loadAd() async {
    return _channel.invokeMethod('loadAd');
  }
}
