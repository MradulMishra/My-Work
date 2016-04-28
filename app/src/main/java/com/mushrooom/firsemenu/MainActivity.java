package com.mushrooom.firsemenu;

import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements WifiP2pManager.ChannelListener{
    private WifiP2pManager _wfdManager;
    private WifiP2pManager.Channel _wfdChannel;
    private WIFIDirectReceiver _wfdReceiver;
    TextView summary;
     Button clear;
    String cool="background processes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        _wfdManager =(WifiP2pManager)getSystemService(WIFI_P2P_SERVICE);
        _wfdChannel =_wfdManager.initialize(this, getMainLooper(), this);
        summary=(TextView)findViewById(R.id.textView);
        clear=(Button)findViewById(R.id.button);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu1:
            registerWfdReciver();
            displaySummary("registering Receiver");
            break;
        case R.id.menu2:
            unregisterWfdReceiver();
            displaySummary("removing Receiver");

            break;
        case R.id.menu3:
            if (isWfdreceiverregisteredAndFeatureEnabled()) {
                _wfdManager.discoverPeers(_wfdChannel, new ActionListenerHandler(this, "Discover peers"));
            }
            displaySummary("Discovering Peers");

            break;
        case R.id.menu4:
            if(isWfdreceiverregisteredAndFeatureEnabled()){
                WifiP2pDevice theDevice=_wfdReceiver.getFirstAvailableDevice();
                if(theDevice!=null){
                    WifiP2pConfig config =new WifiP2pConfig();
                    config.deviceAddress=theDevice.deviceAddress;
                    config.wps.setup= WpsInfo.PBC;
                    _wfdManager.connect(_wfdChannel,config,new ActionListenerHandler(this,"Connection "));
                    displaySummary("Device available");
                    displaySummary("Connecting");


                }
                else{
                    displayToast("no device currently available");
                    displaySummary("no device available");
                }
            }
            break;
        case R.id.menu5:
            finish();
            break;

    }
        return false;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterWfdReceiver();
    }
    public void displayToast(String message){
        Toast toast= Toast.makeText(this,message,Toast.LENGTH_LONG);
        toast.show();
    }
    public void registerWfdReciver(){
        _wfdReceiver=new WIFIDirectReceiver(_wfdManager,_wfdChannel,this);
        _wfdReceiver.registerReceiver();
    }
    public void unregisterWfdReceiver(){
        if(_wfdReceiver!=null)
            _wfdReceiver.unregisterReceiver();
        _wfdReceiver=null;

    }

    private boolean isWfdreceiverregisteredAndFeatureEnabled(){
        boolean isWfdUsable =_wfdReceiver !=null && _wfdReceiver.isWifiDirectEnabled();
        if(!isWfdUsable)
        {
            showWfdReceiverNotRegisteredOrFeaturedNotEnaledMessage();

        }
        return  isWfdUsable;
    }

    private void showWfdReceiverNotRegisteredOrFeaturedNotEnaledMessage(){
        displayToast(_wfdReceiver == null ? "wifiDirect broadcast receiver not yet registered" : "wifidirect not enabled on phone");
    }

    public void onChannelDisconnected(){
        displayToast("wifi direct channel disconnected -reinitializing");
        reinitializeChannel();

    }
    public void reinitializeChannel(){
        _wfdChannel =_wfdManager.initialize(this,getMainLooper(),this);
        if(_wfdChannel!=null) {
            displayToast("wifi direct channel initialization : Succcess");
            displaySummary("wifi direct channel initialization : Succcess");


        }
        else{
            displayToast("wifi direct channel initialization :failed");
            displaySummary("wifi direct channel initialization :failed");


        }
    }
    public void displaySummary(String str){
        cool=cool+"\n"+str;
        summary.setText(cool);
        displayToast("reached here");
    }
    public void clearScreen(View view){
       // ourString=null;
        summary.setText(null);
    }
}
