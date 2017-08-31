package hr.vsite.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.vsite.model.User;
import hr.vsite.repository.UserRepository;
import hr.vsite.services.interfaces.UserServices;

@Service
public class UserServicesImpl implements UserServices {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void save(User user) {		
		userRepository.save(user);		
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public String findUsernameByUsername(String username) {
		String user = userRepository.findUsernameByUsername(username);
		if(user != null){
			return user;
		}
		return "";
	}

	@Override
	public List<String> fetchUsers() {
		return userRepository.fetchUsers();
	}

}
