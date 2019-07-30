import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class Adcolony {
  static const MethodChannel _channel = const MethodChannel('adcolony');

  static Future<String> initialize({@required String appid, @required List zoneid}) async {
    assert(appid != null && appid.isNotEmpty);
    assert(zoneid != null && zoneid.length != 0);
    return await _channel.invokeMethod(
        'initialize', <String, dynamic>{'APP_ID': appid, 'ZONE_IDS': zoneid});
  }
}
