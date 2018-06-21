/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.Specialist;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "generateDatesController")
@RequestScoped
public class GenerateDatesController {

    private List<Specialist> selectedSpecialists;
    
            
            
    public GenerateDatesController() {
    }

    public List<Specialist> getSelectedSpecialists() {
        return selectedSpecialists;
    }

    public void setSelectedSpecialists(List<Specialist> selectedSpecialists) {
        this.selectedSpecialists = selectedSpecialists;
    }
    
    public void confirmMessage() {
        MessageController.addMessage("Usunięto wybrany przedział.", "Pomyśle wykonanie operacji.", FacesMessage.SEVERITY_INFO);
    }
    
    public List<Specialist> getSpecialists() {
        List<Specialist> specialists = null;
        EntityManager em = DBManager.getManager().createEntityManager();

        try {
            em.getTransaction().begin();
            specialists = (List<Specialist>) em.createNamedQuery("Specialist.findAll").getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
        }
        return specialists;
    }
    
    public void generate(){
        
    }
    
}
