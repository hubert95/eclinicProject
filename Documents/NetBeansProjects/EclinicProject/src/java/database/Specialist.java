/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import controllers.DBManager;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

    @OneToOne(cascade = CascadeType.MERGE)
    private Specialization specialization;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "specialist")
    private List<RangeOfAdmission> rangeOfAdmissions;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist", fetch = FetchType.LAZY)
    private List<Visit> visits;

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String name) {
        Specialization specialization = null;
        EntityManager em = DBManager.getManager().createEntityManager();
        
        try {
            em.getTransaction().begin();
            specialization = (Specialization) em.createNamedQuery("Specialization.findByName").setParameter("name", name).getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        this.specialization = specialization;
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
