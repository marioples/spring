package hr.vsite.services.interfaces;

public interface SecurityService {
	
	String findLoggedInUsername();
	
	void autologin(String username, String password);
}
