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

    private FrameLayout layout;
    private AdColonyAdView Ad;

    Banner(HashMap args) {
        try {
            String id = (String) args.get("Id");
            HashMap<String, AdColonyAdSize> sizes = new HashMap<String, AdColonyAdSize>() {
                {
                    put("BANNER", AdColonyAdSize.BANNER);
                    put("LEADERBOARD", AdColonyAdSize.LEADERBOARD);
                    put("MEDIUM_RECTANGLE", AdColonyAdSize.MEDIUM_RECTANGLE);
                    put("SKYSCRAPER", AdColonyAdSize.SKYSCRAPER);
                }
            };
            AdColonyAdSize size = sizes.get(args.get("Size"));
            this.layout = new FrameLayout(AdcolonyPlugin.ActivityInstance);
            this.layout.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            assert id != null;
            assert size != null;
            AdColony.requestAdView(id, this, size);
        } catch (Exception e) {
            Log.e("AdColony", e.toString());
        }
    }

    @Override
    public View getView() {
        if(this.Ad == null)
            return this.layout;
        else
            return this.Ad;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void onRequestFilled(AdColonyAdView adColonyAdView) {
        this.Ad = adColonyAdView;
    }

    public void onRequestNotFilled(AdColonyZone adColonyInterstitial) {
        try {
            AdcolonyPlugin.getInstance().OnMethodCallHandler("onRequestNotFilled");
            //AdColony.requestAdView(this.Id, this, this.Size);
            Log.e("AdColony", "onRequestNotFilled");
        } catch (Exception e) {
            Log.e("AdColony", e.toString());
        }
    }
}
