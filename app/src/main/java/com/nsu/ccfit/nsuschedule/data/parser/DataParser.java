package com.nsu.ccfit.nsuschedule.data.parser;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.nsu.ccfit.nsuschedule.data.parser.json.IntervalJson;
import com.nsu.ccfit.nsuschedule.data.parser.json.UserSettingsJson;
import com.nsu.ccfit.nsuschedule.data.wrappers.Data;
import com.nsu.ccfit.nsuschedule.data.wrappers.server.NSUServerData;
import com.nsu.ccfit.nsuschedule.data.wrappers.TimeIntervalData;
import com.nsu.ccfit.nsuschedule.data.wrappers.server.Time;
import com.nsu.ccfit.nsuschedule.data.wrappers.user.UserSettingsData;
import com.nsu.ccfit.nsuschedule.data.controllers.server.NSUServerDataController;
import com.nsu.ccfit.nsuschedule.data.controllers.user.UserSettingsDataController;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class DataParser {
    private static final String SEMICOLON = ";";
    private static final String EQUALLY = "=";
    private static final String T = "T";

    private final NSUServerDataController nsuServerDataController;
    private final UserSettingsDataController userSettingsDataController;

    public DataParser(NSUServerDataController nsuServerDataController
            , UserSettingsDataController userSettingsDataController) {
        this.nsuServerDataController = nsuServerDataController;
        this.userSettingsDataController = userSettingsDataController;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Data getParsedData() throws IOException, ParserException {
        Data data = new Data();
        ArrayList<NSUServerData> nsuServerDataArrayList = parseNSUServerData(
                nsuServerDataController.getNSUScheduleFile()
        );

        File backUpFile = nsuServerDataController.getBackUpScheduleFile();
        if (backUpFile.length() != 0) {
            ArrayList<NSUServerData> backUpArrayList = parseNSUServerData(
                    backUpFile
            );
            for (NSUServerData currentNSUServerData : nsuServerDataArrayList) {
                for (NSUServerData currentBackUpData : backUpArrayList) {
                    if (Arrays.equals(currentBackUpData.getHash(), currentNSUServerData.getHash())) {
                        currentNSUServerData.setRecentlyUpdated(false);
                        break;
                    }
                }
            }

        }
        ArrayList<UserSettingsData> userSettingsDataArrayList = parseUserSettingsData();
        for (NSUServerData currentNSUServerData : nsuServerDataArrayList) {
            boolean noSettings = true;
            if (userSettingsDataArrayList != null) {
                for (UserSettingsData currentSettingsData : userSettingsDataArrayList) {
                    if (Arrays.equals(currentNSUServerData.getHash(), currentSettingsData.getHash())) {
                        System.out.println("FOUND EQUALS");
                        data.addTimeIntervalData(new TimeIntervalData(currentNSUServerData, currentSettingsData));
                        noSettings = false;
                        break;
                    }
                }
            }
            if (noSettings) {
                System.out.println("NO SETTINGS");
                UserSettingsData userSettingsData =
                        userSettingsDataController.addDefaultSettings(currentNSUServerData.getHash());
                data.addTimeIntervalData(new TimeIntervalData(currentNSUServerData, userSettingsData));
            }
        }
        return data;
    }

    public String parseScheduleUrl() throws IOException {
        if (!userSettingsDataController.getUserSettingsFile().exists()) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new FileReader(userSettingsDataController.getUserSettingsFile()));
        Gson gson = new Gson();
        UserSettingsJson userSettingsJson = gson.fromJson(reader, UserSettingsJson.class);
        String scheduleUrl = userSettingsJson.getScheduleUrl();
        reader.close();
        return scheduleUrl;
    }

    private ArrayList<NSUServerData> parseNSUServerData(File nsuScheduleFile) throws IOException, ParserException {
        ArrayList<NSUServerData> nsuServerDataArrayList = new ArrayList<>();
        FileInputStream fin = new FileInputStream(nsuScheduleFile);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);
        for (Iterator i = calendar.getComponents().iterator(); i.hasNext(); ) {
            Component component = (Component) i.next();
            String location = null;
            String description = null;
            String summary = null;
            int interval = 0;
            WeekDay weekDay = null;
            Time startTime = null;
            Time endTime = null;

            for (Iterator j = component.getProperties().iterator(); j.hasNext(); ) {
                Property property = (Property) j.next();
                if (property.getName().equals(ParseValue.LOCATION.toString())) {
                    location = property.getValue();
                }
                if (property.getName().equals(ParseValue.DESCRIPTION.toString())) {
                    description = property.getValue();
                }
                if (property.getName().equals(ParseValue.SUMMARY.toString())) {
                    summary = property.getValue();
                }
                if (property.getName().equals(ParseValue.RRULE.toString())) {
                    String[] parameters = property.getValue().split(SEMICOLON);
                    for (String currentParameter : parameters) {
                        String[] currentParameterValues = currentParameter.split(EQUALLY);
                        if (currentParameterValues[0].equals(ParseValue.INTERVAL.toString())) {
                            interval = Integer.parseInt(currentParameterValues[1]);
                        }
                        if (currentParameterValues[0].equals(ParseValue.BYDAY.toString())) {
                            weekDay = WeekDay.valueOf(currentParameterValues[1]);
                        }
                    }
                }
                if (property.getName().equals(ParseValue.DTSTART.toString())) {
                    startTime = parseTimeLine(property.getValue());
                }
                if (property.getName().equals(ParseValue.DTEND.toString())) {
                    endTime = parseTimeLine(property.getValue());
                }
            }
            if (location == null
                    || description == null
                    || summary == null
                    || weekDay == null
                    || interval == 0) {
                continue;
            }
            nsuServerDataArrayList.add(new NSUServerData(location
                            , description
                            , summary
                            , weekDay
                            , interval
                            , startTime
                            , endTime
                    )
            );
        }
        return nsuServerDataArrayList;
    }

    private ArrayList<UserSettingsData> parseUserSettingsData() throws IOException {
        ArrayList<UserSettingsData> userSettingsDataArrayList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(userSettingsDataController.getUserSettingsFile()));
        Gson gson = new Gson();
        UserSettingsJson userSettingsJson = gson.fromJson(reader, UserSettingsJson.class);
        reader.close();
        ArrayList<IntervalJson> userSettingsJsonIntervalJsons = userSettingsJson.getIntervalJsons();
        if (userSettingsJsonIntervalJsons == null) {
            System.out.println("No saved settings");
            return null;
        }
        for (IntervalJson currentIntervalJson : userSettingsJsonIntervalJsons) {
            UserSettingsData currentUserSettingsData = new UserSettingsData(
                    currentIntervalJson.getHash()
                    , currentIntervalJson.getNotifications()
                    , currentIntervalJson.getAlarm()
                    , currentIntervalJson.getIsVisible()
            );
            userSettingsDataArrayList.add(currentUserSettingsData);
        }
        return userSettingsDataArrayList;
    }

    public Time parseTimeLine(String timeLine) {
        String[] parameters = timeLine.split(T);
        int hour =
                Character.digit(parameters[1].charAt(0), 10) * 10
                        +
                        Character.digit(parameters[1].charAt(1), 10);
        int minutes =
                Character.digit(parameters[1].charAt(2), 10) * 10
                        +
                        Character.digit(parameters[1].charAt(3), 10);
        return new Time(hour, minutes);
    }
}
