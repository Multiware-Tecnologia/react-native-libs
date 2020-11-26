package com.pinmi.react.printer;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.pinmi.react.printer.adapter.BLEPrinterAdapter;
import com.pinmi.react.printer.adapter.BLEPrinterDeviceId;
import com.pinmi.react.printer.adapter.PrinterAdapter;
import com.pinmi.react.printer.adapter.PrinterDevice;
import com.pinmi.react.printer.adapter.USBPrinterAdapter;
import com.pinmi.react.printer.adapter.USBPrinterDeviceId;
import com.facebook.react.bridge.*;
import javax.annotation.Nullable;
import com.pinmi.react.printer.escpos.PrintPicture;
import android.util.Base64;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.pinmi.react.printer.escpos.EscPosHelper;
import java.util.*;
import android.util.Log;
import com.pinmi.react.printer.escpos.Command;
import com.pinmi.react.printer.escpos.PrinterCommand;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import android.net.Uri;

import java.util.List;

/**
 * Created by xiesubin on 2017/9/22.
 */

public class RNUSBPrinterModule extends ReactContextBaseJavaModule implements RNPrinterModule {


    private static int[] p0 = new int[]{0, 128};
    private static int[] p1 = new int[]{0, 64};
    private static int[] p2 = new int[]{0, 32};
    private static int[] p3 = new int[]{0, 16};
    private static int[] p4 = new int[]{0, 8};
    private static int[] p5 = new int[]{0, 4};
    private static int[] p6 = new int[]{0, 2};


    public static final int WIDTH_80 = 576;
    public static final byte[] LINE_SPACE_24   = {0x1b,0x33,24}; // Set the line spacing at 24
    public static final byte[] CUT_PAPER   = {0x1b,0x69};
    public static final byte[] CENTER_PAPER   = {0x1B,0x61,49};
    //Image
    public static final byte[] SELECT_BIT_IMAGE_MODE = {0x1B, 0x2A, 33};
    public static final byte[] CTL_LF          = {0x0a};          // Print and line feed
    private int printingWidth = WIDTH_80;

    protected ReactApplicationContext reactContext;
    
    /******************************************************************************************************/

    private int deviceWidth = WIDTH_80;

    protected PrinterAdapter adapter;

    public RNUSBPrinterModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    @Override
    public void init(Callback successCallback, Callback errorCallback) {
        this.adapter = USBPrinterAdapter.getInstance();
        this.adapter.init(reactContext,  successCallback, errorCallback);
    }

    @ReactMethod
    @Override
    public void closeConn()  {
        adapter.closeConnectionIfExists();
    }

    @ReactMethod
    @Override
    public void getDeviceList(Callback successCallback, Callback errorCallback)  {
        List<PrinterDevice> printerDevices = adapter.getDeviceList(errorCallback);
        WritableArray pairedDeviceList = Arguments.createArray();
        if(printerDevices.size() > 0) {
            for (PrinterDevice printerDevice : printerDevices) {
                pairedDeviceList.pushMap(printerDevice.toRNWritableMap());
            }
            successCallback.invoke(pairedDeviceList);
        }else{
            errorCallback.invoke("No Device Found");
        }
    }

    @ReactMethod
    @Override
    public void printRawData(String base64Data, Callback errorCallback){
        adapter.sendData(LINE_SPACE_24);
        adapter.sendData(CENTER_PAPER);
        adapter.printRawData(base64Data, errorCallback);
        adapter.sendData(CTL_LF);
        adapter.sendData(CTL_LF);
        adapter.sendData(CTL_LF);
        adapter.sendData(CTL_LF);
        adapter.sendData(CUT_PAPER);
    }

    @ReactMethod
    @Override
    public void printPic(String base64encodeStr, @Nullable  ReadableMap options, Callback successCallback, Callback errorCallback)throws IOException {
        int nWidth = 0;
        int leftPadding = 0;
        if(options!=null){
            nWidth = options.hasKey("width") ? options.getInt("width") : 0;
            leftPadding = options.hasKey("left")?options.getInt("left") : 0;
        }

        //cannot larger then devicesWith;
        if(nWidth > deviceWidth || nWidth == 0){
            nWidth = deviceWidth;
        }

        byte[] bytes = Base64.decode(base64encodeStr, Base64.DEFAULT);

        Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        image = EscPosHelper.resizeImage(image, printingWidth);


        int nBytesPerLine = nWidth / 3;
        int sizeArray = nBytesPerLine + 6;
        int k = 0;
        byte[] data = new byte[sizeArray];
        int nHeight = image.getHeight() / nWidth;


        adapter.sendData(LINE_SPACE_24);


        try {
            for (int y = 0; y < image.getHeight(); y += 24) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                baos.write(SELECT_BIT_IMAGE_MODE); // bit mode
                // width, low & high
                baos.write(new byte[] { (byte) (0x00ff & image.getWidth()), (byte) ((0xff00 & image.getWidth()) >> 8) });
                for (int x = 0; x < image.getWidth(); x++) {
                    // For each vertical line/slice must collect 3 bytes (24 bytes)
                    baos.write(EscPosHelper.collectImageSlice(y, x, image));
                }

                baos.write(CTL_LF);
                adapter.sendData(baos.toByteArray());
                Thread.sleep(50);
            }
        }catch (Exception e) {
            System.out.println("LOG - Exception: " + e);
        }

        successCallback.invoke(true);

        adapter.sendData(CTL_LF);
        adapter.sendData(CTL_LF);
        adapter.sendData(CUT_PAPER);


    }



    @ReactMethod
    public void connectPrinter(Integer vendorId, Integer productId, Callback successCallback, Callback errorCallback) {
        adapter.selectDevice(USBPrinterDeviceId.valueOf(vendorId, productId), successCallback, errorCallback);
    }

    @Override
    public String getName() {
        return "RNUSBPrinter";
    }
}
