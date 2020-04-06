package com.developgadget.adcolony;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

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
import io.flutter.plugin.platform.PlatformViewRegistry;

@SuppressWarnings("ConstantConditions")
public class AdcolonyPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {

    @SuppressLint("StaticFieldLeak")
    private static AdcolonyPlugin Instance;
    private static MethodChannel Channel;
    @SuppressLint("StaticFieldLeak")
    static Activity ActivityInstance;
    static AdColonyInterstitial Ad;
    private static final Listeners listeners = new Listeners();

    static AdcolonyPlugin getInstance() {
        return Instance;
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        this.OnAttachedToEngine(flutterPluginBinding.getBinaryMessenger());
        this.RegistrarBanner(flutterPluginBinding.getPlatformViewRegistry());
    }

    public static void registerWith(Registrar registrar) {
        if (Instance == null) Instance = new AdcolonyPlugin();
        Instance.OnAttachedToEngine(registrar.messenger());
        Instance.RegistrarBanner(registrar.platformViewRegistry());
    }

    private void RegistrarBanner(PlatformViewRegistry registry) {
        registry.registerViewFactory("/Banner", new BannerFactory());
    }

    private void OnAttachedToEngine(BinaryMessenger messenger) {
        if (AdcolonyPlugin.Instance == null)
            AdcolonyPlugin.Instance = new AdcolonyPlugin();
        if (AdcolonyPlugin.Channel != null)
            return;
        AdcolonyPlugin.Channel = new MethodChannel(messenger, "AdColony");
        AdcolonyPlugin.Channel.setMethodCallHandler(this);
    }

    void OnMethodCallHandler(final String method) {
        try {
            AdcolonyPlugin.ActivityInstance.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Channel.invokeMethod(method, null);
                }
            });
        } catch (Exception e) {
            Log.e("AdColony", "Error " + e.toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        try {
            switch (call.method) {
                case "Init":
                    this.InitSdk((HashMap) call.arguments);
                    break;
                case "Request":
                    AdColony.requestInterstitial((String) call.argument("Id"), AdcolonyPlugin.listeners);
                    break;
                case "Show":
                    if (AdcolonyPlugin.Ad != null)
                        AdcolonyPlugin.Ad.show();
                    break;
            }
            result.success(Boolean.TRUE);
        } catch (Exception e) {
            Log.e("AdColony", "Error " + e.toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void InitSdk(final HashMap args) {
        try {
            if (AdcolonyPlugin.ActivityInstance != null) {
                AdColonyAppOptions options = new AdColonyAppOptions() {
                    {
                        setKeepScreenOn(true);
                        setGDPRConsentString((String) Objects.requireNonNull(args.get("Gdpr")));
                        setGDPRRequired(true);
                    }
                };
                Object[] arrayList = ((ArrayList) args.get("Zones")).toArray();
                String[] Zones = Arrays.copyOf(arrayList, arrayList.length, String[].class);
                AdColony.configure(AdcolonyPlugin.ActivityInstance, options, (String) args.get("Id"), Zones);
                AdColony.setRewardListener(AdcolonyPlugin.listeners);
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
        AdcolonyPlugin.ActivityInstance = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
        AdcolonyPlugin.ActivityInstance = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {

    }
}
