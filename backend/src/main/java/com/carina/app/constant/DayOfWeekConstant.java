package com.carina.app.constant;

public enum DayOfWeekConstant {

    MONDAY("monday"),
    TUESDAY("tuesday"),
    WEDNESDAY("wednesday"),
    THURSDAY("thursday"),
    FRIDAY("friday"),
    SATURDAY("saturday"),
    SUNDAY("sunday");

    private final String day;

    DayOfWeekConstant(String day) {
        this.day = day;
    }

    @Override public String toString() {
        return day;
    }

}
