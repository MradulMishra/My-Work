package com.mushrooom.firsemenu;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.util.Log;




public class LogHelper {
    public final static String TAG ="Pluralsight";

    public final static void logDevice(WifiP2pDevice device){
        Log.i(TAG, "Device Address: " + device.deviceAddress);
        Log.i(TAG,"Device Name: "+ device.deviceName);
        Log.i(TAG,"Device Primary Type: "+ device.primaryDeviceType);
        Log.i(TAG,"Device Secondary Type "+ (device.secondaryDeviceType !=null ?device.secondaryDeviceType:null));
        Log.i(TAG,"Device Is Group Owner: "+ (device.isGroupOwner()? "yes":"no"));
        Log.i(TAG,"Device Status: "+ translateDeviceStatus(device.status));
    }
    public final static  void logDeviceList(WifiP2pDeviceList devices){
        Log.i(TAG,String.format("Number of Devices in list :%d",devices.getDeviceList().size()));
        for (WifiP2pDevice device:devices.getDeviceList()){
            logDevice(device);
        }
    }
    public final static  String translateDeviceStatus(int status){
        String statusString=null;
        switch(status){
            case WifiP2pDevice.AVAILABLE:
                statusString="Available";
                break;
            case WifiP2pDevice.INVITED:
                statusString="invited";
                break;
            case WifiP2pDevice.FAILED:
                statusString="failed";
                break;
            case WifiP2pDevice.CONNECTED:
                statusString="connected";
                break;
        }
        return statusString;
    }
}
