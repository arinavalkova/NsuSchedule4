package com.nsu.ccfit.nsuschedule.data.wrappers.server;

import com.nsu.ccfit.nsuschedule.data.common.Hasher;
import com.nsu.ccfit.nsuschedule.data.parser.WeekDay;

import java.util.Arrays;

public class NSUServerData {
    private final byte[] hash;
    private final String location;
    private final String description;
    private final String summary;
    private final WeekDay weekDay;
    private final int interval;
    private final Time startTime;
    private final Time endTime;
    private boolean isRecentlyUpdated;

    public NSUServerData(String location
            , String description
            , String summary
            , WeekDay weekDay
            , int interval
            , Time startTime
            , Time endTime) {
        this.location = location;
        this.description = description;
        this.summary = summary;
        this.weekDay = weekDay;
        this.interval = interval;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isRecentlyUpdated = true;
        this.hash = new Hasher().getStringHash(location + description + startTime + endTime + summary + weekDay + interval);
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getSummary() {
        return summary;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public int getInterval() {
        return interval;
    }

    public byte[] getHash() {
        return hash;
    }

    public boolean isRecentlyUpdated() {
        return isRecentlyUpdated;
    }

    public void setRecentlyUpdated(boolean isRecentlyUpdated) {
        this.isRecentlyUpdated = isRecentlyUpdated;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "NSUServerData{" +
                "hash=" + Arrays.toString(hash) +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", summary='" + summary + '\'' +
                ", weekDay=" + weekDay +
                ", interval=" + interval +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isRecentlyUpdated=" + isRecentlyUpdated +
                '}';
    }
}
