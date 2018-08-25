package com.daniel.user.mmkunyi.fcm;

import android.util.Log;

import com.daniel.user.mmkunyi.MMKuNyiAPP;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class AppFirebaseInstanceIdService extends FirebaseInstanceIdService {

    /**
     * The Application's current Instance ID token is no longer valid and thus a new one must be requested.
     */
    public void onTokenRefresh() {
        // If you need to handle the generation of a token, initially or
        // after a refresh this is where you should do that.
        String token = FirebaseInstanceId.getInstance().getToken();
        //TODO syncUserRegistrationId(token);
        Log.d(MMKuNyiAPP.LOGTAG, "FCM Token : " + token);
    }
}
