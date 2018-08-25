package com.daniel.user.mmkunyi.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.app.TaskStackBuilder
import android.text.TextUtils

import com.bumptech.glide.Glide
import com.daniel.user.mmkunyi.R
import com.daniel.user.mmkunyi.activities.MainActivity

import java.util.concurrent.ExecutionException


/**
 * Created by aung on 8/19/17.
 */

object NotificationUtils {

    private val NOTIFICATION_ID_NEW_MESSAGE = 2001

    private val REQUEST_ID_SAVE_NEWS = 3001

    val KEY_MESSAGE = "custom_msg"

    fun notifyCustomMsg(context: Context, message: String) {
        //Notification Title
        val title = context.getString(R.string.launcher_name)

        //Supporting both unicode & zawgyi
        val mmMessage = message

        //Large Icon
        val appIcon = encodeResourceToBitmap(context, R.mipmap.ic_launcher)

        //Message in BigText Style
        val bigTextStyle = NotificationCompat.BigTextStyle()
        bigTextStyle.bigText(mmMessage)

        val builder = NotificationCompat.Builder(context)
                .setColor(context.resources.getColor(R.color.colorAccent))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(appIcon)
                .setContentTitle(title)
                .setContentText(mmMessage)
                .setAutoCancel(true)
                .setStyle(bigTextStyle)

        //Notification action to Play Songs by Artist.
        saveNewsAction(context, NOTIFICATION_ID_NEW_MESSAGE, builder)

        //Open the app when user tap on notification
//        val resultIntent = MainActivity.newIntentNotiBody(context)

        val stackBuilder = TaskStackBuilder.create(context)
//        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(resultPendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID_NEW_MESSAGE, builder.build())
    }

    private fun encodeResourceToBitmap(context: Context, resourceId: Int): Bitmap? {
        var bitmap: Bitmap? = null
        //Encode bitmap for large notification icon
        val largeIconWidth = context.resources.getDimensionPixelSize(android.R.dimen.notification_large_icon_width)
        val largeIconHeight = context.resources.getDimensionPixelSize(android.R.dimen.notification_large_icon_height)

        try {
            bitmap = Glide.with(context)
                    .asBitmap()
                    .load(resourceId)
                    .into(largeIconWidth, largeIconHeight)
                    .get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

        return bitmap
    }

    private fun saveNewsAction(context: Context, notificationId: Int, builder: NotificationCompat.Builder) {
        //Intent to execute when user tap on Action Button.
        val saveNewsActionIntent = MainActivity.newIntentSaveNews(context, notificationId)
        val playSongsByArtistActionPendingIntent = PendingIntent.getActivity(context, REQUEST_ID_SAVE_NEWS, saveNewsActionIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        //"Save News" Action Label.
        val notiActionSaveNews = context.getString(R.string.noti_action_save_news)

        //Action Button Icon.
        val actionIcon = R.drawable.icon_profile
        if (TextUtils.equals(Build.BRAND, "Xiaomi")) {
            //actionIcon = R.drawable.ic_other_bookmark_border_24dp;
        }

        val playSongsByArtistAction = NotificationCompat.Action(actionIcon,
                notiActionSaveNews, playSongsByArtistActionPendingIntent)
        builder.addAction(playSongsByArtistAction)
    }
}
