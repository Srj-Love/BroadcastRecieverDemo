package com.srjlove.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Suraj on 1/26/2018.
 */

public class MyLocalBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int one = intent.getIntExtra("num_one", 0);
        int two = intent.getIntExtra("num_two", 0);

        int sum = one+ two;

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        Intent returningIntent1 = new Intent("my.local.broadcast"); // broadcast result  locally to activity
        returningIntent1.putExtra("sum", sum);
        manager.sendBroadcast(returningIntent1);
    }
}
