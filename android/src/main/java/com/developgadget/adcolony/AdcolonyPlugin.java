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

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "AdColony");
    final MethodChannel channelInter = new MethodChannel(registrar.messenger(), "AdColony/Interstitial");
    channel.setMethodCallHandler(new AdcolonyPlugin(registrar, channel));
    channelInter.setMethodCallHandler(new Interstitial(registrar, channelInter));
    registrar.platformViewRegistry().registerViewFactory("/BannerAd", new BannerFactory(registrar.messenger(), registrar.activity()));
  }

    private AdcolonyPlugin(Registrar registrar, MethodChannel channel) {
        this.registrar = registrar;
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if ("initialize".equals(call.method)) {
            callInitialize(call, result);
        } else {
            result.notImplemented();
        }
    }

    private void callInitialize(MethodCall call, Result result) {
        String APP_ID = call.argument("APP_ID");
        ArrayList ListZONE = call.argument("ZONE_IDS");
        final String GDPR = call.argument("GDPR");
        assert GDPR != null;
        if(ListZONE != null){
            String[] ZONE_IDS = new String[ListZONE.size()];
            for(int i = 0; i < ListZONE.size(); i++){
                ZONE_IDS[i] = ListZONE.get(i).toString();
            }
            if (APP_ID == null || APP_ID.isEmpty()  || ZONE_IDS.length == 0) {
                result.error("NO APP_ID or ZONE_IDS", "a null or empty APP_ID or ZONE_IDS was provided", null);
                return;
            }
            AdColonyAppOptions options = new AdColonyAppOptions(){
                {
                    setKeepScreenOn(true);
                    setGDPRConsentString(GDPR);
                    setGDPRRequired(true);
                }
            };
            AdColony.configure(registrar.activity(),options, APP_ID, ZONE_IDS);
        }
        result.success(Boolean.TRUE);
    }
}
