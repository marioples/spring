package hr.vsite.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import hr.vsite.model.TestCase;
import hr.vsite.model.TestSuit;
import hr.vsite.services.interfaces.SecurityService;
import hr.vsite.services.interfaces.TestCaseService;
import hr.vsite.services.interfaces.TestSuitService;

@Scope("session")
@Component(value = "testScenarioController")
@ELBeanName(value = "testScenarioController")
@Join(path = "/protected/testscenario", to = "/protected/testscenario.jsf")
public class TestScenarioController {

	private String suitName;
	private String expectation;
	
	private String userName;
	
	private TestCase selectedCase;
	private String[] SelectedSuit;
	private String oneSuit;
	
	private String searchByName;
	private String searchByOwner;
	private String searchByAuthor;
	
	private List<TestCase> AllCase;
	private List<TestSuit> AllSuit;	
	
	@Autowired
	private TestCaseService testCaseService;
	
	@Autowired
	private TestSuitService testSuitService;
	
	@Autowired
	private SecurityService securityService;
	
	@PostConstruct
	private void init(){
		userName = securityService.findLoggedInUsername();
	}
		
	public void CreateSuit(){
		TestSuit testSuit = new TestSuit();
		
		testSuit.setTestSuitName(getSuitName());
		testSuit.setExpectation(getExpectation());
		
		testSuitService.save(testSuit);
	}
	
	public String backToLogin(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		SecurityContextHolder.clearContext();
        return "redirectFromScenarioToLogin";
	}
	
	public void SelectedSuits(){
		if(SelectedSuit.length < 1){
			oneSuit = null;
			return;
		}		
		oneSuit = SelectedSuit[0];		
	}
	
	public boolean isAdminAllowedAccess(){
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("ADMIN");
	}
	
	public List<TestCase> getAllCase() {		
		
		if((searchByName != null && !searchByName.isEmpty()) || (searchByAuthor != null && !searchByAuthor.isEmpty()) || (searchByOwner != null && !searchByOwner.isEmpty())){
			List<TestCase> multipleParametar = testCaseService.findByCaseNameAuthorOwner(searchByName, searchByAuthor, searchByOwner);			
			if(multipleParametar != null)
				return multipleParametar;
		}
				
		if(oneSuit == null){
			List<TestCase> testCase = testCaseService.findAllTestCases();
			if(testCase != null)
				return testCase;
		}
		
		if(oneSuit != null){
			List<TestCase> testCaseWithSuit = testCaseService.findAllCasesWithParametar(oneSuit);
			if(testCaseWithSuit != null)
				return testCaseWithSuit;
		}
		return null;
	}
	
	public String testcase(){
		return "protected/testcase?faces-redirect=true";
	}
	
	public List<TestSuit> getAllSuit() {
		List<TestSuit> testSuits = testSuitService.findAll();
		if(testSuits != null){
			return testSuits;
		}
		return null;
	}

	public String getSuitName() {
		return suitName;
	}

	public void setSuitName(String suitName) {
		this.suitName = suitName;
	}

	public String getExpectation() {
		return expectation;
	}

	public void setExpectation(String expectation) {
		this.expectation = expectation;
	}

	public TestCase getSelectedCase() {
		return selectedCase;
	}

	public void setSelectedCase(TestCase selectedCase) {
		this.selectedCase = selectedCase;
	}

	public String[] getSelectedSuit() {
		return SelectedSuit;
	}

	public void setSelectedSuit(String[] selectedSuit) {
		SelectedSuit = selectedSuit;
	}

	public String getOneSuit() {
		return oneSuit;
	}

	public void setOneSuit(String oneSuit) {
		this.oneSuit = oneSuit;
	}

	public void setAllCase(List<TestCase> allCase) {
		AllCase = allCase;
	}

	public void setAllSuit(List<TestSuit> allSuit) {
		AllSuit = allSuit;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public String getSearchByName() {
		return searchByName;
	}

	public void setSearchByName(String searchByName) {
		this.searchByName = searchByName;
	}

	public String getSearchByOwner() {
		return searchByOwner;
	}

	public void setSearchByOwner(String searchByOwner) {
		this.searchByOwner = searchByOwner;
	}

	public String getSearchByAuthor() {
		return searchByAuthor;
	}

	public void setSearchByAuthor(String searchByAuthor) {
		this.searchByAuthor = searchByAuthor;
	}
}
