/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.dateUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Hubert Januszek
 */
@ManagedBean
@RequestScoped
public class DateUtils {
    
    public DateUtils() {
    }
    
    public static List<LocalDate> weeksInCalendar(YearMonth month) {
        List<LocalDate> firstDaysOfWeeks = new ArrayList<>();
        for (LocalDate day = firstDayOfCalendar(month); stillInCalendar(month, day); day = day
                .plusWeeks(1)) {
            firstDaysOfWeeks.add(day);
        }
        return firstDaysOfWeeks;
    }

    private static LocalDate firstDayOfCalendar(YearMonth month) {
        DayOfWeek FIRST_DAY_OF_WEEK = DayOfWeek.TUESDAY;
        return month.atDay(1).with(FIRST_DAY_OF_WEEK);
    }

    private static boolean stillInCalendar(YearMonth yearMonth, LocalDate day) {
        return !day.isAfter(yearMonth.atEndOfMonth());
    }
    
}
