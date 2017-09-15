package hr.vsite.validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;

import hr.vsite.services.interfaces.UserServices;

@FacesValidator("notEmptyValidator")
public class NotEmptyValidator implements Validator {
	
	@Autowired
	private UserServices userServices;

	@Override
	public void validate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
		
		ResourceBundle bundle = ResourceBundle.getBundle("validation");
		
		String parametar = object.toString();
		
		if(parametar == null || parametar.isEmpty()){
			FacesMessage msg = new FacesMessage("Not empty",
					bundle.getString("Not.empty"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			throw new ValidatorException(msg);
		}	
	}

}
