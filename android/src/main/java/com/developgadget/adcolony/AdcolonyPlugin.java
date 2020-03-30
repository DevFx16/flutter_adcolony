package com.developgadget.adcolony;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAppOptions;

import java.util.ArrayList;
import java.util.HashMap;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class AdcolonyPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {

    private static AdcolonyPlugin Instance;
    private static Interstitial InstanceInterstitial;
    private static MethodChannel Channel;
    private static Activity ActivityInstance;

    public static AdcolonyPlugin getInstance() {
        return Instance;
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        this.OnAttachedToEngine(flutterPluginBinding.getBinaryMessenger());
    }

    public static void registerWith(Registrar registrar) {
        if (Instance == null) Instance = new AdcolonyPlugin();
        Instance.OnAttachedToEngine(registrar.messenger());
    }

    private void OnAttachedToEngine(BinaryMessenger messenger) {
        if (this.Instance == null) this.Instance = new AdcolonyPlugin();
        if (this.InstanceInterstitial == null) this.InstanceInterstitial = new Interstitial();
        if (this.Channel != null) return;
        this.Channel = new MethodChannel(messenger, "AdColony");
        this.Channel.setMethodCallHandler(this);
    }

    public void OnMethodCallHandler(final String method) {
        try {
            this.ActivityInstance.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Channel.invokeMethod(method, null);
                }
            });
        } catch (Exception e) {
            Log.e("AdColony", "Error " + e.toString());
        }
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        try {
            switch (call.method) {
                case "Init":
                    this.InitSdk((HashMap) call.arguments, result);
                    break;
                case "Request":
                    if (call.argument("IsInter"))
                        this.InstanceInterstitial.Request((String) call.argument("Id"));
                    break;
                case "Show":
                    if (call.argument("IsInter"))
                        this.InstanceInterstitial.Show();
                    break;
            }
            result.success(Boolean.TRUE);
        } catch (Exception e) {
            Log.e("AdColony", "Error " + e.toString());
        }
    }

    private void InitSdk(final HashMap args, Result result) {
        try {
            if (this.ActivityInstance != null) {
                AdColonyAppOptions options = new AdColonyAppOptions() {
                    {
                        setKeepScreenOn(true);
                        setGDPRConsentString((String) args.get("Gdpr"));
                        setGDPRRequired(true);
                    }
                };
                AdColony.configure(this.ActivityInstance, options, (String) args.get("Id"), (String[]) ((ArrayList) args.get("Zones")).toArray());
            } else {
                Log.e("AdColony", "Activity Nulll");
            }
        } catch (Exception e) {
            Log.e("AdColony", e.getMessage());
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    }

    @Override
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        this.ActivityInstance = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
        this.ActivityInstance = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {

    }
}
