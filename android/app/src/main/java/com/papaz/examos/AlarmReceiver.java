package com.papaz.examos;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONArray;
import org.json.JSONObject;

public class AlarmReceiver extends BroadcastReceiver {

    static final String CHANNEL_ID   = "papaz_alarms";
    static final String CHANNEL_NAME = "Papaz Reminders";

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);

        // Read alarm list from SharedPreferences
        // AlarmReceiver has NO Capacitor context - cannot read localStorage.
        SharedPreferences prefs = context
            .getSharedPreferences(AlarmPlugin.PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(AlarmPlugin.KEY_ALARMS, "[]");

        try {
            JSONArray alarms = new JSONArray(json);
            long nowMs = System.currentTimeMillis();
            JSONObject toFire = null;
            int toFireIdx = -1;

            // Find the first alarm that is due (within 60s window)
            for (int i = 0; i < alarms.length(); i++) {
                JSONObject a = alarms.getJSONObject(i);
                long fireAt = a.getLong("fireAt");
                if (fireAt <= nowMs + 60000) {
                    toFire = a;
                    toFireIdx = i;
                    break;
                }
            }

            if (toFire != null) {
                String title = toFire.optString("title", "Papaz");
                String body  = toFire.optString("body",  "Your studies are waiting.");
                int    id    = toFire.optInt("notifId", (int)(nowMs % 100000));
                fireNotification(context, id, title, body);

                // Remove the fired alarm from the list
                JSONArray updated = new JSONArray();
                for (int i = 0; i < alarms.length(); i++) {
                    if (i != toFireIdx) updated.put(alarms.get(i));
                }
                prefs.edit().putString(AlarmPlugin.KEY_ALARMS, updated.toString()).apply();

                // Schedule the next one from the updated list
                scheduleNextFromList(context, updated, nowMs);
            }

        } catch (Exception e) {
            // Malformed JSON - nothing to fire
        }
    }

    /* ------------------------------------------------------------------ */
    /*  Called from JS (via AlarmPlugin) and from BootReceiver             */
    /* ------------------------------------------------------------------ */
    public static void scheduleNext(Context context) {
        SharedPreferences prefs = context
            .getSharedPreferences(AlarmPlugin.PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(AlarmPlugin.KEY_ALARMS, "[]");
        try {
            JSONArray alarms = new JSONArray(json);
            scheduleNextFromList(context, alarms, System.currentTimeMillis());
        } catch (Exception e) {
            // ignore
        }
    }

    private static void scheduleNextFromList(Context context, JSONArray alarms, long nowMs) {
        // Find earliest upcoming alarm
        long earliest = Long.MAX_VALUE;
        try {
            for (int i = 0; i < alarms.length(); i++) {
                JSONObject a = alarms.getJSONObject(i);
                long fireAt = a.getLong("fireAt");
                if (fireAt > nowMs && fireAt < earliest) earliest = fireAt;
            }
        } catch (Exception e) {
            return;
        }
        if (earliest == Long.MAX_VALUE) return; // nothing to schedule

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (am == null) return;

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, earliest, pi);
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, earliest, pi);
        }
    }

    /* ------------------------------------------------------------------ */
    /*  Fire a high-importance notification                                */
    /*  IMPORTANCE_HIGH + vibration + DEFAULT sound - never skip these.    */
    /*  Wrong settings on first install = uninstall required to fix.       */
    /* ------------------------------------------------------------------ */
    private void fireNotification(Context context, int id, String title, String body) {
        // Open app on tap
        Intent openApp = context.getPackageManager()
            .getLaunchIntentForPackage(context.getPackageName());
        PendingIntent tapIntent = PendingIntent.getActivity(
            context, id, openApp,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .setContentIntent(tapIntent);

        try {
            NotificationManagerCompat.from(context).notify(id, builder.build());
        } catch (SecurityException e) {
            // POST_NOTIFICATIONS not granted - nothing we can do here
        }
    }

    /* ------------------------------------------------------------------ */
    /*  Create notification channel once.                                  */
    /*  IMPORTANCE_HIGH + vibration + DEFAULT sound.                       */
    /*  Channels are created once and never updated by Android.            */
    /*  Ship correct settings from the very first build.                   */
    /* ------------------------------------------------------------------ */
    static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Exam reminders and study nudges from Papaz");
            channel.enableVibration(true);
            // DEFAULT sound - never null, never setSilent
            channel.setSound(
                android.provider.Settings.System.DEFAULT_NOTIFICATION_URI,
                new android.media.AudioAttributes.Builder()
                    .setUsage(android.media.AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            );
            NotificationManager nm = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (nm != null) nm.createNotificationChannel(channel);
        }
    }
}
