package com.coderder.colorMeeting.service;

public enum Weekday {
    MON(0),
    TUE(1),
    WED(2),
    THU(3),
    FRI(4),
    SAT(5),
    SUN(6);

    private final int value;
    private Weekday(int value){
        this.value = value;
    }
}
