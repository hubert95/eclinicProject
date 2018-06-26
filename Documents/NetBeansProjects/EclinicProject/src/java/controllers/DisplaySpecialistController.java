/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.security.MD5;
import database.RangeOfAdmission;
import database.Specialist;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "displaySpecialistController")
@RequestScoped
public class DisplaySpecialistController {

    private Specialist selectedSpecialist;
    
    public DisplaySpecialistController() {
    }

    public Specialist getSelectedSpecialist() {
        return selectedSpecialist;
    }

    public void setSelectedSpecialist(Specialist selectedSpecialist){
        this.selectedSpecialist = selectedSpecialist;
    }
    
    public List<Specialist> getSpecialists() {
        List<Specialist> specialists = null;
        EntityManager em = DBManager.getManager().createEntityManager();

        try {
            em.getTransaction().begin();
            specialists = (List<Specialist>) em.createNamedQuery("Specialist.findAll").getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        
        return specialists;
    }

    public List<RangeOfAdmission> getRangesBySpecialist(Long id){
        EntityManager em = DBManager.getManager().createEntityManager();
        Specialist specialist = null;
        
        try {
            em.getTransaction().begin();
            specialist = (Specialist) em.createNamedQuery("Specialist.findById").setParameter("id", id).getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return specialist.getRangeOfAdmissions();
    }
}
