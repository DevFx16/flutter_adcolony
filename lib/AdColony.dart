import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

enum AdColonyEvent { onRequestFilled, onRequestNotFilled, onReward }

typedef void AdColonyListener(AdColonyEvent event);

class AdColony {
  static const MethodChannel _channel = const MethodChannel('AdColony');
  static const MethodChannel _channelInter = const MethodChannel('AdColony/Interstitial');
  static Map<String, AdColonyEvent> _methodEvent = {
    'onRequestFilled': AdColonyEvent.onRequestFilled,
    'onRequestNotFilled': AdColonyEvent.onRequestNotFilled,
    'onReward': AdColonyEvent.onReward
  };

  static Future<dynamic> initialize({@required String appId, @required List zoneId, @required bool consent}) async {
    assert(appId != null && appId.isNotEmpty);
    assert(zoneId != null && zoneId.length != 0);
    assert(consent != null);
    return await _channel.invokeMethod('initialize', <String, dynamic>{'APP_ID': appId, 'ZONE_IDS': zoneId, 'GDPR': consent ? '1' : '0'});
  }

  static Future<dynamic> requestInterstitial({@required String zoneId, @required AdColonyListener listener}) async {
    assert(zoneId != null && zoneId.isNotEmpty);
    assert(listener != null);
    _channelInter.setMethodCallHandler((MethodCall call) async => _handleMethod(call, listener));
    await _channelInter.invokeMethod('loadInterstitial', <String, String>{'ZONE_ID': zoneId});
    return Future<dynamic>.value(null);
  }

  static Future<dynamic> showAd() async {
    await _channelInter.invokeMethod('showAd');
    return Future<dynamic>.value(null);
  }

  static Future<dynamic> _handleMethod(MethodCall call, AdColonyListener listener) async {
    print(call.method);
    final AdColonyEvent mobileAdEvent = _methodEvent[call.method];
    listener(mobileAdEvent);
    return Future<dynamic>.value(null);
  }
}
