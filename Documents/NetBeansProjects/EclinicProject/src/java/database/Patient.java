/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import controllers.peselUtils.PeselUtils;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
})
public class Patient extends User implements Serializable {
    
    private String pesel;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfBirth;
    @Transient
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

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
}
