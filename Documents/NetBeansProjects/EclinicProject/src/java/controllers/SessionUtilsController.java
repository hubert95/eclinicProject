/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Maksymilian Jagodzinski
 */

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import database.Account.Role;
import database.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "sessionController")
@SessionScoped
public class SessionUtilsController {

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public static String getLogin() {
            HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("login");
		else
			return null;
	}

	public static Long getId() {
		HttpSession session = getSession();
		if (session != null)
			return (Long) session.getAttribute("id");
		else
			return null;
	}
        public static Role getRole() {
		HttpSession session = getSession();
		if (session != null)
			return (Role) session.getAttribute("role");
		else
			return null;
	}
        
        public static String getUserInfo(){
            HttpSession session = getSession();
            if (session != null) {
                User user = (User) session.getAttribute("user");
                return user.getFirstname() + " " + user.getLastname();
            } else {
                return null;
            }
        }
        
        public static void sessionClose(){
            HttpSession session = SessionUtilsController.getSession();
            session.invalidate();
        }
}
