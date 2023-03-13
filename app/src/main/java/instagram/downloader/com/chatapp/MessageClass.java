package instagram.downloader.com.chatapp;


import android.annotation.TargetApi;

import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


/**
 * Created by Евгений on 04.03.2023.
 */

public class MessageClass extends FirebaseMessagingService {

    private static String CHANNEL_ID = "Cat channel";
    private static final int NOTIFICATION = 101;
    private NotificationManagerCompat notificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        notificationManager = NotificationManagerCompat.from(this);
        if (remoteMessage.getNotification().getBody() != null) {
              String S = remoteMessage.getNotification().getBody();
              String S2 = remoteMessage.getNotification().getTitle();
            Log.i("hhgshghsgh", S);
             Log.i("shgshghsgh", S2);
            sendNotification(remoteMessage);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void sendNotification(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        String S1 = notification.getBody();
        System.out.println(S1);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("уведомленіе")
                .setContentText(S1)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(false);
            notificationBuilder.setChannelId(CHANNEL_ID);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }
}

