package hr.vsite.services.interfaces;

import java.util.List;

import hr.vsite.model.User;

public interface UserServices {

	void save(User user);
	
	User findByUsername(String username);
	
	String findUsernameByUsername(String username);
	
	List<String> fetchUsers();
}
