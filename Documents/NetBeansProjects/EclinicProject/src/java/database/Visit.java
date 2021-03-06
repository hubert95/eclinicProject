/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import controllers.converters.VisitStateConverter;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Hubert Januszek
 */
@Entity
@TableGenerator(name = "visit")
@NamedQueries({
    @NamedQuery(name = "Visit.findAll", query = "SELECT v FROM Visit v")
    ,
    @NamedQuery(name = "Visit.findById", query = "SELECT v FROM Visit v WHERE v.id = :id")
    ,
    @NamedQuery(name = "Visit.findByState", query = "SELECT v FROM Visit v WHERE v.state = :state")
    ,
    @NamedQuery(name = "Visit.findAllFreeDates", query = "SELECT v FROM Visit v WHERE v.state = database.VisitState.UNRESERVED AND v.dateOfVisit > :date")
    ,
    @NamedQuery(name = "Visit.findUnpayedAndPayed", query = "SELECT v FROM Visit v WHERE v.state = database.VisitState.UNPAYED OR v.state = database.VisitState.PAYED")
    ,  
    @NamedQuery(name = "Visit.findForTimetable", query = "SELECT v FROM Visit v WHERE v.specialist.id = :specialistId AND v.dateOfVisit = :dateOfVisit AND (v.state = database.VisitState.UNPAYED OR v.state = database.VisitState.PAYED)")
})
public class Visit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    Specialist specialist;
    @Temporal(TemporalType.DATE)
    private Date dateOfVisit;
    @Column(length = 255)
    private LocalTime beginOfTheVisit;
    private int lengthOfVisit;
    private float price;
    @Column
    @Convert(converter = VisitStateConverter.class)
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

    public String getStringStateName() {
        switch (state) {
            case UNRESERVED:
                return "Niezarezerwowana";
            case UNPAYED:
                return "Nieopłacona";
            case PAYED:
                return "Opłacona";
            case CANCELEDBYCLINIC:
                return "Anulowana przez przychodnię";
            case CANCELEDBYPATIENT:
                return "Anulowana przez pacjenta";
            case FINISHED:
                return "Zakończona";
        }
        return null;
    }
}
