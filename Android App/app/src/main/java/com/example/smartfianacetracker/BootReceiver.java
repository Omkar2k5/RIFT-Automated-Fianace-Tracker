package com.example.smartfianacetracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null &&
            (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) ||
             intent.getAction().equals("com.example.smartfianacetracker.RESTART_SERVICE"))) {
            
            Log.d(TAG, "Received boot or restart broadcast");
            Intent serviceIntent = new Intent(context, SmsService.class);
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
            Log.d(TAG, "Service start requested");
        }
    }
}
