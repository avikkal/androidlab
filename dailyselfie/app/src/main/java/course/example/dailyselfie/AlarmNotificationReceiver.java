package course.example.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by javikkal on 8/22/2015.
 */
public class AlarmNotificationReceiver extends BroadcastReceiver {

    private final CharSequence tickerText = "It is selfie time!";
    private long[] mVibratePattern = { 0, 200, 200, 300 };
    private  int SELFIE_NOTIFICATION_ID = 1001;
    public void onReceive(Context context, Intent intent) {
        Log.i("DialySelfieApp", "Logging alarm at:" + DateFormat.getDateTimeInstance().format(new
                Date
                ()));

        RemoteViews notificationView = new RemoteViews("course.example.dailyselfie",R.layout
                .notification_view);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(
                context, 0, notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        Notification.Builder notificationBuilder = new Notification.Builder(
                context).setTicker(tickerText)
                .setSmallIcon(R.drawable.ic_camera_action)
                .setAutoCancel(true)
                .setContentIntent(notificationPendingIntent)
                .setVibrate(mVibratePattern)
                .setContent(notificationView);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context
                .NOTIFICATION_SERVICE);
        notificationManager.notify(SELFIE_NOTIFICATION_ID, notificationBuilder.build());

        Log.i("DialySelfieApp", "Sending notification at:"
                + DateFormat.getDateTimeInstance().format(new Date()));

    }
}
