package com.developgadget.adcolony;

import android.content.Context;
import java.util.HashMap;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class BannerFactory extends PlatformViewFactory {

    BannerFactory() {
        super(StandardMessageCodec.INSTANCE);
    }

    @Override
    public PlatformView create(Context context, int i, Object args) {
        return new BannerView((HashMap) args);
    }
}
