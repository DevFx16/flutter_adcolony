package com.developgadget.adcolony;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdSize;
import com.adcolony.sdk.AdColonyAdView;
import com.adcolony.sdk.AdColonyAdViewListener;
import com.adcolony.sdk.AdColonyZone;

import java.util.HashMap;

import io.flutter.plugin.platform.PlatformView;

public class BannerView extends AdColonyAdViewListener implements PlatformView {

    private AdColonyAdView Banner;

    BannerView(HashMap args) {
        String ZoneId = (String) args.get("ZoneId");
        String AdSize = (String) args.get("Size");
        assert ZoneId != null;
        assert AdSize != null;
        HashMap<String, AdColonyAdSize> sizes = new HashMap<String, AdColonyAdSize>() {
            {
                put("BANNER", AdColonyAdSize.BANNER);
                put("LEADERBOARD", AdColonyAdSize.LEADERBOARD);
                put("MEDIUM_RECTANGLE", AdColonyAdSize.MEDIUM_RECTANGLE);
                put("SKYSCRAPER", AdColonyAdSize.SKYSCRAPER);
            }
        };
        AdColonyAdSize Size = sizes.get(AdSize);
        assert Size != null;
        AdColony.requestAdView(ZoneId, this, Size);
    }

    @Override
    public void onRequestFilled(AdColonyAdView adColonyAdView) {
        Log.d("AdColony Banner", "onRequestFilled");
        this.Banner = adColonyAdView;
    }

    @Override
    public void onRequestNotFilled(AdColonyZone zone) {
        Log.e("AdColony Banner", "onRequestNotFilled");
    }

    @Override
    public View getView() {
        return Banner;
    }

    @Override
    public void dispose() {
        if (this.Banner != null)
            this.Banner.destroy();
    }
}
