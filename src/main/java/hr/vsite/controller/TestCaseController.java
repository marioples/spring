package hr.vsite.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javaplugs.jsf.SpringScopeView;

import hr.vsite.model.Comment;
import hr.vsite.model.TestCase;
import hr.vsite.model.TestSuit;
import hr.vsite.model.TestingSteps;
import hr.vsite.services.interfaces.SecurityService;
import hr.vsite.services.interfaces.TestCaseServise;
import hr.vsite.services.interfaces.TestSuitService;
import hr.vsite.services.interfaces.UserServices;

@SpringScopeView
@Component(value = "testCaseController")
@ELBeanName(value = "testCaseController")
@Join(path = "/protected/testcase", to = "/protected/testcase.jsf")
public class TestCaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String TEST_NOT_RUN = "Not run";
	private static final String TEST_FAILED = "Failed";
	private static final String TEST_BLOCKED = "Blocked";
	private static final String TEST_PASSED = "Passed";
	
	private static Integer MAX_STEPS = 0;
	
	private String priority;
	private String environment;
	private String subEnv;
	private String caseName;
	private String prerequisites;	
	private String description;
	private String author;
	private String owner;
	private String Status = "Not run";	
	private Integer progress;
	private Date executionDate;
	private Date createdDate;
	private List<Comment> comment;
    
    private String step;
	private List<String> selectedLink;
	private String[] relatedSuit;
	
	private List<TestingSteps> testingSteps;
	
	private List<SelectItem> subEnvironment;
	
	private Integer countAddedStep = 0;
	private Integer countCheck = 0;
	
	private String commentDescription;
	
	private Comment comm;
	
	private Date now;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private TestCaseServise testCaseServise;
	
	@Autowired
	private TestSuitService testSuitService;
	
	@Autowired
	private UserServices userServices;
	
	@PostConstruct
	private void init(){
		testingSteps = new ArrayList<TestingSteps>(); 
		comment = new ArrayList<Comment>();
		initializeGroup();
	}
	
	public void saveStep(){
		if(MAX_STEPS < 10){
			TestingSteps steps = new TestingSteps();
			steps.setStep(getStep());
			steps.setChecked(false);
			
			testingSteps.add(steps);
			displayMessage();
			MAX_STEPS++;
		}		
	}
	
	private void displayMessage() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,  new FacesMessage("Successful", "Step added for testing"));
	}
	
	private void displayTestStatus(String status){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Test case", "With status " + status));
	}
	
	public void saveComment(){
		Date posted_date = new Date();
		
		comm = new Comment();
		
		comm.setCommentDescription(getCommentDescription());
		comm.setPosted_date(posted_date);
		comm.setUserComment(getAuthor());
		
		comment.add(comm);
	}
	
	public List<String> getAllUsers(){
		List<String> user = userServices.fetchUsers();
		if(user != null){
			return user;
		}
		return null;
	}
	
	public String saveTestCase(){		
		now = new Date();
		createdDate = now;
		
		MAX_STEPS = 0;
		
		TestCase testCase = new TestCase();		
		
		for(Comment c : comment){
			c.setTestCase(testCase);
		}
		
		for(TestingSteps ts : testingSteps){
			ts.setSteptestCase(testCase);
		}
		
		testCase.setCaseName(getCaseName());
		testCase.setEnvironment(getEnvironment());
		testCase.setSubEnvironment(getSubEnv());
		testCase.setAuthor(getAuthor());
		testCase.setOwner(getOwner());
		testCase.setDesription(getDescription());
		testCase.setExecutedDate(getExecutionDate());
		testCase.setCreatedDate(getCreatedDate());
		testCase.setPrerequisites(getPrerequisites());
		testCase.setPriority(getPriority());
		testCase.setStatus(getStatus());
		testCase.setTestingSteps(getTestingSteps());
		testCase.setReleatedLink(getSelectedLink());
		testCase.setProgress(getProgress());
		testCase.setComments(getComment());
		
		if(!(getAuthor().equals(getOwner()))){
			testCase.setIsAssigned(true);
		}
		
		if(getAuthor().equals(getOwner())){
			testCase.setIsAssigned(true);
		}
		
		if(relatedSuit.length > 0 && relatedSuit.length < 2){
			int id = Integer.parseInt(relatedSuit[0]);
			TestSuit suit = testSuitService.findById(id);
			testCase.setSuit(suit);
		} 							
		testCaseServise.save(testCase);
		
		return "testscenario.xhtml?faces-redirect=true";
	}
	
	public void confirmButton() {
		for(TestingSteps ts : testingSteps){
			if(ts.getChecked() == Boolean.TRUE){
				countCheck++;
			}
			if(ts.getChecked() == Boolean.TRUE || ts.getChecked() == Boolean.FALSE){
				countAddedStep++;
			}
		}
		
		progress = (int)(countCheck * 100 / countAddedStep);

		if(countAddedStep == countCheck){
			Status = TEST_PASSED;
			for(String s : selectedLink){
				if(s.contains(TEST_FAILED) || s.contains(TEST_NOT_RUN)){
					Status = TEST_BLOCKED;
				}
			}
		}
		if(countAddedStep != countCheck){
			Status = TEST_FAILED;
		}
		
		displayTestStatus(Status);
		
		countCheck = 0;
		countAddedStep = 0;		
	}

	public List<String> getAllLinks(){
		List<TestCase> links = testCaseServise.findAllTestCases();
		List<String> li = new ArrayList<String>();
		
		if(links != null){
			for(TestCase t : links)
			{
				li.add(t.getCaseName() + " - " + t.getStatus());
			}
			return li;
		}
		return null;
	}
	
	public List<TestSuit> AllSuit(){
		List<TestSuit> suits = testSuitService.findAll();
		if(suits != null)
			return suits;
		return null;
	}
	
	public String backToTestcsenario(){
		return "testscenario.xhtml?faces-redirect=true";
	}
	
	public void initializeGroup(){
		SelectItemGroup win = new SelectItemGroup("Windows");
		win.setSelectItems(new SelectItem[]{new SelectItem("Windows xp", "Windows xp"), new SelectItem("Windows 7", "Windows 7"), new SelectItem("Windows 8", "Windows 8"), new SelectItem("Windows 8.1", "Windows 8.1"), new SelectItem("Windows 9", "Windows 9"), new SelectItem("Windows 10", "Windows 10")});
		
		SelectItemGroup ie = new SelectItemGroup("Internet Explorer");
		ie.setSelectItems(new SelectItem[]{new SelectItem("IE 7", "IE 7"), new SelectItem("IE 8", "IE 8"), new SelectItem("IE 9", "IE 9"), new SelectItem("IE 10", "IE 10"), new SelectItem("IE 11", "IE 11")});
		
		SelectItemGroup iOS = new SelectItemGroup("iOS");
		iOS.setSelectItems(new SelectItem[]{new SelectItem("iOS 4.x", "iOS 4.x"), new SelectItem("iOS 5.x", "iOS 5.x"), new SelectItem("iOS 6.x", "iOS 6.x"), new SelectItem("iOS 7.x", "iOS 7.x"), new SelectItem("iOS 8.x", "iOS 8.x"), new SelectItem("iOS 9.x", "iOS 9.x"), new SelectItem("iOS 10.x", "iOS 10.x")});
		
		SelectItemGroup android = new SelectItemGroup("Android");
		android.setSelectItems(new SelectItem[]{new SelectItem("Nougat - 7.0, 7.1", "Nougat - 7.0, 7.1"), new SelectItem("Marshmallow - 6.0", "Marshmallow - 6.0"), new SelectItem("Lolipop - 5.0, 5.1", "Lolipop - 5.0, 5.1"), new SelectItem("Kitkat - 4.4", "Kitkat - 4.4"), new SelectItem("Jelly Bean - 4.3, 4.2, 4.1", "Jelly Bean - 4.3, 4.2, 4.1"), new SelectItem("Ice Cream Sandwich - 4.0", "Ice Cream Sandwich - 4.0"), new SelectItem("Gingerbread - 2.3", "Gingerbread - 2.3")});
		
		SelectItemGroup go = new SelectItemGroup("Google");
		go.setSelectItems(new SelectItem[]{new SelectItem("Google Chrome", "Google Chrome")});
		
		SelectItemGroup moz = new SelectItemGroup("Mozilla");
		moz.setSelectItems(new SelectItem[]{new SelectItem("Mozilla firefox", "Mozilla firefox")});
		
		SelectItemGroup sa = new SelectItemGroup("Safari");
		sa.setSelectItems(new SelectItem[]{new SelectItem("Safari", "Safari")});
		
		SelectItemGroup op = new SelectItemGroup("Opera");
		op.setSelectItems(new SelectItem[]{new SelectItem("Opera", "Opera")});
		
		subEnvironment = new ArrayList<SelectItem>();
		subEnvironment.add(win);
		subEnvironment.add(ie);
		subEnvironment.add(iOS);
		subEnvironment.add(android);
		subEnvironment.add(go);
		subEnvironment.add(moz);
		subEnvironment.add(sa);
		subEnvironment.add(op);		
	}
	
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getSubEnv() {
		return subEnv;
	}
	public void setSubEnv(String subEnv) {
		this.subEnv = subEnv;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getPrerequisites() {
		return prerequisites;
	}
	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthor() {
		return securityService.findLoggedInUsername();
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public Integer getProgress() {
		if(progress == null) {
            progress = 0;
        }
        return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	public Date getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public List<Comment> getComment() {
		return comment;
	}
	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
	
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public List<String> getSelectedLink() {
		return selectedLink;
	}
	public void setSelectedLink(List<String> selectedLink) {
		this.selectedLink = selectedLink;
	}
	public String[] getRelatedSuit() {
		return relatedSuit;
	}
	public void setRelatedSuit(String[] relatedSuit) {
		this.relatedSuit = relatedSuit;
	}
	public List<SelectItem> getSubEnvironment() {
		return subEnvironment;
	}
	public void setSubEnvironment(List<SelectItem> subEnvironment) {
		this.subEnvironment = subEnvironment;
	}
	public Integer getCountAddedStep() {
		return countAddedStep;
	}
	public void setCountAddedStep(Integer countAddedStep) {
		this.countAddedStep = countAddedStep;
	}
	public Integer getCountCheck() {
		return countCheck;
	}
	public void setCountCheck(Integer countCheck) {
		this.countCheck = countCheck;
	}
	public String getCommentDescription() {
		return commentDescription;
	}
	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}
	public Comment getComm() {
		return comm;
	}
	public void setComm(Comment comm) {
		this.comm = comm;
	}
	public Date getNow() {
		return now;
	}
	public void setNow(Date now) {
		this.now = now;
	}
	
	public List<TestingSteps> getTestingSteps() {
		return testingSteps;
	}

	public void setTestingSteps(List<TestingSteps> testingSteps) {
		this.testingSteps = testingSteps;
	}

}
