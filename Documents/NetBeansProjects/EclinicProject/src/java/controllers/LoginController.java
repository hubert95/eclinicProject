/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.Account;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author Hubert Januszek
 */
@ManagedBean
@SessionScoped
public class LoginController {

    public LoginController() {
    }
    
    public static Account loginControl(String login, String password){
        
        EntityManager em = DBManager.getManager().createEntityManager();
        Account account = null;
        
        try {
            em.getTransaction().begin();
            account = (Account) em.createNamedQuery("Account.control").setParameter("login", login).setParameter("password", password).getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            return null;
        } finally{
            em.close();
        }
        
        return account;
    }
    
}
