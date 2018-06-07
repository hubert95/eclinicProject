/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Hubert Januszek
 */
@FacesValidator("passwordValidator")
public class PasswordValidator extends RegexChecker implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {

        if (!check(passwordPattern, o.toString())) {
            FacesMessage msg
                    = new FacesMessage("Hasło nie spełnia wymagań. W celu uzyskania informacji na temat poprawnego hasła należy przejść do zakładki \"Pomoc\".",
                            "");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(msg);
        }

    }

}
