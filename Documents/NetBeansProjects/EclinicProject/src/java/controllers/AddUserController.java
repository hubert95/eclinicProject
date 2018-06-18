/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.Account;
import database.Address;
import database.Contact;
import database.Patient;
import database.Recepcionist;
import database.Specialist;
import java.text.ParseException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManager;

/**
 *
 * @author Maksymilian Jagodziński
 */
@ManagedBean(name = "addUserController")
@RequestScoped
public class AddUserController{
    
    private Account account = new Account();
    private Specialist specialist = new Specialist();
    private Recepcionist recepcionist = new Recepcionist();
    private Address address = new Address();
    private Contact contact = new Contact();
    
    private String login;
    private String passw1;
    private String passw2;
    
    private String typeOfEmployee;
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Recepcionist getRecepcionist() {
        return recepcionist;
    }

    public void setRecepcionist(Recepcionist recepcionist) {
        this.recepcionist = recepcionist;
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

    public String getTypeOfEmployee() {
        return typeOfEmployee;
    }

    public void setTypeOfEmployee(String typeOfEmployee) {
        this.typeOfEmployee = typeOfEmployee;
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
    
    public void save() throws ParseException{
        EntityManager em = DBManager.getManager().createEntityManager();

        if(typeOfEmployee.equals("Lekarz")){
            specialist.setAddress(address);
            specialist.setContact(contact);
            account.setRole(Account.Role.DOCTOR);
            specialist.setAccount(account);
        }else if(typeOfEmployee.equals("Recepcjonista")){
            recepcionist.setAddress(address);
            recepcionist.setContact(contact);
            account.setRole(Account.Role.RECEPCIONIST);
            recepcionist.setAccount(account);
        }
        
        account.setPassword(passw1);
        account.setLogin(login);

        try {
            em.getTransaction().begin();
            
            if(typeOfEmployee.equals("Lekarz")){
                em.persist(specialist);
            }else if(typeOfEmployee.equals("Recepcjonista")){
                em.persist(recepcionist);
            }
            
            em.getTransaction().commit();
            MessageController.addMessageAfterRedirect("Informacja", "Dodano pomyślnie nowego pracownika.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            MessageController.addMessageAfterRedirect("Błąd", "Proces dodawania nowego pracownika nie powiódł się. Sprawdź poprawność danych.", FacesMessage.SEVERITY_ERROR);
        } finally {
            em.close();
            
            if(typeOfEmployee.equals("Lekarz")){
                this.specialist = new Specialist();
            }else if(typeOfEmployee.equals("Recepcjonista")){
                this.recepcionist = new Recepcionist();
            }
        }
    }
}
