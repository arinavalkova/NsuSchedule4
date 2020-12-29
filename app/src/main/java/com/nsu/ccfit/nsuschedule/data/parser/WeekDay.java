package com.nsu.ccfit.nsuschedule.data.parser;

public enum WeekDay {
    MO(0), TU(1), WE(2), TH(3), FR(4), SA(5), SU(6);

    private final int val;

    WeekDay(int i) {
        val = i;
    }

    static public WeekDay getByIndex(int i) {
        return WeekDay.values()[i];
    }
}
