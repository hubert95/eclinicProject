/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.Patient;
import database.Visit;
import database.VisitState;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "ownerVisitsController")
@RequestScoped
public class OwnerVisitsController {

    /**
     * Creates a new instance of OwnerVisitsController
     */
    public OwnerVisitsController() {
    }
    
    public List<Visit> getFutureVisits(){
        Long id = SessionUtilsController.getOwnerId();
        Patient patient = null;
        List<Visit> allVisits = null;
        EntityManager em = DBManager.getManager().createEntityManager();
        
        try {
            em.getTransaction().begin();
            patient = (Patient) em.createNamedQuery("Patient.findById").setParameter("id", id).getSingleResult();
            allVisits = (List<Visit>) em.createNamedQuery("Visit.findUnpayedAndPayed").getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return allVisits;
    }
    
    public List<Visit> getPastVisits() {
        Long id = SessionUtilsController.getOwnerId();
        Patient patient = null;
        List<Visit> visitsPast = null;
        EntityManager em = DBManager.getManager().createEntityManager();

        try {
            em.getTransaction().begin();
            patient = (Patient) em.createNamedQuery("Patient.findById").setParameter("id", id).getSingleResult();
            visitsPast = (List<Visit>) em.createNamedQuery("Visit.findByState").setParameter("state", VisitState.FINISHED).getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return visitsPast;
    }
    
}
