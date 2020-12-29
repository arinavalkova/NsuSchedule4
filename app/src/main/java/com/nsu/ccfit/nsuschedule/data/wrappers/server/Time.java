package com.nsu.ccfit.nsuschedule.data.wrappers.server;

public class Time {
    private final String hours;
    private final String minutes;

    public Time(String hours, String minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public String getHours() {
        return hours;
    }

    public String getMinutes() {
        return minutes;
    }

    @Override
    public String toString() {
        return hours + ":" + minutes;
    }
}
