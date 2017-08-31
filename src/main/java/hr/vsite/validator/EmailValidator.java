package hr.vsite.validator;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator{
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
			"(\\.[A-Za-z]{2,})$";
	
	private final static Pattern EMAIL_COMPILED_PATTERN = Pattern.compile(EMAIL_PATTERN);
	
	private Matcher matcher;		
	
	public EmailValidator() {}

	@Override
	public void validate(FacesContext context, UIComponent component, Object object) throws ValidatorException {
		
		ResourceBundle bundl = ResourceBundle.getBundle("validation");
		
		if(object.toString() == null || object.equals("") ){
			FacesMessage msg = new FacesMessage("Empty email",
					bundl.getString("Not.empty"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			throw new ValidatorException(msg);
		}				
		
		matcher = EMAIL_COMPILED_PATTERN.matcher(object.toString());
		
		if(!matcher.matches()){
			
			FacesMessage msg = new FacesMessage("email",
					bundl.getString("Invalid.email"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			throw new ValidatorException(msg);
		}		
	}
}
