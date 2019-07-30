package com.developgadget.adcolony;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import com.adcolony.sdk.*;

/** AdcolonyPlugin */
public class AdcolonyPlugin implements MethodCallHandler {
  private String APP_ID = null;
  private String[] ZONE_IDS = null;
  private final Registrar registrar;
  private final MethodChannel channel;
  private AdColonyInterstitial AdInterstitial;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "adcolony");
    channel.setMethodCallHandler(new AdcolonyPlugin(registrar, channel));
  }

  private AdcolonyPlugin(Registrar registrar, MethodChannel channel) {
    this.registrar = registrar;
    this.channel = channel;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    switch (call.method) {
    case "initialize":
      callInitialize(call, result);
      break;
    case "loadInterstitial":
      callLoadInterstitial(call, result);
      break;
    default:
      result.notImplemented();
      break;
    }
    return;
  }

  private void callInitialize(MethodCall call, Result result) {
    this.APP_ID = call.argument("APP_ID");
    this.ZONE_IDS = (String[]) call.argument("ZONE_IDS");
    if (this.APP_ID == null || this.APP_ID.isEmpty() || this.ZONE_IDS == null || this.ZONE_IDS.length == 0) {
      result.error("NO APP_ID or ZONE_IDS", "a null or empty APP_ID or ZONE_IDS was provided", null);
      return;
    }
    AdColony.configure(registrar.context(), this.APP_ID, this.ZONE_IDS);
    result.success(Boolean.TRUE);
  }

  private void callLoadInterstitial(MethodCall call, Result result) {
    String ZoneId = call.argument("ZONE_ID");
    if (this.AdInterstitial == null || this.AdInterstitial.isExpired()) {
      AdColony.requestInterstitial(ZoneId, new AdColonyInterstitialListener() {
        @Override
        public void onRequestFilled(AdColonyInterstitial ad) {
          InterstitialActivity.this.AdInterstitial = ad;
          this.channel.invokeMethod("onRequestFilled");
          Log.d(TAG, "onRequestFilled");
        }

        @Override
        public void onRequestNotFilled(AdColonyZone zone) {
          this.channel.invokeMethod("onRequestNotFilled");
          Log.d(TAG, "onRequestNotFilled");
        }

        @Override
        public void onOpened(AdColonyInterstitial ad) {
          this.channel.invokeMethod("onOpened");
          Log.d(TAG, "onOpened");
        }

        @Override
        public void onExpiring(AdColonyInterstitial ad) {
          this.channel.invokeMethod("onExpiring");
          Log.d(TAG, "onExpiring");
        }
      }, adOptions);
    }
    result.success(Boolean.TRUE);
  }

}
