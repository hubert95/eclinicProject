/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.Visit;
import database.VisitState;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author Hubert Januszek
 */
@ManagedBean(name = "freeVisitController")
@RequestScoped
public class FreeVisitController {

    public FreeVisitController() {
    }
    
    public List<Visit> getFreeVisits(){
        List<Visit> visits = null;
        EntityManager em = DBManager.getManager().createEntityManager();
        
        try {
            em.getTransaction().begin();
            visits = (List<Visit>) em.createNamedQuery("Visit.findAllUnreserved").setParameter("state", VisitState.UNRESERVED).getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            em.close();
        }

        return visits;
    }
     
}
