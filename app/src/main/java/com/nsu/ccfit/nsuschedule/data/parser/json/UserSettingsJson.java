package com.nsu.ccfit.nsuschedule.data.parser.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserSettingsJson {

    @SerializedName("scheduleUrl")
    @Expose
    private String scheduleUrl;
    @SerializedName("intervals")
    @Expose
    private ArrayList<IntervalJson> intervalJsons = null;

    public String getScheduleUrl() {
        return scheduleUrl;
    }

    public void setScheduleUrl(String scheduleUrl) {
        this.scheduleUrl = scheduleUrl;
    }

    public ArrayList<IntervalJson> getIntervalJsons() {
        return intervalJsons;
    }

    public void setIntervalJsons(ArrayList<IntervalJson> intervalJsons) {
        this.intervalJsons = intervalJsons;
    }
}