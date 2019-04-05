package com.barebone.app.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class AppMessageService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e("FIREBASE_MESSAGE", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e("FIREBASE_MESSAGE", "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.

                //scheduleJob();
            } else {
                // Handle message within 10 seconds

                //handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e("FIREBASE_MESSAGE", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        /*Redirect or only show a notification via NotificationHandler class
        i.e. just invoke NotificationHandler.showNotificationWithPendingIntent(params... ) or
        NotificationHandler.showNotificationWithoutPendingIntent(params... ) method as follows
        Note that: we are directly sending remoteMessage object to those methods so make sure to check for null item before
        showing notifications via NotificationHandler class.

        NotificationHandler.showNotificationWithoutPendingIntent(this, remoteMessage, SplashScreenActivity.class, R.drawable.ic_stat_ac_unit);
        */
    }



}
