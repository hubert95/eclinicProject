/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import controllers.converters.VisitStateConverter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Admin
 */

//dokończyć

@Entity
@TableGenerator(name = "visit")
public class Visit implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    Patient patient;
    @ManyToOne
    Specialist specialist;
    @Temporal(TemporalType.DATE)
    private Date dateOfVisit;
    @Column(length = 255)
    private LocalTime beginOfTheVisit;
    private int lengthOfVisit;
    private float price;
    @Column
    @Enumerated(EnumType.STRING)
    private VisitState state = VisitState.UNRESERVED;

    public Visit() {
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Date getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(Date dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }
    
    public LocalTime getBeginOfTheVisit() {
        return beginOfTheVisit;
    }

    public void setBeginOfTheVisit(LocalTime beginOfTheVisit) {
        this.beginOfTheVisit = beginOfTheVisit;
    }

    public int getLengthOfVisit() {
        return lengthOfVisit;
    }

    public void setLengthOfVisit(int lengthOfVisit) {
        this.lengthOfVisit = lengthOfVisit;
    }
    

    public VisitState getState() {
        return state;
    }

    public void setState(VisitState state) {
        this.state = state;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
