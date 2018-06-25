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
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "reserveController")
@RequestScoped
public class ReserveTheVisitController {

    /**
     * Creates a new instance of ReserveTheVisitController
     */
    public ReserveTheVisitController() {
    }

    public String reserveWithoutPay() {
        Long visitId = SessionUtilsController.getVisitId();
        Long userId = SessionUtilsController.getOwnerId();
        Patient patient = null;
        PatientCard cardOfPatient = null;
        if (visitId == null) {
            MessageController.addMessage("Błąd.", "Nie udało się zarezerwować wizyty, spróbuj ponownie.", FacesMessage.SEVERITY_ERROR);
            return null;
        }

        EntityManager em = DBManager.getManager().createEntityManager();
        Visit visit = null;
        try {
            em.getTransaction().begin();
            visit = (Visit) em.createNamedQuery("Visit.findById").setParameter("id", visitId).getSingleResult();
            visit.setState(VisitState.UNPAYED);
            patient = (Patient) em.createNamedQuery("Patient.findById").setParameter("id", userId).getSingleResult();
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
            e.printStackTrace();
        } finally {
            em.close();
        }

        MessageController.addMessageAfterRedirect("Informacja.", "Wizyta została poprawnie zarezerwowana.", FacesMessage.SEVERITY_INFO);
        return "my_visits";
    }

    public void reserveWithPay() {
        Long visitId = SessionUtilsController.getVisitId();

    }

}
