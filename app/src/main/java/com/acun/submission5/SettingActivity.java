package com.acun.submission5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.acun.submission5.notification.DailyReminderReceiver;
import com.acun.submission5.notification.NotificationPreference;
import com.acun.submission5.notification.ReleaseReminderReceiver;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    NotificationPreference notificationPreference;
    DailyReminderReceiver dailyReceiver;
    ReleaseReminderReceiver releaseReceiver;
    SharedPreferences spDaily, spReleaseToday;
    SharedPreferences.Editor edtDaily, edtReleaseToday;

    String DAILY_REMINDER = "daily_reminder";
    String TODAY_REMINDER = "release_today_reminder";
    String KEY_DAILY = "Daily";
    String KEY_TODAY = "Today";
    String TYPE_DAILY = "TypeDaily";
    String TYPE_TODAY = "TypeToday";

    String daily = "07:00";
    String releaseToday = "08:00";
    SwitchCompat swDaily, swReleaseToday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setTitle(getResources().getString(R.string.settings));

        dailyReceiver = new DailyReminderReceiver();
        releaseReceiver = new ReleaseReminderReceiver();
        notificationPreference = new NotificationPreference(this);
        TextView language = findViewById(R.id.tv_language);
        language.setOnClickListener(this);
        swDaily = findViewById(R.id.switch_daily);
        swReleaseToday = findViewById(R.id.switch_today);

        setNotificationPreference();
        setDaily();
        setReleaseToday();
    }

    private void setNotificationPreference() {
        spDaily = getSharedPreferences(DAILY_REMINDER, MODE_PRIVATE);
        boolean checkDailyReminder = spDaily.getBoolean(KEY_DAILY, false);
        swDaily.setChecked(checkDailyReminder);
        spReleaseToday = getSharedPreferences(TODAY_REMINDER, MODE_PRIVATE);
        boolean checkReleaseToday = spReleaseToday.getBoolean(KEY_TODAY, false);
        swReleaseToday.setChecked(checkReleaseToday);
    }

    private void dailyOn() {
        String msg = getResources().getString(R.string.daily_message);
        String dailyOn = getResources().getString(R.string.daily_on);
        notificationPreference.setDaily(daily);
        notificationPreference.setDailyMessage(msg);
        dailyReceiver.setAlarm(SettingActivity.this, TYPE_DAILY, daily, msg);

        Toast.makeText(this, dailyOn, Toast.LENGTH_SHORT).show();
    }

    private void dailyOff() {
        dailyReceiver.cancelNotif(SettingActivity.this);

        Toast.makeText(this, getResources().getString(R.string.daily_off), Toast.LENGTH_SHORT).show();
    }

    private void releaseOn() {
        String msg = getResources().getString(R.string.release_message);
        String releaseOn = getResources().getString(R.string.release_on);
        notificationPreference.setReleaseToday(releaseToday);
        notificationPreference.setReleaseTodayMessage(msg);
        releaseReceiver.setAlarm(SettingActivity.this, TYPE_TODAY, releaseToday, msg);

        Toast.makeText(this, releaseOn, Toast.LENGTH_SHORT).show();
    }

    private void releaseOff() {
        releaseReceiver.cencelNotification(SettingActivity.this);

        Toast.makeText(this, getResources().getString(R.string.release_off), Toast.LENGTH_SHORT).show();
    }

    private void setDaily() {
        edtDaily = spDaily.edit();
        swDaily.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edtDaily.putBoolean(KEY_DAILY, true);
                edtDaily.apply();
                dailyOn();
            } else {
                edtDaily.putBoolean(KEY_DAILY, false);
                edtDaily.commit();
                dailyOff();
            }
        });
    }

    private void setReleaseToday() {
        edtReleaseToday = spReleaseToday.edit();
        swReleaseToday.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edtReleaseToday.putBoolean(KEY_TODAY, true);
                edtReleaseToday.apply();
                releaseOn();
            } else {
                edtReleaseToday.putBoolean(KEY_TODAY, false);
                edtReleaseToday.commit();
                releaseOff();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_language) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
    }
}
