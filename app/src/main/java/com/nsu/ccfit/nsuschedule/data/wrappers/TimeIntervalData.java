package com.nsu.ccfit.nsuschedule.data.wrappers;

import com.nsu.ccfit.nsuschedule.data.wrappers.server.NSUServerData;
import com.nsu.ccfit.nsuschedule.data.wrappers.user.UserSettingsData;

public class TimeIntervalData {
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
}
