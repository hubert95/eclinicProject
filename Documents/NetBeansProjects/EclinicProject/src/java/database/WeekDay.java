/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Hubert Januszek
 */

public enum WeekDay {
    MONDAY("Poniedziałek"),
    TUESDAY("Wtorek"),
    WEDNESDAY("Środa"),
    THURSDAY("Czwartek"),
    FRIDAY("Piątek"),
    SATURDAY("Sobota"),
    SUNDAY("Niedziela");
    
    private final String text;
    
    WeekDay(final String text){
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }
    
}
