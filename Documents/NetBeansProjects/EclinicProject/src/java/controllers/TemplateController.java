package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import database.Account;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;

/**
 *
 * @author Hubert Januszek
 */
@ManagedBean(name = "templateController")
@SessionScoped
public class TemplateController implements Serializable {

    private String login = "";
    private String password = "";

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String index() {
        return "index?faces-redirect=true";
    }

    public String freeDates() {
        return "free_dates?faces-redirect=true";
    }

    public String freeDatesPatient() {
        return "free_dates?faces-redirect=true";
    }

    public String clinicOffer() {
        return "clinic_offer?faces-redirect=true";
    }

    public String clinicOfferPatient() {
        return "clinic_offer?faces-redirect=true";
    }

    public String specialists() {
        return "specialists?faces-redirect=true";
    }

    public String specialistsPatient() {
        return "specialists?faces-redirect=true";
    }

    public String myVisits() {
        return "my_visits?faces-redirect=true";
    }

    public String patient() {
        return "patient?faces-redirect=true";
    }

    public String doctor() {
        return "doctor?faces-redirect=true";
    }

    public String timetableDoctor() {
        return "timetable?faces-redirect=true";
    }

    public String myPatientsDoctor() {
        return "my_patients?faces-redirect=true";
    }

    public String myProfileDoctor() {
        return "my_profile?faces-redirect=true";
    }

    public String help() {
        return "help?faces-redirect=true";
    }

    public String register() {
        return "register?faces-redirect=true";
    }

    public String recepcionist() {
        return "recepcionist";
    }

    public String recepcionistPatients() {
        return "patients";
    }

    public String recepcionistDoctors() {
        return "doctors";
    }

    public String logOut() {
        return "/view/index?faces-redirect=true";
    }
    
    public String admin(){
        return "admin?faces-redirect=true";
    }
    
    public String addEmployeesAdmin(){
        return "add_employees_admin?faces-redirect=true";
    }
    
    public String manageEmployeesAdmin(){
        return "manage_employees_admin?faces-redirect=true";
    }
    
    public String othersAdmin(){
        return "others_admin?faces-redirect=true";
    }
    
    public String addUserAdmin(){
        return "add_employees_admin?faces-redirect=true";
    }
    
    public String recepcionistGenerateDates(){
        return "generate_dates?faces-redirect=true";
    }

    public String loginController() {

        Account account = LoginController.loginControl(login, password);

        clearValues();

        if (account != null) {
            switch (account.getRole()) {
                case PATIENT:
                    return "patient/patient";
                case DOCTOR:
                    return "doctor/doctor";
                case RECEPCIONIST:
                    return "recepcionist/recepcionist";
                case ADMIN:
                    return "admin/admin";
            }
        }
            
        MessageController.addMessage("Ostrzeżenie", "Wprowadzono błędny login lub hasło.", FacesMessage.SEVERITY_WARN);

        return null;
    }
    
    public String loginByFacebook(){
        return null;
    }

    public void clearValues() {
        login = null;
        password = null;
    }
}
