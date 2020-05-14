package com.example.baher.fyeapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String TAG = "NOTIFICATIONZ";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "Service Created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "Service Destroyed...");
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();

        String text= data.get("text");

        Log.i(TAG,"Message: "+text);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this)

                .setSmallIcon(R.drawable.fyelogo)
                .setContentTitle(data.get("title"))
                .setContentText(text).setContentIntent(pendingIntent)
                .setSound(Uri.parse(String.valueOf(defaultSoundUri)));
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder.setAutoCancel(true);
        manager.notify(0, builder.build());

    }
}