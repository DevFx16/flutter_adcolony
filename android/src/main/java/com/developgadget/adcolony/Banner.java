package com.developgadget.adcolony;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdSize;
import com.adcolony.sdk.AdColonyAdView;
import com.adcolony.sdk.AdColonyAdViewListener;
import com.adcolony.sdk.AdColonyZone;

import java.util.HashMap;

import io.flutter.Log;
import io.flutter.plugin.platform.PlatformView;

@SuppressWarnings("SuspiciousMethodCalls")
public class Banner extends AdColonyAdViewListener implements PlatformView {

    private LinearLayout layout;
    private final HashMap<String, AdColonyAdSize> sizes = new HashMap<String, AdColonyAdSize>() {
        {
            put("BANNER", AdColonyAdSize.BANNER);
            put("LEADERBOARD", AdColonyAdSize.LEADERBOARD);
            put("MEDIUM_RECTANGLE", AdColonyAdSize.MEDIUM_RECTANGLE);
            put("SKYSCRAPER", AdColonyAdSize.SKYSCRAPER);
        }
    };

    Banner(HashMap args) {
        try {
            String id = (String) args.get("Id");
            AdColonyAdSize size = this.sizes.get(args.get("Size"));
            assert id != null;
            assert size != null;
            this.layout = new LinearLayout(AdcolonyPlugin.ActivityInstance);
            AdColony.requestAdView(id, this, size);
        } catch (Exception e) {
            Log.e("AdColony", e.toString());
        }
    }

    @Override
    public View getView() {
            return this.layout;
    }

    @Override
    public void dispose() {
        this.layout.removeAllViews();
    }

    @Override
    public void onRequestFilled(AdColonyAdView adColonyAdView) {
        this.layout.addView(adColonyAdView, adColonyAdView.getLayoutParams());
    }

    public void onRequestNotFilled(AdColonyZone adColonyInterstitial) {
        try {
            AdcolonyPlugin.getInstance().OnMethodCallHandler("onRequestNotFilled");
            Log.e("AdColony", "onRequestNotFilled");
        } catch (Exception e) {
            Log.e("AdColony", e.toString());
        }
    }
}
