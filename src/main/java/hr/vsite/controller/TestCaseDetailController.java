package hr.vsite.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javaplugs.jsf.SpringScopeView;

import hr.vsite.model.Comment;
import hr.vsite.model.TestCase;
import hr.vsite.model.TestSuit;
import hr.vsite.services.interfaces.TestCaseServise;
import hr.vsite.services.interfaces.TestSuitService;
import hr.vsite.services.interfaces.UserServices;

@SpringScopeView
@Component(value = "testCaseDetailController")
@ELBeanName(value = "testCaseDetailController")
@Join(path = "/testcaseDetail", to = "/testcaseDetail.jsf")
public class TestCaseDetailController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final String TEST_NOT_RUN = "Not run";
	private static final String TEST_FAILED = "Failed";
	private static final String TEST_BLOCKED = "Blocked";
	private static final String TEST_PASSED = "Passed";

	private TestCase testCase;

	private String step;
	private String Status;
	
	private Integer countCheck = 0;
	private Integer countAddedStep = 0;
	
	private String commentDescription;
	private List<Comment> comment;
	
	public int id;
	
	private List<SelectItem> subEnvironment;
	
	@Autowired
	private TestSuitService testSuitService;
	
	@Autowired
	private TestCaseServise testCaseServise;
	
	@Autowired
	private UserServices userServices;
	
	@PostConstruct
	public void init(){
		initGrouping();
	}
	
	public String loadCase(){
		testCase = testCaseServise.findbyId(id);
		return null;
	}
	
	public void initGrouping(){
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
		moz.setSelectItems(new SelectItem[]{new SelectItem("Google Chrome", "Google Chrome")});
		
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
	
	public void saveStep(){		
		testCase.getSteps().put(getStep(), Boolean.FALSE);
		displayMessage();
	}
	
	public void displayMessage(){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,  new FacesMessage("Successful", "Step added for testing"));
	}
	
	public void saveComment(){
		Date posted_date = new Date();		
	}
	
	public String update(){
		Date update = new Date();		
		
		testCase.setUpdatedDate(update);
		testCaseServise.save(testCase);
		
		return "testscenario.xhtml?faces-redirect=true";
		 //return navigationBean.redirectFromView();
		/*System.out.println(testCase.getCaseName() + " Bean1");
		System.out.println(testCase.getEnvironment() + " Bean3");
		System.out.println(testCase.getReleatedLink() + " Bean9");
		System.out.println(testCase.getPrerequisites() + " Bean2");
		System.out.println(testCase.getPriority() + " Bean4");
		System.out.println(testCase.getProgress() + " Bean5");
		System.out.println(testCase.getDesription() + " Bean6");
		System.out.println(testCase.getOwner() + " Bean7");
		System.out.println(testCase.getExecutedDate() + " Bean8");
		
		System.out.println(testCase.getSuit().getTestSuitName() + " Bean9");*/
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

	public List<String> getAllUsers(){
		List<String> user = userServices.fetchUsers();
		if(user != null){
			return user;
		}
		return null;
	}
	
	public void confirmButton() {
		for(Entry<String, Boolean> entry : testCase.getSteps().entrySet()){
			if(entry.getValue() == Boolean.TRUE)
			{
				countCheck++;
			}
			if(entry.getValue()){
				countAddedStep++;
			}
		}
		int p = (int)(countCheck * 100 / countAddedStep);
		
		testCase.setProgress(p); 
		
		if(countAddedStep == countCheck){
			Status = TEST_PASSED;
			for(String s : testCase.getReleatedLink()){
				if(s.contains(TEST_FAILED) || s.contains(TEST_NOT_RUN)){
					Status = TEST_BLOCKED;
				}
			}
		}
		if(countAddedStep != countCheck){
			Status = TEST_FAILED;
		}
		countCheck = 0;
		RequestContext requestContext = RequestContext.getCurrentInstance();		
		requestContext.execute("modify('" + Status + "')");
	}


	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Integer getCountCheck() {
		return countCheck;
	}

	public void setCountCheck(Integer countCheck) {
		this.countCheck = countCheck;
	}

	public Integer getCountAddedStep() {
		return countAddedStep;
	}

	public void setCountAddedStep(Integer countAddedStep) {
		this.countAddedStep = countAddedStep;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<SelectItem> getSubEnvironment() {
		return subEnvironment;
	}

	public void setSubEnvironment(List<SelectItem> subEnvironment) {
		this.subEnvironment = subEnvironment;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public String getCommentDescription() {
		return commentDescription;
	}

	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}
}
