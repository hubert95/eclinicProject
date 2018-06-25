/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.security.MD5;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import database.Address;
import database.Contact;
import database.Patient;
import database.Account;
import database.Clinic;
import database.PatientCard;
import database.Person;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Hubert Januszek
 */
@ManagedBean
@ViewScoped
public class RegisterController implements Serializable {

    private Patient patient = new Patient();
    private Account account = new Account();
    private Address address = new Address();
    private Contact contact = new Contact();

    private String passw1;
    private String passw2;

    private boolean skip;

    public String getPassw1() {
        return passw1;
    }

    public void setPassw1(String passw1) {
        this.passw1 = passw1;
    }

    public String getPassw2() {
        return passw2;
    }

    public void setPassw2(String passw2) {
        this.passw2 = passw2;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getStringDate() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        return df.format(patient.getDateOfBirth());
    }

    public void validatePassword(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();

        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? ""
                : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();

        // get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();

        // Let required="true" do its job.
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }

        if (!password.equals(confirmPassword)) {
            FacesMessage msg = new FacesMessage("Hasła muszą być takie same.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(passwordId, msg);
            fc.renderResponse();
        }
    }


    public void save() throws ParseException, NoSuchAlgorithmException {
        EntityManager em = DBManager.getManager().createEntityManager();

        patient.setDateOfBirth();
        patient.setAddress(address);
        patient.setContact(contact);
        account.setPassword(MD5.hashPassword(passw1));
        account.setLogin(patient.getPesel());
        account.setRole(Account.Role.PATIENT);
        patient.setAccount(account);
        PatientCard pc = new PatientCard();
        Clinic cl = new Clinic();
        pc.setClinic(new Clinic());
        pc.setPatient(patient);
        List<PatientCard> cards = new ArrayList<>();
        cards.add(pc);
        patient.setPatientCards(cards);

        try {
            em.getTransaction().begin();
            em.persist(patient);
            em.getTransaction().commit();
            MessageController.addMessageAfterRedirect("Informacja", "Dodano nowego użytkownika. " + patient.getFirstname() + " witaj w naszym serwisie.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            MessageController.addMessageAfterRedirect("Błąd", "Proces dodawania nowego użytkownika nie powiódł się. Skontaktuj się z administratorem.", FacesMessage.SEVERITY_ERROR);
        } finally {
            em.close();
            this.patient = new Patient();
        }
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }
}
