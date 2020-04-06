package com.developgadget.adcolony;

import android.util.Log;

import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyReward;
import com.adcolony.sdk.AdColonyRewardListener;
import com.adcolony.sdk.AdColonyZone;

public class Listeners extends AdColonyInterstitialListener implements AdColonyRewardListener {


    @Override
    public void onRequestFilled(AdColonyInterstitial adColonyInterstitial){
        AdcolonyPlugin.Ad = adColonyInterstitial;
        Log.i("AdColony", "onRequestFilled");
        AdcolonyPlugin.getInstance().OnMethodCallHandler("onRequestFilled");
    }

    public void onRequestNotFilled(AdColonyZone adColonyInterstitial) {
        AdcolonyPlugin.Ad = null;
        Log.i("AdColony", "onRequestNotFilled");
        AdcolonyPlugin.getInstance().OnMethodCallHandler("onRequestNotFilled");
    }

    public void onOpened(AdColonyInterstitial ad) {
        Log.i("AdColony", "onOpened");
        AdcolonyPlugin.getInstance().OnMethodCallHandler("onOpened");
    }

    public void onClosed(AdColonyInterstitial ad) {
        AdcolonyPlugin.Ad = null;
        Log.i("AdColony", "onClosed");
        AdcolonyPlugin.getInstance().OnMethodCallHandler("onClosed");
    }

    public void onIAPEvent(AdColonyInterstitial ad, String product_id, int engagement_type) {
        Log.i("AdColony", "onIAPEvent");
        AdcolonyPlugin.getInstance().OnMethodCallHandler("onIAPEvent");
    }

    public void onExpiring(AdColonyInterstitial ad) {
        AdcolonyPlugin.Ad = null;
        Log.i("AdColony", "onExpiring");
        AdcolonyPlugin.getInstance().OnMethodCallHandler("onExpiring");
    }

    public void onLeftApplication(AdColonyInterstitial ad) {
        Log.i("AdColony", "onLeftApplication");
        AdcolonyPlugin.getInstance().OnMethodCallHandler("onLeftApplication");
    }

    public void onClicked(AdColonyInterstitial ad) {
        Log.i("AdColony", "onClicked");
        AdcolonyPlugin.getInstance().OnMethodCallHandler("onClicked");
    }

    @Override
    public void onReward(AdColonyReward adColonyReward) {
        AdcolonyPlugin.Ad = null;
        Log.i("AdColony", "onReward");
        AdcolonyPlugin.getInstance().OnMethodCallHandler("onReward");
    }

}
