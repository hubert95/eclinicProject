/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.RangeOfAdmission;
import database.Recepcionist;
import database.Specialist;
import database.WeekDay;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "manageDoctorController")
@ViewScoped
public class ManageDoctorController {

    private Specialist selectedSpecialist;
    private RangeOfAdmission selectedRange;
    
    private RangeOfAdmission roa = new RangeOfAdmission();
    private LocalTime startOfVR;
    private String stringStartOfRange;
    private int lenghtOfVisit = 10;
    private int numberOfVisit = 1;
    private List<SelectItem> weekdaysItem;
    private List<RangeOfAdmission> rangeOfAdmissions;
    
    private boolean tab;
    private int tabNumber;
    

    @PostConstruct
    public void init() {
        this.tab = false;
        this.tabNumber = 0;
        
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

    public Specialist getSelectedSpecialist() {
        return selectedSpecialist;
    }

    public void setSelectedSpecialist(Specialist selectedSpecialist) {
        this.selectedSpecialist = selectedSpecialist;
    } 

    public boolean isTab() {
        return tab;
    }

    public int getTabNumber() {
        return tabNumber;
    }

    public void setTabNumber(int tabNumber) {
        this.tabNumber = tabNumber;
    }
    
    public List<Specialist> getSpecialists(){
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
    

    public void confirmMessage() {
        MessageController.addMessage("Usunięto wybrany przedział.", "Pomyśle wykonanie operacji.", FacesMessage.SEVERITY_INFO);
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
//        tmp();
        EntityManager em = DBManager.getManager().createEntityManager();

        startOfVR = LocalTime.parse(stringStartOfRange);
        LocalTime endOfVisitRange = startOfVR.plusMinutes(numberOfVisit * lenghtOfVisit);

        try {
            em.getTransaction().begin();
            selectedSpecialist = (Specialist) em.createNamedQuery("Specialist.findById").setParameter("id", 1L).getSingleResult();
            List<RangeOfAdmission> roaList = selectedSpecialist.getRangeOfAdmissions();
            roa.setBeginOfRange(startOfVR);
            roa.setLengthOfVisit(lenghtOfVisit);
            roa.setEndOfRange(endOfVisitRange);
            roa.setSpecialist(selectedSpecialist);
            roaList.add(roa);
            selectedSpecialist.setRangeOfAdmissions(roaList);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void removeRange() {
        EntityManager em = DBManager.getManager().createEntityManager();
        List<RangeOfAdmission> roaTmp = null;
        int i = 0;

        if (selectedRange == null) {
            MessageController.addMessage("Błąd.", "Nie zaznaczono żadnego przedziału. Proszę spróbować ponownie.", FacesMessage.SEVERITY_WARN);
        } else {
        }
        try {
            em.getTransaction().begin();
            Specialist s = (Specialist) em.createNamedQuery("Specialist.findById").setParameter("id", 1L).getSingleResult();
            roaTmp = s.getRangeOfAdmissions();
            
            if(roaTmp.size() == 0) return;
            
            for(RangeOfAdmission x : roaTmp){
                if(x.getId() == selectedRange.getId())
                    break;
                i++;
            }
            s.getRangeOfAdmissions().remove(i);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            em.close();
        }
    }
    
    public void onTabChange() throws IOException{
        if(selectedSpecialist == null){
            MessageController.addMessage("ABC", "NIE", FacesMessage.SEVERITY_ERROR);
            return;
        }
        
//        tab = !tab;
        
        tabNumber = 1;
        
        System.out.println(selectedSpecialist.getFirstname());
        ReloadController rc = new ReloadController();
        System.out.println(tab);
    }
}
