package com.srjlove.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    /**
     * don't forget to add static in this inner class (in manifest required )
     */
    public static class MySecondRecieverInner extends BroadcastReceiver{

        private static final String TAG = "MySecondRecieverInner";
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive: Hello from Second broadcast");


            Log.i(TAG, "onReceive: Name are "+ intent.getStringExtra("name")+ " age: "+ intent.getIntExtra("age",0));
            Toast.makeText(context, "Hello from Second inner receiver", Toast.LENGTH_SHORT).show();
        }
    }
}
