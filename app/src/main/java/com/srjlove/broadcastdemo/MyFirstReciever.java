package com.srjlove.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Suraj on 1/21/2018.
 */

public class MyFirstReciever extends BroadcastReceiver {

    private static final String TAG = "MyFirstReciever";

    @Override
    public void onReceive(Context context, Intent intent) {

        // Broadcast always run in Main thread, don't run long running task in main.
        // app notice time is > 10s time than app will crash
        Log.i(TAG, "onReceive: Hello from first broadcast, Thread name: "+ Thread.currentThread().getName());
        Toast.makeText(context, "Hello from First reciever", Toast.LENGTH_SHORT).show();


        Log.i(TAG, "onReceive: Name are "+ intent.getStringExtra("name")+ " age: "+ intent.getIntExtra("age",0));
    }

}
