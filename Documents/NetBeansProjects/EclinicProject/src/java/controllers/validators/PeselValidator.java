/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.validators;

import controllers.DBManager;
import controllers.MessageController;
import database.Patient;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;

/**
 *
 * @author Hubert Januszek
 */
@FacesValidator("peselValidator")
public class PeselValidator extends RegexChecker implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        Patient pat = null;
        EntityManager em = DBManager.getManager().createEntityManager();

        try {
            em.getTransaction().begin();
            pat = (Patient) em.createNamedQuery("Patient.findByPesel").setParameter("pesel", o.toString()).getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        
        if (pat != null) {
            FacesMessage msg = new FacesMessage("Uwaga!", "Istenieje już konto na podany numer PESEL. Skontaktuj się z administratorem.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        
        if (!check(peselPattern, o.toString())) {
            FacesMessage msg = new FacesMessage("Wprowadzony numer PESEL jest nieprawidłowy.", "");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(msg);
        }

    }

}
