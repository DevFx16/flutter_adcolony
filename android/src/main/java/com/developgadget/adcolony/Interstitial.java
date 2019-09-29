package com.developgadget.adcolony;

import android.util.Log;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyReward;
import com.adcolony.sdk.AdColonyRewardListener;
import com.adcolony.sdk.AdColonyZone;

import io.flutter.plugin.common.MethodChannel;

class Interstitial extends AdColonyInterstitialListener implements AdColonyRewardListener {

    private AdColonyInterstitial Ad;
    private final MethodChannel channel;

    Interstitial(MethodChannel channel) {
        this.channel = channel;
    }

    void Show() {
        if (this.Ad != null)
            Ad.show();
        else
            Log.d("AdColony", "Ad not request");

    }

    void Request(String ZoneId) {
        if (ZoneId != null && !ZoneId.isEmpty()) {
            if (this.Ad == null || this.Ad.isExpired()) {
                AdColony.requestInterstitial(ZoneId, this);
            }
        } else {
            Log.d("AdColony", "ZONE_ID IS EMPTY");
        }
    }

    @Override
    public void onRequestFilled(AdColonyInterstitial adColonyInterstitial) {
        this.Ad = adColonyInterstitial;
        this.channel.invokeMethod("onRequestFilled", null);
        Log.d("AdColony", "onRequestFilled");
    }

    @Override
    public void onRequestNotFilled(AdColonyZone zone) {
        this.channel.invokeMethod("onRequestNotFilled", null);
        Log.e("AdColony", "onRequestNotFilled");
    }

    @Override
    public void onReward(AdColonyReward adColonyReward) {
        this.channel.invokeMethod("onReward", null);
        Log.e("AdColony", "onReward");
    }
}