package com.reactnativegerencianet;

import androidx.annotation.NonNull;

import com.reactnativegerencianet.Gerencianet;

import com.facebook.react.bridge.Promise;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.module.annotations.ReactModule;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ReactModule(name = GerencianetModule.NAME)
public class GerencianetModule extends ReactContextBaseJavaModule {
    public static final String NAME = "GerencianetModule";

    public GerencianetModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    public void generateHash(String accountId, Boolean approval, Int number, String brand, Int cvv, Int expiration_month, Int expiration_year,  final Callback callback) throws GerencianetException{
        HashMap<String, Object> options = new HashMap<String>();
        options.put("account_id", accountId);
        options.put("sandbox", approval);

        Map<String, Object> card = new HashMap<>();
        card.put("brand", brand);
        card.put("number", number);
        card.put("cvv", cvv);
        card.put("expiration_month", expiration_month);
        card.put("expiration_year", expiration_year);

        try {
            Gerencianet gn = new Gerencianet(options);
            Map<String, Object> response = gn.call("paymentToken", new HashMap<String,String>(), card);
            System.out.println(response);
            callback.invoke(response);
        }catch (GerencianetException e){
            System.out.println(e.getCode());
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
