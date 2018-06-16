/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.converters;

import database.WeekDay;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Admin
 */
@Converter
public class WeekDayConverter implements AttributeConverter<WeekDay, String> {

    @Override
    public String convertToDatabaseColumn(WeekDay attribute) {
        switch (attribute) {
            case MONDAY:
                return "Poniedziałek";
            case TUESDAY:
                return "Wtorek";
            case WEDNESDAY:
                return "Środa";
            case THURSDAY:
                return "Czwartek";
            case FRIDAY:
                return "Piątek";
            case SATURDAY:
                return "Sobota";
            case SUNDAY:
                return "Niedziela";
        }
        return null;
    }

    @Override
    public WeekDay convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "Poniedziałek":
                return WeekDay.MONDAY;
                case "Wtorek":
                return WeekDay.TUESDAY;
                case "Środa":
                return WeekDay.WEDNESDAY;
                case "Czwarted":
                return WeekDay.THURSDAY;
                case "Piątek":
                return WeekDay.FRIDAY;
                case "Sobota":
                return WeekDay.SATURDAY;
                case "Niedziela":
                return WeekDay.SUNDAY;
        }
        return null;
    }

}
