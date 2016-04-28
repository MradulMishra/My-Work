package com.mushrooom.firsemenu;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * Created by MISHRA on 10-03-2016.
 */
public class ActionListenerHandler implements WifiP2pManager.ActionListener{
    MainActivity _activity;
    String _actionDisplayText;
    public ActionListenerHandler(MainActivity activity,String actionDisplayText){
        _actionDisplayText=actionDisplayText;
        _activity=activity;

    }

    @Override
    public void onSuccess() {
        _activity.displayToast((_actionDisplayText+"Done"));
    }

    public void onFailure(int i){
        _activity.displayToast(_actionDisplayText+"Failed");
        Log.d(LogHelper.TAG,String.format("%s Failed - Code: %d",_actionDisplayText,i));
    }
}