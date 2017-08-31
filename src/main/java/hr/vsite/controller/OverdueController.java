package hr.vsite.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import hr.vsite.model.TestCase;
import hr.vsite.services.interfaces.SecurityService;
import hr.vsite.services.interfaces.TestCaseServise;

@Scope("request")
@Component(value = "overdueController")
@ELBeanName(value = "overdueController")
@Join(path = "/overdue", to = "/overdue.jsf")
public class OverdueController {

	private String userName;
	
	public Long CountOverdueTests;
	
	private List<TestCase> OverdueTests;
	
	@Autowired
	private TestCaseServise testCaseServise;
	
	@Autowired
	private SecurityService securityService;
	
	@PostConstruct
	public void init(){
		userName = securityService.findLoggedInUsername();
	}	

	public List<TestCase> getOverdueTests() {
		List<TestCase> overdue = testCaseServise.ovredueTests(userName);
		if(overdue != null)
			return overdue;
		return null;
	}
	
	public String backToLogin(){
		SecurityContextHolder.clearContext();
        return "redirectFromOverdueToLogin";
	}

	public Long getCountOverdueTests() {
		return testCaseServise.countOvredueTests(userName);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setOverdueTests(List<TestCase> overdueTests) {
		OverdueTests = overdueTests;
	}

	public void setCountOverdueTests(Long countOverdueTests) {
		CountOverdueTests = countOverdueTests;
	}
}
