import 'package:flutter/material.dart';
import 'package:adcolony/adcolony.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    Adcolony.initialize(
        appid: 'app4f4659d279be4554ad', zoneid: ['vz9a841ab586ae4d72b9']);
    Adcolony.onRequestFilled = () {
      Adcolony.showAd();
    };
    Adcolony.requestInterstitial(zoneid: 'vz9a841ab586ae4d72b9');
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: $_platformVersion\n'),
        ),
      ),
    );
  }
}