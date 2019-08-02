import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

typedef void OnRequestFilled();
typedef void OnRequestNotFilled();
typedef void OnOpened();
typedef void OnExpiring();
typedef void OnReward();

class Adcolony {
  static const MethodChannel _channel = const MethodChannel('adcolony');
  static OnRequestFilled onRequestFilled;
  static OnRequestNotFilled onRequestNotFilled;
  static OnOpened onOpened;
  static OnExpiring onExpiring;
  static OnReward onReward;

  static Future<dynamic> initialize(
      {@required String appid, @required List zoneid}) async {
    _channel.setMethodCallHandler(_handleMethod);
    assert(appid != null && appid.isNotEmpty);
    assert(zoneid != null && zoneid.length != 0);
    return await _channel.invokeMethod(
        'initialize', <String, dynamic>{'APP_ID': appid, 'ZONE_IDS': zoneid});
  }

  static Future<dynamic> requestInterstitial({@required String zoneid}) async {
    await _channel
        .invokeMethod('loadInterstitial', <String, String>{'ZONE_ID': zoneid});
    return Future<dynamic>.value(null);
  }

  static Future<dynamic> requestRewarded({@required String zoneid}) async {
    await _channel
        .invokeMethod('loadRewarded', <String, String>{'ZONE_ID': zoneid});
    return Future<dynamic>.value(null);
  }

  static Future<dynamic> showAd() async {
    await _channel.invokeMethod('showAd');
    return Future<dynamic>.value(null);
  }

  static Future<dynamic> _handleMethod(MethodCall call) {
    final String method = call.method;
    if (method == 'OnRequestFilled') {
      if (onRequestFilled != null) onRequestFilled();
    } else if (method == 'OnRequestNotFilled') {
      if (onRequestNotFilled != null) onRequestNotFilled();
    } else if (method == 'onOpened') {
      if (onOpened != null) onOpened();
    } else if (method == 'OnReward') {
      if (onReward != null) onReward();
    } else if (method == 'OnExpiring') {
      if (onExpiring != null) onExpiring();
    }
    return Future<dynamic>.value(null);
  }
}