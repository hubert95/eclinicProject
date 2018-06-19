/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

/**
 *
 * @author Hubert Januszek
 */
@Entity
@TableGenerator(name = "specialist")
@NamedQueries({
    @NamedQuery(name = "Specialist.findAll", query = "SELECT s FROM Specialist s"),
    @NamedQuery(name = "Specialist.findById", query = "SELECT s FROM Specialist s WHERE s.id = :id")
})
public class Specialist extends User implements Serializable {

    @OneToMany(mappedBy = "specialist")
    private List<Specialization> specializations;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "specialist")
    private List<RangeOfAdmission> rangeOfAdmissions;
    
    @OneToMany(mappedBy = "specialist", cascade = CascadeType.PERSIST)
    private List<Visit> visits;


    public List<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<Specialization> specializations) {
        this.specializations = specializations;
    }

    public List<RangeOfAdmission> getRangeOfAdmissions() {
        return rangeOfAdmissions;
    }

    public void setRangeOfAdmissions(List<RangeOfAdmission> rangeOfAdmissions) {
        this.rangeOfAdmissions = rangeOfAdmissions;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }


}
