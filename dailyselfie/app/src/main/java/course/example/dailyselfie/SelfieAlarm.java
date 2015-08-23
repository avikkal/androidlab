package course.example.dailyselfie;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by javikkal on 8/22/2015.
 */
public class SelfieAlarm{
    private Activity mActivity;
    private AlarmManager mAlarmManager;
    private Intent mNotificationReceiverIntent;
    private PendingIntent mNotificationReceiverPendingIntent;

    private static final long ALARM_DELAY = 2 * 60 * 1000L;

    public SelfieAlarm(Activity activity){
        mActivity = activity;
    }

    public  void setAlarm(){

        mAlarmManager = (AlarmManager) mActivity.getSystemService(Context.ALARM_SERVICE);
        mNotificationReceiverIntent = new Intent(mActivity,
                                                 AlarmNotificationReceiver.class);
        // Create an PendingIntent that holds the NotificationReceiverIntent
        mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(mActivity, 0,
                mNotificationReceiverIntent, 0);

        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + ALARM_DELAY,
                ALARM_DELAY, mNotificationReceiverPendingIntent);


    }


}
