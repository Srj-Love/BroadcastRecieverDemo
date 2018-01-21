package com.srjlove.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Suraj on 1/21/2018.
 */

public class MyThirdReciever extends BroadcastReceiver {

    private static final String TAG = "MyThirdReciever";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: Hello from Third broadcast");
        Toast.makeText(context, "Hello from Third reciever", Toast.LENGTH_SHORT).show();

    }
}
