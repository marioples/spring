package hr.vsite.controller;

import java.util.LinkedList;
import java.util.List;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import hr.vsite.model.Role;
import hr.vsite.model.User;
import hr.vsite.services.interfaces.RoleService;
import hr.vsite.services.interfaces.SecurityService;
import hr.vsite.services.interfaces.UserServices;

@Scope("session")
@Component(value = "signupController")
@ELBeanName(value = "signupController")
@Join(path = "/signup", to = "/signup.jsf")
public class SignupController { 
	
	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_USER = "ROLE_USER";
	
	private String _username;
	private String _email;
	private String _password;
	private String _passwordConfirm;
	
	private Boolean isAdminRoleChecked;

	@Autowired
	private UserServices userServices;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public String registration(){
		
		Role role = new Role();
		
		if(isAdminRoleChecked == true){
			role.setName(ROLE_ADMIN);
		}
		if(isAdminRoleChecked == false){
			role.setName(ROLE_USER);
		}
		
		roleService.save(role);
		
		List<Role> listOfRole = new LinkedList<Role>();
		listOfRole.add(role);

		User user = new User();
		
		user.setUsername(_username);
		user.setEmail(_email);
		user.setPassword(bCryptPasswordEncoder.encode(_password));
		user.setRolesOfUsers(listOfRole);
		user.setPasswordConfirm(_passwordConfirm);
		
		userServices.save(user);
		
		securityService.autologin(_username, _passwordConfirm);
		
		return "protected/testscenario?faces-redirect=true";
	}

	public String get_username() {
		return _username;
	}

	public void set_username(String _username) {
		this._username = _username;
	}

	public String get_email() {
		return _email;
	}

	public void set_email(String _email) {
		this._email = _email;
	}

	public String get_password() {
		return _password;
	}

	public void set_password(String _password) {
		this._password = _password;
	}

	public String get_passwordConfirm() {
		return _passwordConfirm;
	}

	public void set_passwordConfirm(String _passwordConfirm) {
		this._passwordConfirm = _passwordConfirm;
	}

	public Boolean getIsAdminRoleChecked() {
		return isAdminRoleChecked;
	}

	public void setIsAdminRoleChecked(Boolean isAdminRoleChecked) {
		this.isAdminRoleChecked = isAdminRoleChecked;
	}	
}
