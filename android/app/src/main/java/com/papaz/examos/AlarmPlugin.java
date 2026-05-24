package com.papaz.examos;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "AlarmPlugin")
public class AlarmPlugin extends Plugin {

    static final String PREFS_NAME  = "papaz_prefs";
    static final String KEY_ALARMS  = "alarm_list_json";
    static final String KEY_NOTIF   = "notif_enabled";

    /* ------------------------------------------------------------------ */
    /*  JS -> Java: save alarm list to SharedPreferences                   */
    /* ------------------------------------------------------------------ */
    @PluginMethod
    public void saveAlarms(PluginCall call) {
        String json = call.getString("json", "[]");
        SharedPreferences prefs = getContext()
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_ALARMS, json).apply();
        call.resolve();
    }

    /* ------------------------------------------------------------------ */
    /*  JS -> Java: schedule the next alarm from the saved list            */
    /* ------------------------------------------------------------------ */
    @PluginMethod
    public void scheduleNext(PluginCall call) {
        AlarmReceiver.scheduleNext(getContext());
        call.resolve();
    }

    /* ------------------------------------------------------------------ */
    /*  JS -> Java: cancel all pending alarms                              */
    /* ------------------------------------------------------------------ */
    @PluginMethod
    public void cancelAll(PluginCall call) {
        AlarmManager am = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(
            getContext(), 0, intent,
            PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE
        );
        if (pi != null && am != null) {
            am.cancel(pi);
            pi.cancel();
        }
        call.resolve();
    }

    /* ------------------------------------------------------------------ */
    /*  JS -> Java: check if notifications are enabled                     */
    /*  Always use Java bridge - WebView Notification.permission lies.     */
    /* ------------------------------------------------------------------ */
    @PluginMethod
    public void areNotificationsEnabled(PluginCall call) {
        boolean enabled = NotificationManagerCompat
            .from(getContext())
            .areNotificationsEnabled();
        JSObject ret = new JSObject();
        ret.put("enabled", enabled);
        call.resolve(ret);
    }

    /* ------------------------------------------------------------------ */
    /*  JS -> Java: set nav bar color (accent bleed)                       */
    /* ------------------------------------------------------------------ */
    @PluginMethod
    public void setNavBarColor(PluginCall call) {
        String hex = call.getString("color", "#0a0a0b");
        try {
            int color = android.graphics.Color.parseColor(hex);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    getActivity().getWindow().setNavigationBarColor(color);
                }
            });
        } catch (IllegalArgumentException e) {
            // bad hex - ignore
        }
        call.resolve();
    }

    /* ------------------------------------------------------------------ */
    /*  JS -> Java: set status bar color (accent bleed)                    */
    /* ------------------------------------------------------------------ */
    @PluginMethod
    public void setStatusBarColor(PluginCall call) {
        String hex = call.getString("color", "#0a0a0b");
        try {
            int color = android.graphics.Color.parseColor(hex);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    getActivity().getWindow().setStatusBarColor(color);
                }
            });
        } catch (IllegalArgumentException e) {
            // bad hex - ignore
        }
        call.resolve();
    }

    /* ------------------------------------------------------------------ */
    /*  JS -> Java: haptic feedback                                        */
    /* ------------------------------------------------------------------ */
    @PluginMethod
    public void vibrate(PluginCall call) {
        int duration = call.getInt("duration", 20);
        android.os.Vibrator v = (android.os.Vibrator)
            getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (v == null) { call.resolve(); return; }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(android.os.VibrationEffect.createOneShot(
                duration, android.os.VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(duration);
        }
        call.resolve();
    }
}
