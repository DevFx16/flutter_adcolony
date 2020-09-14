package com.developgadget.adcolony;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdSize;
import com.adcolony.sdk.AdColonyAdView;
import com.adcolony.sdk.AdColonyAdViewListener;
import com.adcolony.sdk.AdColonyZone;

import java.util.HashMap;

import io.flutter.Log;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

@SuppressWarnings("SuspiciousMethodCalls")
public class Banner extends AdColonyAdViewListener implements PlatformView, MethodChannel.MethodCallHandler {

    private FrameLayout layout;
    private String idString;
    private AdColonyAdSize size;
    private MethodChannel channel;
    private final HashMap<String, AdColonyAdSize> sizes = new HashMap<String, AdColonyAdSize>() {
        {
            put("BANNER", AdColonyAdSize.BANNER);
            put("LEADERBOARD", AdColonyAdSize.LEADERBOARD);
            put("MEDIUM_RECTANGLE", AdColonyAdSize.MEDIUM_RECTANGLE);
            put("SKYSCRAPER", AdColonyAdSize.SKYSCRAPER);
        }
    };

    Banner(Context context, BinaryMessenger messenger, int id, HashMap args) {
        try {
            this.idString = (String) args.get("Id");
            this.size = this.sizes.get(args.get("Size"));
            assert this.idString != null;
            assert this.size != null;
            this.layout = new FrameLayout(AdcolonyPlugin.ActivityInstance);
            this.channel = new MethodChannel(messenger, "Banner" + "_" + id);
            this.channel.setMethodCallHandler(this);
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

    public void updateView(AdColonyAdView view) {
        this.layout.removeAllViews();
        this.layout.addView(view);
    }

    @Override
    public void onRequestFilled(AdColonyAdView adColonyAdView) {
        this.updateView(adColonyAdView);
    }

    public void onRequestNotFilled(AdColonyZone adColonyInterstitial) {
        try {
            this.OnMethodCallHandler("onRequestNotFilled");
            Log.e("AdColony", "onRequestNotFilled");
            this.layout.removeAllViews();
        } catch (Exception e) {
            Log.e("AdColony", e.toString());
        }
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        switch (call.method) {
            case "loadAd":
                AdColony.requestAdView(this.idString, this, this.size);
                break;
            default:
                result.notImplemented();
        }
    }

    void OnMethodCallHandler(final String method) {
        try {
            AdcolonyPlugin.ActivityInstance.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Banner.this.channel.invokeMethod(method, null);
                }
            });
        } catch (Exception e) {
            Log.e("AdColony", "Error " + e.toString());
        }
    }
}
