package com.nsu.ccfit.nsuschedule.data.controllers.user;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.nsu.ccfit.nsuschedule.data.parser.json.IntervalJson;
import com.nsu.ccfit.nsuschedule.data.parser.json.UserSettingsJson;
import com.nsu.ccfit.nsuschedule.data.wrappers.user.UserSettingsData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class UserSettingsDataController {
    private final static String USER_SETTINGS_FILE = "user_settings8.json";
    private File userSettingsFile;

    public UserSettingsDataController(File filesDir) {
        this.userSettingsFile = new File(filesDir, USER_SETTINGS_FILE);
    }

    public File getUserSettingsFile() {
        return userSettingsFile;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setScheduleUrl(String scheduleUrl) throws IOException {
        if (!userSettingsFile.exists()) {
            userSettingsFile.createNewFile();
        }
        BufferedReader reader = new BufferedReader(new FileReader(userSettingsFile));

        Gson gson = new Gson();
        UserSettingsJson userSettingsJson = gson.fromJson(reader, UserSettingsJson.class);
        reader.close();
        if (userSettingsJson == null) {
            userSettingsJson = new UserSettingsJson();
        }
        userSettingsJson.setScheduleUrl(scheduleUrl);
        String string = gson.toJson(userSettingsJson);

        BufferedWriter writer = new BufferedWriter(new FileWriter(userSettingsFile));
        writer.write(string);
        writer.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public UserSettingsData addDefaultSettings(byte[] hash) throws IOException {
        UserSettingsData userSettingsData = new UserSettingsData(
                hash
                , true
                , false
                , true
        );
        BufferedReader reader = new BufferedReader(new FileReader(userSettingsFile));
        IntervalJson intervalJson = new IntervalJson();

        intervalJson.setHash(userSettingsData.getHash());
        intervalJson.setAlarm(userSettingsData.isAlarmAllowed());
        intervalJson.setNotifications(userSettingsData.isNotificationsAllowed());
        intervalJson.setIsVisible(userSettingsData.isVisible());

        Gson gson = new Gson();
        UserSettingsJson userSettingsJson = gson.fromJson(reader, UserSettingsJson.class);
        ArrayList<IntervalJson> intervalJsonArrayList = userSettingsJson.getIntervalJsons();
        if (intervalJsonArrayList == null) {
            intervalJsonArrayList = new ArrayList<>();
        }
        intervalJsonArrayList.add(intervalJson);
        userSettingsJson.setIntervalJsons(intervalJsonArrayList);

        String string = gson.toJson(userSettingsJson);

        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(userSettingsFile));
        writer.write(string);
        writer.close();
        return userSettingsData;
    }

    public boolean changeIsVisibleByHash(byte[] hash) throws IOException {
        boolean result = false;
        BufferedReader reader = new BufferedReader(new FileReader(userSettingsFile));
        Gson gson = new Gson();
        UserSettingsJson userSettingsJson = gson.fromJson(reader, UserSettingsJson.class);
        ArrayList<IntervalJson> intervalJsonArrayList = userSettingsJson.getIntervalJsons();
        for (IntervalJson currentIntervalJson : intervalJsonArrayList) {
            if (Arrays.equals(currentIntervalJson.getHash(), hash)) {
                if (currentIntervalJson.getIsVisible()) {
                    currentIntervalJson.setIsVisible(false);
                } else {
                    currentIntervalJson.setIsVisible(true);
                }
                result = true;
                break;
            }
        }
        String string = gson.toJson(userSettingsJson);

        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(userSettingsFile));
        writer.write(string);
        writer.close();
        return result;
    }

    public boolean changeIsNotificationsAllowedByHash(byte[] hash) throws IOException {
        boolean result = false;
        BufferedReader reader = new BufferedReader(new FileReader(userSettingsFile));
        Gson gson = new Gson();
        UserSettingsJson userSettingsJson = gson.fromJson(reader, UserSettingsJson.class);
        ArrayList<IntervalJson> intervalJsonArrayList = userSettingsJson.getIntervalJsons();
        for (IntervalJson currentIntervalJson : intervalJsonArrayList) {
            if (Arrays.equals(currentIntervalJson.getHash(), hash)) {
                if (currentIntervalJson.getNotifications()) {
                    currentIntervalJson.setNotifications(false);
                } else {
                    currentIntervalJson.setNotifications(true);
                }
                result = true;
                break;
            }
        }
        String string = gson.toJson(userSettingsJson);

        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(userSettingsFile));
        writer.write(string);
        writer.close();
        return result;
    }

    public boolean changeIsAlarmsAllowedByHash(byte[] hash) throws IOException {
        boolean result = false;
        BufferedReader reader = new BufferedReader(new FileReader(userSettingsFile));
        Gson gson = new Gson();
        UserSettingsJson userSettingsJson = gson.fromJson(reader, UserSettingsJson.class);
        ArrayList<IntervalJson> intervalJsonArrayList = userSettingsJson.getIntervalJsons();
        for (IntervalJson currentIntervalJson : intervalJsonArrayList) {
            if (Arrays.equals(currentIntervalJson.getHash(), hash)) {
                if (currentIntervalJson.getAlarm()) {
                    currentIntervalJson.setAlarm(false);
                } else {
                    currentIntervalJson.setAlarm(true);
                }
                result = true;
                break;
            }
        }
        String string = gson.toJson(userSettingsJson);

        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(userSettingsFile));
        writer.write(string);
        writer.close();
        return result;
    }
}