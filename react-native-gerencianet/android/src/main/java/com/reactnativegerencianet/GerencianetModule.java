package com.reactnativegerencianet;

import androidx.annotation.NonNull;

import com.reactnativegerencianet.exceptions.GerencianetException;
import com.reactnativegerencianet.Gerencianet;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.module.annotations.ReactModule;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public void generateHash(String accountId, Boolean approvalMode, String brand, String cvv,
            String expiration_month, String expiration_year, String number, final Callback callback)
            throws GerencianetException {
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("account_id", accountId);
        options.put("sandbox", approvalMode);

        Map<String, Object> card = new HashMap<>();
        card.put("brand", brand);
        card.put("number", number);
        card.put("cvv", cvv);
        card.put("expiration_month", expiration_month);
        card.put("expiration_year", expiration_year);

        try {
            Gerencianet gn = new Gerencianet(options);
            Map<String, Object> response = gn.call("paymentToken", new HashMap<String, String>(), card);
            WritableMap hash = new WritableNativeMap();
            hash.putString("hash",convertWithStream(response));
            callback.invoke(hash);
        } catch (GerencianetException error) {
            System.out.println(error.getCode());
            System.out.println(error.getError());
            System.out.println(error.getErrorDescription());
            callback.invoke(error);
        } catch (Exception error) {
            error.printStackTrace();
            System.out.println(error.getMessage());
            callback.invoke(error);
        }
    }

    public String convertWithStream(Map<String, ?> map) {
        String mapAsString = map.keySet().stream()
          .map(key -> key + "=" + map.get(key))
          .collect(Collectors.joining(", ", "{", "}"));
        return mapAsString;
    }
}
