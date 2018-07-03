/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import controllers.DBManager;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Hubert Januszek
 */
@Entity
@TableGenerator(name = "patientcard")
public class PatientCard implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Patient patient;
    @OneToOne(cascade = CascadeType.MERGE)
    private Clinic clinic;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Visit> visits;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes;
    
    public PatientCard() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Clinic getClinic() {
        return clinic;
    }
    
    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public void setClinic(String name) {
        Clinic c = null;
        
        EntityManager em = DBManager.getManager().createEntityManager();
        try {
            em.getTransaction().begin();
            c = (Clinic) em.createNamedQuery("Clinic.findByName").setParameter("name", name).getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        
        this.clinic = c;
    }
    
    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
    
}
