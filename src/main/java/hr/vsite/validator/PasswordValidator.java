package hr.vsite.validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
		
		ResourceBundle bundl = ResourceBundle.getBundle("validation");
		
		String password = object.toString();
		
		UIInput uiConfirmePassword = (UIInput) component.getAttributes().get("input_confirmpass");
		
		String confirmPassword = uiConfirmePassword.getSubmittedValue().toString();
		
		if(password == null || password.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()){
			FacesMessage msg = new FacesMessage("Empty password", 
					bundl.getString("Not.empty"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			throw new ValidatorException(msg);
		}
		
		if(!password.equals(confirmPassword)){
			
			FacesMessage msg = new FacesMessage("Must match two passwords",
					bundl.getString("Diff.password"));
			
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			throw new ValidatorException(msg);
		}
	}

}
