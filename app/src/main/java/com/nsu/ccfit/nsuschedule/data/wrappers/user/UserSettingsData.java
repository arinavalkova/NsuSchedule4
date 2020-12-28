package com.nsu.ccfit.nsuschedule.data.wrappers.user;

public class UserSettingsData {
    private final byte[] hash;
    private final boolean isNotificationsAllowed;
    private final boolean isAlarmAllowed;
    private final boolean isVisible;

    public UserSettingsData(byte[] hash, boolean isNotificationsAllowed, boolean isAlarmAllowed, boolean isVisible) {
        this.hash = hash;
        this.isNotificationsAllowed = isNotificationsAllowed;
        this.isAlarmAllowed = isAlarmAllowed;
        this.isVisible = isVisible;
    }

    public byte[] getHash() {
        return hash;
    }

    public boolean isNotificationsAllowed() {
        return isNotificationsAllowed;
    }

    public boolean isAlarmAllowed() {
        return isAlarmAllowed;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
