/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.Patient;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author Hubert Januszek
 */
@ManagedBean(name = "managePatientController")
@RequestScoped
public class ManagePatientController {

    Patient selectedPatient;
    
    private int tabNumber;
    private boolean tab;
    
    public ManagePatientController() {
    }
    
    public Patient getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient = selectedPatient;
    }

    public int getTabNumber() {
        return tabNumber;
    }

    public void setTabNumber(int tabNumber) {
        this.tabNumber = tabNumber;
    }

    public boolean isTab() {
        return tab;
    }

    public void setTab(boolean tab) {
        this.tab = tab;
    }
    
    public List<Patient> getPatients() {
        List<Patient> patients = null;
        EntityManager em = DBManager.getManager().createEntityManager();

        try {
            em.getTransaction().begin();
            patients = (List<Patient>) em.createNamedQuery("Patient.findAll").getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
        }
        return patients;
    }
    
    public void reserve(){
        
    }

    public void onTabChange() throws IOException {
        if (selectedPatient == null) {
            MessageController.addMessage("Nie zaznaczono pacjenta.", "Proszę wybrać pacjenta.", FacesMessage.SEVERITY_ERROR);
            return;
        }
        tab = !tab;
        if (tabNumber == 0) {
            tabNumber = 1;
        } else {
            tabNumber = 0;
        }
    }
    
}
