/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 *
 * @author Hubert Januszek
 */

@Entity
@TableGenerator(name = "rangeofadmission")
public class RangeOfAdmission implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private WeekDay weekDay;
    @Column(columnDefinition = "varchar(8)")
    private LocalTime beginOfRange;
    @Column(columnDefinition = "varchar(8)")
    private LocalTime endOfRange;

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
    
    
    
}
