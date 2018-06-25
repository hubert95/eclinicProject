/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.Specialization;
import database.Visit;
import database.VisitState;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author Hubert Januszek
 */
@ManagedBean(name = "freeVisitController")
@RequestScoped
public class FreeVisitController {
    
    private List<Visit> filteredVisits;
    private Long visitId;

    public FreeVisitController() {
    }

    public List<Visit> getFilteredVisits() {
        return filteredVisits;
    }

    public void setFilteredVisits(List<Visit> filteredVisits) {
        this.filteredVisits = filteredVisits;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    public Long getVisitId() {
        return visitId;
    }
    
    public List<String> getNamesOfSpecialization(){
        EntityManager em = DBManager.getManager().createEntityManager();
        List<Specialization> specializations = null;
        
        try {
            em.getTransaction().begin();
            specializations = (List<Specialization>) em.createNamedQuery("Specialization.findAll").getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        
        if(specializations == null) return null;
        
        List<String> names = new ArrayList<>();

        for(Specialization s : specializations){
            names.add(s.getName());
        }
        
        return names;
    }
    
    public void notLoggedForReservation(){
        MessageController.addMessage("Nie jesteś zalogowany.", "By móc zarezerwować wizytę musisz być zalogowany", FacesMessage.SEVERITY_INFO);
    }
    
    public List<Visit> getFreeVisits(){
        List<Visit> visits = null;
        EntityManager em = DBManager.getManager().createEntityManager();
        
        try {
            em.getTransaction().begin();
            visits = (List<Visit>) em.createNamedQuery("Visit.findByState").setParameter("state", VisitState.UNRESERVED).getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            em.close();
        }

        return visits;
    }
     
}
