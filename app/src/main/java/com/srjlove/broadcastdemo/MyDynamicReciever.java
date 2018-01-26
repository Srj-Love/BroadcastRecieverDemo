package com.srjlove.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Suraj on 1/26/2018.
 */

public class MyDynamicReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "my dynamic reciever", Toast.LENGTH_SHORT).show();
    }
}
