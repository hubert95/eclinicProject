/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import controllers.converters.WeekDayConverter;
import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;

/**
 *
 * @author Hubert Januszek
 */

@Entity
@TableGenerator(name = "rangeofadmission")
@NamedQueries({
    @NamedQuery(name = "RangeOfAdmission.findAll", query = "SELECT r FROM RangeOfAdmission r")
})
public class RangeOfAdmission implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Convert(converter = WeekDayConverter.class)
    @Enumerated(EnumType.STRING)
    private WeekDay weekDay = WeekDay.MONDAY;
    @Column(length = 255)
    private LocalTime beginOfRange;
    @Column(length = 255)
    private LocalTime endOfRange;
    private int lengthOfVisit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public LocalTime getBeginOfRange() {
        return beginOfRange;
    }

    public void setBeginOfRange(LocalTime beginOfRange) {
        this.beginOfRange = beginOfRange;
    }

    public LocalTime getEndOfRange() {
        return endOfRange;
    }

    public void setEndOfRange(LocalTime endOfRange) {
        this.endOfRange = endOfRange;
    }

    public int getLengthOfVisit() {
        return lengthOfVisit;
    }

    public void setLengthOfVisit(int lengthOfVisit) {
        this.lengthOfVisit = lengthOfVisit;
    }

}
