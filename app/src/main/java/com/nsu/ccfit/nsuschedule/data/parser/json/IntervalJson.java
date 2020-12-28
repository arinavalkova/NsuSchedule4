package com.nsu.ccfit.nsuschedule.data.parser.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntervalJson {

    @SerializedName("hash")
    private byte[] hash;
    @SerializedName("notifications")
    private Boolean notifications;
    @SerializedName("alarm")
    private Boolean alarm;
    @SerializedName("isVisible")
    private Boolean isVisible;

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public Boolean getNotifications() {
        return notifications;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    public Boolean getAlarm() {
        return alarm;
    }

    public void setAlarm(Boolean alarm) {
        this.alarm = alarm;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }
}

