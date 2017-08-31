package hr.vsite.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.vsite.model.Role;
import hr.vsite.repository.RoleRepository;

@Service
public class RoleServiceImpl implements hr.vsite.services.interfaces.RoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public void save(Role role) {
		roleRepository.save(role);		
	}

}
