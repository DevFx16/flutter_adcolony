package com.developgadget.adcolony;

import android.util.Log;
import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyReward;
import com.adcolony.sdk.AdColonyRewardListener;
import com.adcolony.sdk.AdColonyZone;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

class Interstitial extends AdColonyInterstitialListener implements AdColonyRewardListener, MethodChannel.MethodCallHandler {

    private AdColonyInterstitial Ad;
    private final MethodChannel channel;
    private final PluginRegistry.Registrar registrar;

    Interstitial(PluginRegistry.Registrar registrar, MethodChannel channel) {
        this.registrar = registrar;
        this.channel = channel;
    }

    private void Show() {
        if (this.Ad != null)
            Ad.show();
        else
            Log.d("AdColony", "Ad not request");
    }

    private void Request(String ZoneId) {
        if (ZoneId != null && !ZoneId.isEmpty()) {
            if (this.Ad == null || this.Ad.isExpired()) {
                AdColony.setRewardListener(this);
                AdColony.requestInterstitial(ZoneId, this);
            }
        } else {
            Log.d("AdColony", "ZONE_ID IS EMPTY");
        }
    }

    @Override
    public void onRequestFilled(AdColonyInterstitial adColonyInterstitial) {
        this.Ad = adColonyInterstitial;
        registrar.activity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Interstitial.this.channel.invokeMethod("onRequestFilled", null);
            }
        });
        Log.d("AdColony", "onRequestFilled");
    }

    @Override
    public void onRequestNotFilled(AdColonyZone zone) {
        registrar.activity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Interstitial.this.channel.invokeMethod("onRequestNotFilled", null);
            }
        });
        Log.e("AdColony", "onRequestNotFilled");
    }

    @Override
    public void onReward(AdColonyReward adColonyReward) {
        this.channel.invokeMethod("onReward", null);
        Log.e("AdColony", "onReward");
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        switch (methodCall.method){
            case "loadInterstitial":
                this.Request((String) methodCall.argument("ZONE_ID"));
                result.success(Boolean.TRUE);
                break;
            case "showAd":
                this.Show();
                result.success(Boolean.TRUE);
                break;
        }
    }
}