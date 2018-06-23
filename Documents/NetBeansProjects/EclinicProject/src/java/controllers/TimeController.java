/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.RangeOfAdmission;
import database.WeekDay;
import java.time.LocalTime;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class TimeController {

    /**
     * Creates a new instance of TimeController
     */
    public TimeController() {
    }

    public static boolean isInRange(LocalTime start, LocalTime end, LocalTime time) {
        if (start.isAfter(end)) {
            return !time.isBefore(start) || !time.isAfter(end);
        } else {
            return !time.isBefore(start) && !time.isAfter(end);
        }
    }

    public static boolean checkTimesInRanges(List<RangeOfAdmission> roa, LocalTime timeBegin, LocalTime timeEnd, WeekDay wd) {
        for (RangeOfAdmission r : roa) {
            if (r.getWeekDay().equals(wd)) {
                if (r.getEndOfRange().equals(timeBegin)) {
                    return true;
                } else {
                    if (isInRange(r.getBeginOfRange(), r.getEndOfRange(), timeBegin) || isInRange(r.getBeginOfRange(), r.getEndOfRange(), timeEnd)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

}
