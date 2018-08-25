package com.daniel.user.mmkunyi.fcm;

import android.util.Log;

import com.daniel.user.mmkunyi.MMKuNyiAPP;
import com.daniel.user.mmkunyi.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(MMKuNyiAPP.LOGTAG, "FCM Message : " + remoteMessage.getMessageId());
        Log.d(MMKuNyiAPP.LOGTAG, "FCM Notification Message: " +
                remoteMessage.getNotification());
        Log.d(MMKuNyiAPP.LOGTAG, "FCM Data Message: " + remoteMessage.getData());

        Map<String, String> remoteMsgData = remoteMessage.getData();
        String message = remoteMsgData.get(NotificationUtils.INSTANCE.getKEY_MESSAGE());

        NotificationUtils.INSTANCE.notifyCustomMsg(getApplicationContext(), message);
    }
}
