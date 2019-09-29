package com.developgadget.adcolony;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import com.adcolony.sdk.*;
import java.util.ArrayList;

/** AdcolonyPlugin */
public class AdcolonyPlugin implements MethodCallHandler {

    private final Registrar registrar;
    private Interstitial AdInterstitial;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "AdColony");
    channel.setMethodCallHandler(new AdcolonyPlugin(registrar, channel));
    registrar.platformViewRegistry().registerViewFactory("/BannerAd", new BannerFactory());
  }

    private AdcolonyPlugin(Registrar registrar, MethodChannel channel) {
        this.registrar = registrar;
        this.AdInterstitial = new Interstitial(channel);
        AdColony.setRewardListener(this.AdInterstitial);
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        switch (call.method) {
            case "initialize":
                callInitialize(call, result);
                break;
            case "loadInterstitial":
                this.AdInterstitial.Request((String) call.argument("ZONE_ID"));
                break;
            case "showAd":
                this.AdInterstitial.Show();
                break;
            default:
                result.notImplemented();
                break;
        }
        result.success(Boolean.TRUE);
    }

    private void callInitialize(MethodCall call, Result result) {
        String APP_ID = call.argument("APP_ID");
        ArrayList ListZONE = call.argument("ZONE_IDS");
        if(ListZONE != null){
            String[] ZONE_IDS = new String[ListZONE.size()];
            for(int i = 0; i < ListZONE.size(); i++){
                ZONE_IDS[i] = ListZONE.get(i).toString();
            }
            if (APP_ID == null || APP_ID.isEmpty()  || ZONE_IDS.length == 0) {
                result.error("NO APP_ID or ZONE_IDS", "a null or empty APP_ID or ZONE_IDS was provided", null);
                return;
            }
            AdColony.configure(registrar.activity(), APP_ID, ZONE_IDS);
        }
        result.success(Boolean.TRUE);
    }
}
