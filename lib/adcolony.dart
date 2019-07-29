import 'dart:async';

import 'package:flutter/services.dart';

class Adcolony {
  static const MethodChannel _channel =
      const MethodChannel('adcolony');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
