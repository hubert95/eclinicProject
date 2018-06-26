/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.Patient;
import database.Visit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author Hubert Januszek
 */
@ManagedBean(name = "timetableController")
@RequestScoped
public class TimetableController {

    private Date mindate;
    private Date selectDay;
    private Visit selectedVisit;
    
    public TimetableController() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        this.mindate = new Date();
        this.selectDay = dt;
    }

    public Date getMindate() {
        return mindate;
    }

    public Date getSelectDay() {
        return selectDay;
    }

    public void setSelectDay(Date selectDay) {
        this.selectDay = selectDay;
    }

    public Visit getSelectedVisit() {
        return selectedVisit;
    }

    public void setSelectedVisit(Visit selectedVisit) {
        this.selectedVisit = selectedVisit;
    }

    public List<Visit> getVisitsForCheckedDay(){
        Long specialistId = SessionUtilsController.getOwnerId();
        List<Visit> visits = null;
        EntityManager em = DBManager.getManager().createEntityManager();
        
        try {
            em.getTransaction().begin();
            visits = (List<Visit>) em.createNamedQuery("Visit.findForTimetable").setParameter("specialistId", specialistId).setParameter("dateOfVisit", selectDay).getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }      
        
        return visits;
    }
    
    public Patient getPatientForSelectedVisit(){
        Patient patient = null;
        EntityManager em = DBManager.getManager().createEntityManager();
        
        try {
            em.getTransaction().begin();
            //dodać uchwyt do pacjenta
            em.getTransaction().commit();
        } catch (Exception e) {
        }
        return patient;
    }
    
}
