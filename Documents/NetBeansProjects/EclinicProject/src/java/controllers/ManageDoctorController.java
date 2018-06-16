/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.RangeOfAdmission;
import database.Specialist;
import database.WeekDay;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "manageDoctorController")
@RequestScoped
public class ManageDoctorController {

    private RangeOfAdmission roa = new RangeOfAdmission();
    private LocalTime startOfVR;
    private String stringStartOfRange;
    private int lenghtOfVisit = 10;
    private int numberOfVisit = 1;
    private List<SelectItem> weekdaysItem;
    private List<RangeOfAdmission> rangeOfAdmissions;
    private RangeOfAdmission selectedRange;

    @PostConstruct
    public void init() {
        weekdaysItem = new ArrayList<>();

        for (WeekDay wd : WeekDay.values()) {
            weekdaysItem.add(new SelectItem(wd, wd.name()));
        }

        EntityManager em = DBManager.getManager().createEntityManager();
        try {
            em.getTransaction().begin();
            rangeOfAdmissions = (List<RangeOfAdmission>) em.createNamedQuery("RangeOfAdmission.findAll").getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public ManageDoctorController() {
    }

    public WeekDay[] getWeekdays() {
        return WeekDay.values();
    }

    public String getStringStartOfRange() {
        return stringStartOfRange;
    }

    public void setStringStartOfRange(String stringStartOfRange) {
        this.stringStartOfRange = stringStartOfRange;
    }

    public int getLenghtOfVisit() {
        return lenghtOfVisit;
    }

    public void setLenghtOfVisit(int lenghtOfVisit) {
        this.lenghtOfVisit = lenghtOfVisit;
    }

    public int getNumberOfVisit() {
        return numberOfVisit;
    }

    public void setNumberOfVisit(int numberOfVisit) {
        this.numberOfVisit = numberOfVisit;
    }

    public List<SelectItem> getWeekdaysItem() {
        return weekdaysItem;
    }

    public void setWeekdaysItem(List<SelectItem> weekdaysItem) {
        this.weekdaysItem = weekdaysItem;
    }

    public RangeOfAdmission getRoa() {
        return roa;
    }

    public void setRoa(RangeOfAdmission roa) {
        this.roa = roa;
    }

    public RangeOfAdmission getSelectedRange() {
        return selectedRange;
    }

    public void setSelectedRange(RangeOfAdmission selectedRange) {
        this.selectedRange = selectedRange;
    }

    public List<RangeOfAdmission> getRangeOfAdmissions() {
        return rangeOfAdmissions;
    }

    public void setRangeOfAdmissions(List<RangeOfAdmission> rangeOfAdmissions) {
        this.rangeOfAdmissions = rangeOfAdmissions;
    }

    public void tmp() {
        Specialist s = new Specialist();
        s.setFirstname("Andrzej");
        s.setLastname("Kowalski");
        EntityManager em = DBManager.getManager().createEntityManager();

        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        em.close();
    }

    public void save() {
        tmp();
        EntityManager em = DBManager.getManager().createEntityManager();

        startOfVR = LocalTime.parse(stringStartOfRange);
        LocalTime endOfVisitRange = startOfVR.plusMinutes(numberOfVisit * lenghtOfVisit);

        try {
            em.getTransaction().begin();
            Specialist s = (Specialist) em.createNamedQuery("Specialist.findById").setParameter("id", 1L).getSingleResult();
            List<RangeOfAdmission> roaList = s.getRangeOfAdmissions();
            roa.setBeginOfRange(startOfVR);
            roa.setLengthOfVisit(lenghtOfVisit);
            roa.setEndOfRange(endOfVisitRange);
            roaList.add(roa);
            s.setRangeOfAdmissions(roaList);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
