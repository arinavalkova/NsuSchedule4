package com.nsu.ccfit.nsuschedule.data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.nsu.ccfit.nsuschedule.data.parser.DataParser;
import com.nsu.ccfit.nsuschedule.data.controllers.server.NSUServerDataController;
import com.nsu.ccfit.nsuschedule.data.controllers.user.UserSettingsDataController;
import com.nsu.ccfit.nsuschedule.data.wrappers.Data;

import net.fortuna.ical4j.data.ParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataController {
    private final NSUServerDataController nsuServerDataController;
    private final UserSettingsDataController userSettingsDataController;
    private final DataParser dataParser;

    public DataController(File filesDir) {
        this.nsuServerDataController = new NSUServerDataController(filesDir);
        this.userSettingsDataController = new UserSettingsDataController(filesDir);
        this.dataParser = new DataParser(
                this.nsuServerDataController
                , this.userSettingsDataController
        );
    }

    public Boolean loadNSUServerData() throws IOException {
        String scheduleUrl = dataParser.parseScheduleUrl();
        if (scheduleUrl == null) {
            return false;
        }
        return nsuServerDataController.loadData(scheduleUrl);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Data getData() throws IOException, ParserException {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAA" + dataParser.parseScheduleUrl());
        return dataParser.getParsedData();
    }

    //---TEST
    public void printData() {
        try {
            BufferedReader fin = new BufferedReader(new FileReader(nsuServerDataController.getNSUScheduleFile()));
            String line;
            while ((line = fin.readLine()) != null) System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setScheduleUrl(String scheduleUrl) throws IOException {
        userSettingsDataController.setScheduleUrl(scheduleUrl);
    }

    public boolean changeIsVisibleByHash(byte[] hash) throws IOException {
        return userSettingsDataController.changeIsVisibleByHash(hash);
    }

    public boolean changeIsNotificationsAllowedByHash(byte[] hash) throws IOException {
        return userSettingsDataController.changeIsNotificationsAllowedByHash(hash);
    }

    public boolean changeIsAlarmsAllowedByHash(byte[] hash) throws IOException {
        return userSettingsDataController.changeIsAlarmsAllowedByHash(hash);
    }
}
