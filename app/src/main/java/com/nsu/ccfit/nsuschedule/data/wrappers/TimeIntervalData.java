package com.nsu.ccfit.nsuschedule.data.wrappers;

import com.nsu.ccfit.nsuschedule.data.wrappers.server.NSUServerData;
import com.nsu.ccfit.nsuschedule.data.wrappers.user.UserSettingsData;
import com.nsu.ccfit.nsuschedule.scheduleabstract.Parity;
import com.nsu.ccfit.nsuschedule.scheduleabstract.ScheduleItem;

public class TimeIntervalData implements ScheduleItem {
    private final NSUServerData nsuServerData;
    private final UserSettingsData userSettingsData;

    public TimeIntervalData(NSUServerData nsuServerData, UserSettingsData userSettingsData) {
        this.nsuServerData = nsuServerData;
        this.userSettingsData = userSettingsData;
    }

    public NSUServerData getNsuServerData() {
        return nsuServerData;
    }

    public UserSettingsData getUserSettingsData() {
        return userSettingsData;
    }

    @Override
    public String toString() {
        StringBuilder dataString = new StringBuilder();
        dataString.append(nsuServerData.getDescription()).append(" \n");
        dataString.append(nsuServerData.getLocation()).append(" \n");
        dataString.append(nsuServerData.getSummary()).append(" \n");
        dataString.append(nsuServerData.getInterval()).append("\n");
        dataString.append(nsuServerData.getWeekDay()).append(" \n");
        dataString.append(nsuServerData.getStartTime()).append(" \n");
        dataString.append(nsuServerData.getEndTime()).append(" \n");

        dataString.append(userSettingsData.isAlarmAllowed()).append(" \n");
        dataString.append(userSettingsData.isNotificationsAllowed()).append(" \n");
        dataString.append(userSettingsData.isVisible()).append(" \n");
        return dataString.toString();
    }

    @Override
    public String getDescription() {
        return nsuServerData.getDescription();
    }

    @Override
    public String getLocation() {
        return nsuServerData.getLocation();
    }

    @Override
    public String getSummary() {
        return nsuServerData.getSummary();
    }

    @Override
    public String getStartTime() {
        return nsuServerData.getStartTime().toString();
    }

    @Override
    public String getEndTime() {
        return nsuServerData.getEndTime().toString();
    }

    @Override
    public Parity getParity() {
        int p = nsuServerData.getInterval();
        if (p == 1) return Parity.ALL;
        return Parity.EVEN;
    }

    @Override
    public boolean isVisible() {
        return userSettingsData.isVisible();
    }
}
