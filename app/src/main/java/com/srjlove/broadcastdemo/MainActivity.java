package com.srjlove.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MyDynamicReciever myDynamicReciever;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDynamicReciever = new MyDynamicReciever();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

    }


    public void broadcastMessage(View view) {
        //Intent mIntent = new Intent(this, MyFirstReciever.class);// send broadcast explicitly
        Intent mIntent = new Intent("my.custom.reciever");

        // send extra data via intent
        // mIntent.putExtra("name", "Srj Love");
        //mIntent.putExtra("age", 21);

        //sendBroadcast(mIntent); // for running NORMAL broadcast
        sendOrderedBroadcast(mIntent, null); // for running ORDERED broadcast

        // this proves broadcast are sent on asynchronously (1st we see this toast and then the other Broadcast messages)
        Toast.makeText(this, "after sending Broadcast", Toast.LENGTH_SHORT).show();
    }

    public void broadcastSecondReciever(View view) {
        Intent mIntent = new Intent(this, MySecondRecieverInner.class);// send broadcast explicitly
        // Intent mIntent = new Intent("my.custom.another.reciever");

        // send extra data via Bundle
        Bundle mBundle = new Bundle();
        mBundle.putString("name", "Srj Love");
        mBundle.putInt("age", 21);
        mIntent.putExtras(mBundle);

        // sendBroadcast(mIntent); // for running NORMAL broadcast
        sendOrderedBroadcast(mIntent, null); // for running ORDERED broadcast
    }

    @Override
    protected void onResume() {
        super.onResume();
        //creating reciever in dynamic way

        IntentFilter mFilter = new IntentFilter(Intent.ACTION_INPUT_METHOD_CHANGED);// use intent instead of defining string
        registerReceiver(myDynamicReciever, mFilter);


        // sticky broadcast
        IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent i = registerReceiver(null, mIntentFilter);
        assert i != null;
        int status = i.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        getBatteryStatus(status);
        Log.i(TAG, i + "");

        // local Broadcast
        IntentFilter mLocalFileter = new IntentFilter("my.local.broadcast");
        localBroadcastManager.registerReceiver(myLocalBroadcastReceiver, mLocalFileter);
    }


    @Override
    protected void onPause() {
        super.onPause();

        /* don't forget to unregister reciever */
        unregisterReceiver(myDynamicReciever);
        unregisterReceiver(myStickyBroadcast);
        localBroadcastManager.unregisterReceiver(myLocalBroadcastReceiver);
    }

    public void stickyBroadcast(View view) {
        IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent i = registerReceiver(myStickyBroadcast, mIntentFilter);

        Log.i(TAG, i + "");

    }

    private void getBatteryStatus(int status) {
        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            Toast.makeText(this, "Battery charging", Toast.LENGTH_SHORT).show();
        } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
            Toast.makeText(this, "Battery Full", Toast.LENGTH_SHORT).show();
        } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
            Toast.makeText(this, "BATTERY_STATUS_DISCHARGING", Toast.LENGTH_SHORT).show();
        }
    }

    public void myLocalBroadcast(View view) {
        Intent localBroad = new Intent(this, MyLocalBroadcast.class);
        localBroad.putExtra("num_one", 20);
        localBroad.putExtra("num_two", 30);
        sendBroadcast(localBroad);
    }

    /**
     * don't forget to add static in this inner class (in manifest required )
     */
    public static class MySecondRecieverInner extends BroadcastReceiver {

        private static final String TAG = "MySecondRecieverInner";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive: Hello from Second broadcast");


            Log.i(TAG, "onReceive: Name are " + intent.getStringExtra("name") + " age: " + intent.getIntExtra("age", 0));
            Toast.makeText(context, "Hello from Second inner receiver", Toast.LENGTH_SHORT).show();
        }
    }

    private BroadcastReceiver myStickyBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            getBatteryStatus(status);
        }
    };


    private BroadcastReceiver myLocalBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int sum = intent.getIntExtra("sum", 0);
            Toast.makeText(context, "Sum is " + sum, Toast.LENGTH_SHORT).show();
        }
    };
}
