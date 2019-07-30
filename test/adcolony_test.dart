import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:adcolony/adcolony.dart';

void main() {
  const MethodChannel channel = MethodChannel('adcolony');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('initialize', () async {
    expect(await Adcolony.initialize(appid: 'fgdg', zoneid: ['sgsgds']), 'true');
  });
}
