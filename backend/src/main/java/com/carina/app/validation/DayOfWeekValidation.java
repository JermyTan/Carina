package com.carina.app.validation;

import com.carina.app.constant.DayOfWeekConstant;
import com.carina.app.exception.InvalidDayException;

public class DayOfWeekValidation {

    /**
     * Returns the day of the week in DayOfWeekConstant.
     * @param day day of the week from input.
     * @return day of the week in DayOfWeekConstant.
     * @throws InvalidDayException invalid day of week.
     */
    public static DayOfWeekConstant parseDayOfWeek(String day) throws InvalidDayException {
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
        case "sunday":
            parseDay = DayOfWeekConstant.SUNDAY;
            break;
        default:
            throw new InvalidDayException(day + "is an invalid day");
        }
        return parseDay;
    }
}
