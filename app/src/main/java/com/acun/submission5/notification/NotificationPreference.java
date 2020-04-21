package com.acun.submission5.notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class NotificationPreference {
    private static final String PREFERENCE = "preference";
    private static final String KEY_DAILY = "Daily";
    private static final String KEY_TODAY = "Today";
    private static final String DAILY_MESSAGE = "dailyMessage";
    private static final String RELEASE_TODAY_MESSAGE = "todayMessage";
    private SharedPreferences.Editor spEditor;

    @SuppressLint("CommitPrefEdits")
    public NotificationPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        spEditor = sharedPreferences.edit();
    }

    public void setReleaseToday(String time) {
        spEditor.putString(KEY_TODAY, time);
        spEditor.commit();
    }

    public void setReleaseTodayMessage(String msg) {
        spEditor.putString(RELEASE_TODAY_MESSAGE, msg);
    }

    public void setDaily(String time) {
        spEditor.putString(KEY_DAILY, time);
        spEditor.commit();
    }

    public void setDailyMessage(String msg) {
        spEditor.putString(DAILY_MESSAGE, msg);
    }
}
