package com.developgadget.adcolony;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdSize;
import com.adcolony.sdk.AdColonyAdView;
import com.adcolony.sdk.AdColonyAdViewListener;
import com.adcolony.sdk.AdColonyZone;
import java.util.HashMap;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.platform.PlatformView;

public class BannerView extends AdColonyAdViewListener implements PlatformView {

    private AdColonyAdView Banner;
    private LinearLayout Parent;
    private final MethodChannel channel;
    private final Activity activity;

    BannerView(HashMap args, Context ctx, int id, BinaryMessenger messenger, Activity activity) {
        this.activity = activity;
        this.channel = new MethodChannel(messenger, "AdColony/Banner" + id);
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
        this.Parent = new LinearLayout(ctx);
        this.Parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        this.Parent.setOrientation(LinearLayout.HORIZONTAL);
        this.Parent.setGravity(Gravity.CENTER);
        AdColony.requestAdView(ZoneId, this, Size);
    }

    @Override
    public void onRequestFilled(final AdColonyAdView adColonyAdView) {
        Log.d("AdColony Banner", "onRequestFilled");
        this.Banner = adColonyAdView;
        this.Parent.addView(adColonyAdView);
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BannerView.this.channel.invokeMethod("onRequestFilled", null);
            }
        });
    }

    @Override
    public void onRequestNotFilled(AdColonyZone zone) {
        Log.e("AdColony Banner", "onRequestNotFilled");
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BannerView.this.channel.invokeMethod("onRequestNotFilled", null);
            }
        });
    }

    @Override
    public View getView() {
        if (this.Banner != null) {
            return this.Banner;
        }
        return this.Parent;
    }

    @Override
    public void dispose() {
        if (this.Banner != null) {
            this.Banner.destroy();
        }
    }
}
