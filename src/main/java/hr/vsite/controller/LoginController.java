package hr.vsite.controller;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("request")
@Component(value = "loginController")
@ELBeanName(value = "loginController")
@Join(path = "/login", to = "/login.jsf")
public class LoginController {

	public String doLogin() throws IOException, ServletException {
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_check");

        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();

        return null;
	}
	
	public String Logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();		
		return "/login?faces-redirect=true";
	}
}
