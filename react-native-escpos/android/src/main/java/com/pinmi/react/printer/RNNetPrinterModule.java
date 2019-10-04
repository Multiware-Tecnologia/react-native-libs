package com.pinmi.react.printer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.pinmi.react.printer.adapter.BLEPrinterDeviceId;
import com.pinmi.react.printer.adapter.NetPrinterAdapter;
import com.pinmi.react.printer.adapter.NetPrinterDeviceId;
import com.pinmi.react.printer.adapter.PrinterAdapter;
import com.pinmi.react.printer.escpos.PrintPicture;

import javax.annotation.Nullable;

/**
 * Created by xiesubin on 2017/9/22.
 */

public class RNNetPrinterModule extends ReactContextBaseJavaModule implements RNPrinterModule {


    public static final int WIDTH_80 = 576;
    protected ReactApplicationContext reactContext;

    /******************************************************************************************************/

    private int deviceWidth = WIDTH_80;

    private PrinterAdapter adapter;

    public RNNetPrinterModule(ReactApplicationContext reactContext){
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    @Override
    public void init(Callback successCallback, Callback errorCallback) {
        this.adapter = NetPrinterAdapter.getInstance();
        this.adapter.init(reactContext,  successCallback, errorCallback);
    }

    @ReactMethod
    @Override
    public void closeConn() {
        this.adapter.closeConnectionIfExists();
    }

    @ReactMethod
    @Override
    public void getDeviceList(Callback successCallback, Callback errorCallback) {
        this.adapter.getDeviceList(errorCallback);
    }

    @ReactMethod
    public void connectPrinter(String host, Integer port, Callback successCallback, Callback errorCallback) {
        adapter.selectDevice(NetPrinterDeviceId.valueOf(host, port), successCallback, errorCallback);
    }

    @ReactMethod
    @Override
    public void printRawData(String base64Data, Callback errorCallback) {
        adapter.printRawData(base64Data, errorCallback);
    }

    @ReactMethod
    @Override
    public void printPic(String base64encodeStr, @Nullable ReadableMap options, Callback successCallback, Callback errorCallback) {
        int width = 0;
        int leftPadding = 0;
        if(options!=null){
            width = options.hasKey("width") ? options.getInt("width") : 0;
            leftPadding = options.hasKey("left")?options.getInt("left") : 0;
        }

        //cannot larger then devicesWith;
        if(width > deviceWidth || width == 0){
            width = deviceWidth;
        }

        byte[] bytes = Base64.decode(base64encodeStr, Base64.DEFAULT);
        Bitmap mBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        int nMode = 2;
        if (mBitmap != null) {
            /**
             * Parameters:
             * mBitmap  要打印的图片
             * nWidth   打印宽度（58和80）
             * nMode    打印模式
             * Returns: byte[]
             */
            PrintPicture.POS_PrintBMP(mBitmap, width, nMode, leftPadding);

        }
    }

    @Override
    public String getName() {
        return "RNNetPrinter";
    }
}
