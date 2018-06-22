/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.RangeOfAdmission;
import database.Specialist;
import database.Visit;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.ZoneId;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import static java.time.temporal.ChronoUnit.MINUTES;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "generateDatesController")
@RequestScoped
public class GenerateDatesController {

    private List<Specialist> selectedSpecialists;
    private String selectedGeneratorRange;

    public GenerateDatesController() {
    }

    public List<Specialist> getSelectedSpecialists() {
        return selectedSpecialists;
    }

    public void setSelectedSpecialists(List<Specialist> selectedSpecialists) {
        this.selectedSpecialists = selectedSpecialists;
    }

    public String getSelectedGeneratorRange() {
        return selectedGeneratorRange;
    }

    public void setSelectedGeneratorRange(String selectedGeneratorRange) {
        this.selectedGeneratorRange = selectedGeneratorRange;
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

    public float returnPriceForVisit(int lengthOfVisit) {
        switch (lengthOfVisit) {
            case 10:
                return (float) 25;
            case 20:
                return (float) 30;
            case 30:
                return (float) 45;
            case 40:
                return (float) 55;
            case 50:
                return (float) 75;
            case 60:
                return (float) 90;
        }
        return (float) 0;
    }

    public void save(List<Visit> listOfVisits, Specialist spec) {
        EntityManager em = DBManager.getManager().createEntityManager();
        
        try {
            em.getTransaction().begin();
            Specialist specFromDB = (Specialist) em.createNamedQuery("Specialist.findById").setParameter("id", spec.getId()).getSingleResult();
            specFromDB.setVisits(listOfVisits);
            em.merge(specFromDB);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void generate() {
        if (selectedSpecialists == null)
            return;

        List<Visit> listOfVisits = new ArrayList<>();

        LocalDate ld = LocalDate.now();
        LocalDate dateOfWeekDay = null;

        dateOfWeekDay = ld.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        List<RangeOfAdmission> roa = null;

        for (Specialist s : selectedSpecialists) {
            roa = s.getRangeOfAdmissions();
            for (RangeOfAdmission r : roa) {
                switch (r.getWeekDay()) {
                    case MONDAY:
                        dateOfWeekDay = ld.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                        break;
                    case TUESDAY:
                        dateOfWeekDay = ld.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
                        break;
                    case WEDNESDAY:
                        dateOfWeekDay = ld.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
                        break;
                    case THURSDAY:
                        dateOfWeekDay = ld.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
                        break;
                    case FRIDAY:
                        dateOfWeekDay = ld.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
                        break;
                    case SATURDAY:
                        dateOfWeekDay = ld.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
                        break;
                    case SUNDAY:
                        dateOfWeekDay = ld.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
                        break;
                }

                int iterator = (int) MINUTES.between(r.getBeginOfRange(), r.getEndOfRange()) / r.getLengthOfVisit();

                LocalTime lt = r.getBeginOfRange();
                for (int i = 0; i < iterator; i++) {
                    Visit visit = new Visit();
                    visit.setSpecialist(s);
                    visit.setLengthOfVisit(r.getLengthOfVisit());
                    visit.setDateOfVisit(Date.from(dateOfWeekDay.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    visit.setPrice(returnPriceForVisit(r.getLengthOfVisit()));
                    visit.setBeginOfTheVisit(lt);
                    lt.plusMinutes(r.getLengthOfVisit());

                    listOfVisits.add(visit);
                }               
                save(listOfVisits, s);
            }
        }
    }

}
