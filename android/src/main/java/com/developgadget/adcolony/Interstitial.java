package com.developgadget.adcolony;

import android.util.Log;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;

public class Interstitial extends AdColonyInterstitialListener {

    private AdColonyInterstitial Ad;

    public void Request(String Id) {
        try {
            AdColony.requestInterstitial(Id, this);
        } catch (Exception e) {
            Log.e("AdColony", e.getMessage());
        }
    }

    public void Show() {
        if (this.Ad != null && !this.Ad.isExpired())
            this.Ad.show();
    }

    @Override
    public void onRequestFilled(AdColonyInterstitial adColonyInterstitial) {
        this.Ad = adColonyInterstitial;
        AdcolonyPlugin.getInstance().OnMethodCallHandler("onRequestFilled");
    }

    public void onRequestNotFilled(AdColonyInterstitial adColonyInterstitial) {
        AdcolonyPlugin.getInstance().OnMethodCallHandler("onRequestNotFilled");
    }
}
