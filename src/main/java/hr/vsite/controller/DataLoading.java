package hr.vsite.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import hr.vsite.model.Role;
import hr.vsite.model.User;
import hr.vsite.services.interfaces.RoleService;
import hr.vsite.services.interfaces.UserServices;

@Component
public class DataLoading implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	UserServices userServices;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		roleService.save(role);
		
		List<Role> listOfRole = new LinkedList<Role>();
		listOfRole.add(role);
		
		User user = new User("admin", "admin2@gmail.com", bCryptPasswordEncoder.encode("admin") , listOfRole);
		
		userServices.save(user);
		
	}

	
	
}
