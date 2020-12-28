package com.nsu.ccfit.nsuschedule.data.wrappers;

import com.nsu.ccfit.nsuschedule.data.parser.WeekDay;

import java.util.ArrayList;

public class Data {
    private ArrayList<TimeIntervalData> dataList;

    public Data() {
        this.dataList = new ArrayList<>();
    }

    public ArrayList<TimeIntervalData> getWeekDayTimeIntervalDataList(WeekDay weekDay) {
        ArrayList<TimeIntervalData> weekDayTimeIntervalDataList = new ArrayList<>();
        for (TimeIntervalData currentTimeIntervalData : dataList) {
            if (currentTimeIntervalData.getNsuServerData().getWeekDay().equals(weekDay))
                weekDayTimeIntervalDataList.add(currentTimeIntervalData);
        }
        return weekDayTimeIntervalDataList;
    }

    public void addTimeIntervalData(TimeIntervalData timeIntervalData) {
        this.dataList.add(timeIntervalData);
    }

    @Override
    public String toString() {
        StringBuilder dataString = new StringBuilder();
        for (TimeIntervalData currentTimeIntervalData : dataList) {
           dataString.append(currentTimeIntervalData.toString());
        }
        return dataString.toString();
    }
}
