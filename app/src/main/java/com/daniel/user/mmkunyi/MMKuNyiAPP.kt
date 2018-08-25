package com.daniel.user.mmkunyi

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build.VERSION_CODES.M
import android.support.v4.media.MediaBrowserCompat
import com.daniel.user.mmkunyi.activities.LogInActivity
import com.daniel.user.mmkunyi.activities.MainActivity
import com.daniel.user.mmkunyi.data.MMKuNyiModel

class MMKuNyiAPP : Application() {

    companion object {
        const val LOGTAG = "MMKINYI"
    }
    lateinit var intent: Intent
    override fun onCreate() {
        super.onCreate()
        MMKuNyiModel.initModel(this)

        intent = if (MMKuNyiModel.getInstance().mFirebaseUser != null){
            MainActivity.goToMain(this)
        }
        else
            LogInActivity.goToLogin(this)
        //TODO ask back to sensei
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)


    }






}