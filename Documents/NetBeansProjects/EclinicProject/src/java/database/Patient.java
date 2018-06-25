/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import controllers.DBManager;
import controllers.MessageController;
import controllers.peselUtils.PeselUtils;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 *
 * @author Hubert Januszek
 */

@Entity
@TableGenerator(name = "patient")
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p from Patient p"),
    @NamedQuery(name = "Patient.findByPesel", query = "SELECT p from Patient p WHERE p.pesel = :pesel")
})
public class Patient extends User implements Serializable {
    
    private String pesel;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfBirth;
    @Transient
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    @OneToMany(mappedBy = "patient", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PatientCard> patientCards;
   

    public Patient() {
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) throws ParseException {
        this.pesel = pesel;
        setDateOfBirth();
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth() throws ParseException {
        this.dateOfBirth = sdf.parse(PeselUtils.getBirthDayFromPesel(pesel));;
    }
    
    public String dateOfBirthToString(){
        return sdf.format(dateOfBirth);
    }

    public List<PatientCard> getPatientCards() {
        return patientCards;
    }

    public void setPatientCards(List<PatientCard> patientCards) {
        this.patientCards = patientCards;
    }

    
}
