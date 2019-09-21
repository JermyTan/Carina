package com.carina.app.validation;

import com.carina.app.constant.DayOfWeekConstant;

public class DayOfWeekValidation {

    public static DayOfWeekConstant parseDayOfWeek(String day) {
        DayOfWeekConstant parseDay;
        switch (day.toLowerCase()) {
            case "monday":
                parseDay = DayOfWeekConstant.MONDAY;
                break;
            case "tuesday":
                parseDay = DayOfWeekConstant.TUESDAY;
                break;
            case "wednesday":
                parseDay = DayOfWeekConstant.WEDNESDAY;
                break;
            case "thursday":
                parseDay = DayOfWeekConstant.THURSDAY;
                break;
            case "friday":
                parseDay = DayOfWeekConstant.FRIDAY;
                break;
            case "saturday":
                parseDay = DayOfWeekConstant.SATURDAY;
                break;
            default:
                parseDay = DayOfWeekConstant.SUNDAY;
        }
        return parseDay;
    }
}
