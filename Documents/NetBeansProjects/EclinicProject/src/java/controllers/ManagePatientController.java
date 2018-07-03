/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.Patient;
import database.PatientCard;
import database.Visit;
import database.VisitState;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author Hubert Januszek
 */
@ManagedBean(name = "managePatientController")
@SessionScoped
public class ManagePatientController {

    private Patient selectedPatient;
    private List<Visit> filteredVisits;

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

    public List<Visit> getFilteredVisits() {
        return filteredVisits;
    }

    public void setFilteredVisits(List<Visit> filteredVisits) {
        this.filteredVisits = filteredVisits;
    }

    public List<Patient> getPatients() {
        List<Patient> patients = null;
        EntityManager em = DBManager.getManager().createEntityManager();

        try {
            em.getTransaction().begin();
            patients = (List<Patient>) em.createNamedQuery("Patient.findAll").getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return patients;
    }

    public List<Visit> getNotCanceledVisits() {
        List<Visit> allVisits = null;
        EntityManager em = DBManager.getManager().createEntityManager();

        try {
            em.getTransaction().begin();
            allVisits = (List<Visit>) em.createNamedQuery("Visit.findUnpayedAndPayed").getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return allVisits;
    }

    public String reserve(Long visitId) {
        Patient patient = null;
        PatientCard cardOfPatient = null;
        Visit visit = null;
        EntityManager em = DBManager.getManager().createEntityManager();

        try {
            em.getTransaction().begin();
            visit = (Visit) em.createNamedQuery("Visit.findById").setParameter("id", visitId).getSingleResult();
            visit.setState(VisitState.UNPAYED);
            patient = (Patient) em.createNamedQuery("Patient.findById").setParameter("id", selectedPatient.getId()).getSingleResult();
            List<Visit> visits = patient.getPatientCards().get(0).getVisits();
            visits.add(visit);
            List<PatientCard> cards = patient.getPatientCards();
            //W tym projekcie ograniczamy się do jednej przychodni.
            cardOfPatient = cards.get(0);
            cardOfPatient.setPatient(patient);
            cardOfPatient.setVisits(visits);
            em.merge(patient);
            em.merge(cardOfPatient);
            em.getTransaction().commit();
        } catch (Exception e) {
            MessageController.addMessage("Błąd.", "Nie udało się zarezerwować wizyty, spróbuj ponownie.", FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
        } finally {
            em.close();
        }

        MessageController.addMessageAfterRedirect("Informacja.", "Wizyta została poprawnie zarezerwowana.", FacesMessage.SEVERITY_INFO);

        return null;
    }

    public String cancelByClinic(Long visitId) {
        Visit visit = null;
        EntityManager em = DBManager.getManager().createEntityManager();

        try {
            em.getTransaction().begin();
            visit = (Visit) em.createNamedQuery("Visit.findById").setParameter("id", visitId).getSingleResult();
            visit.setState(VisitState.CANCELEDBYCLINIC);
            em.merge(visit);
            em.getTransaction().commit();
        } catch (Exception e) {
            MessageController.addMessage("Błąd.", "Nie udało się odwołać wizyty, spróbuj ponownie.", FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
        } finally {
            em.close();
        }

        MessageController.addMessageAfterRedirect("Informacja.", "Wizyta została poprawnie anulowana.", FacesMessage.SEVERITY_INFO);

        return null;
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
