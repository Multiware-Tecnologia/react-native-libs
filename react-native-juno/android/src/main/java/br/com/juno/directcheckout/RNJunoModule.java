//
//  react-native-juno
//  Created by Mauro Moura - Group ADBRAX on 08/04/20.
//

package br.com.juno.directcheckout;

import br.com.juno.directcheckout.DirectCheckout;
import br.com.juno.directcheckout.model.Card;
import br.com.juno.directcheckout.model.DirectCheckoutException;
import br.com.juno.directcheckout.model.DirectCheckoutListener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.OrientationEventListener;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.util.DisplayMetrics;
import android.hardware.SensorManager;

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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class RNJunoModule extends ReactContextBaseJavaModule {

    public RNJunoModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }


    @Override
    public String getName() {
        return "RNJunoModule";
    }

    @ReactMethod
    public void initializeJuno(String token, Boolean production){
            DirectCheckout.initialize(getCurrentActivity(), production, token);
    }

    @ReactMethod
    public void generateHash(String cardNumber, String holderName, String securityCode, String expirationMonth, String expirationYear, final Callback callback){
        Card card  = new Card(
                cardNumber,
                holderName,
                securityCode,
                expirationMonth,
                expirationYear
        );

        DirectCheckout.getCardHash(card, new DirectCheckoutListener<String>() {
            @Override
            public void onSuccess(String cardHash) {
                callback.invoke(cardHash);
            }

            @Override
            public void onFailure(DirectCheckoutException exception) {
                System.out.println(exception.getMessage());
                callback.invoke(exception.getMessage());
            }
        });
    }
}
