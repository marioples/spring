package hr.vsite.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.component.steps.Steps;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import hr.vsite.model.TestCase;
import hr.vsite.model.TestSuit;
import hr.vsite.services.interfaces.SecurityService;
import hr.vsite.services.interfaces.TestCaseServise;
import hr.vsite.services.interfaces.TestSuitService;

@Scope("session")
@Component(value = "trackingController")
@ELBeanName(value = "trackingController")
@Join(path = "/protected/tracking", to = "/protected/tracking.jsf")
public class TrackingController implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final String PatternDayMonth = "dd/MM";
	private static final String PatternDayMonthYear = "dd.MM.yy";
	
	private static final String TEST_PASSED = "Passed";
	private static final String TEST_FAILED = "Failed";
	private static final String TEST_NOT_RUN = "Not run";
	private static final String TEST_BLOCKED = "Blocked";
	
	public Long TestPassed;
	public Long TestFailed;
	public Long TestNotRun;
	public Long TestBlocked;
	
	public Long PercentTestPassed;
	public Long PercentTestFailed;
	public Long PercentTestNotRun;
	public Long PercentTestBlocked;
	
	public Long TotalAmount = 0L;	
	public String dailyDate;	
	public SimpleDateFormat sdf;	
	public Date today;
	private Date createdDate;
	public LineChartModel chart;
	public List<String> selectSuit;
	
	public TestCase updateTestCase;
	public TestCase copyTestCase;
	
	private List<TestCase> AllCase;
	private List<TestSuit> AllSuit;	
	
	public List<Integer> selectedCase;
	private String suitName;
	private String expecation;
	
	private String userName;

	private String dailyRecords;
	
	private Boolean isUpdateFlag = false;
	private Boolean isCreateFlag = false;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private TestCaseServise testCaseServise;
	
	@Autowired
	private TestSuitService testSuitService;
	
	@PostConstruct
	public void init(){
		createAnimatedModel();
		TotalAmount = testCaseServise.countTotal();
		userName = securityService.findLoggedInUsername();
	}
	
	public String backToLogin(){
		SecurityContextHolder.clearContext();
        return "redirectToLogin";
	}
	
	public String redirectToTestCase(){
		return "testcase.xhtml?faces-redirect=true";
	}

	public String getDailyDate() {
		sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.ENGLISH);
		today = new Date();
		dailyDate = sdf.format(today);
		
		return dailyDate;
	}
	
	public List<TestCase> getDailyRecords() {
		List<TestCase> todayTests = testCaseServise.getDailyTests();
		if(todayTests != null)
			return todayTests;
		return null;
	}
	
	public List<TestSuit> getAllSuit() {
		List<TestSuit> suits = testSuitService.findAll();
		if(suits != null)
			return suits;
		return null;
	}
	
	public List<TestCase> getAllCase() {
		List<TestCase> cases = testCaseServise.findAllTestCases();
		if(cases != null)
			return cases;
		return null;
	}		

	public boolean isAdminAllowedAccess(){
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("ADMIN");
	}
	
	public void deleteCase(){
		int id = selectedCase.get(0);
		TestCase testToDelete = testCaseServise.findbyId(id);
		testCaseServise.delete(testToDelete);	
	}

	public void updateCase(){		
		int id = selectedCase.get(0);
		TestCase selectedTestCase = testCaseServise.findbyId(id);			
		isUpdateFlag = true;
		updateTestCase = selectedTestCase;
		System.out.println("Copy case with flag - " + isUpdateFlag);
	}
	
	public void copyCase(){		
		int id = selectedCase.get(0);
		TestCase selectedTestCase = testCaseServise.findbyId(id);			
		isCreateFlag = true;
		copyTestCase = selectedTestCase;
		System.out.println("Copy case with flag - " + isCreateFlag);
	}
	
	public TestCase SelectedTestCase(){
		if(isUpdateFlag == true){					
			System.out.println("Selected Case update flag - " + isUpdateFlag);
			return updateTestCase;			
		}
		if(isCreateFlag == true){
			System.out.println("Selected Case copy flag - " + isCreateFlag);
			return copyTestCase;
		}
		return null;
	}
	
	public void update(){
		System.out.println("Updateddddd method");
		isUpdateFlag = false;
		testCaseServise.saveTestCase(updateTestCase);
	}
	
	public void copyTest(){
		System.out.println("Copyyyyyyyyy method");
		
		Date now = new Date();
		createdDate = now;
		
		TestCase createCase = new TestCase();
		
		createCase.setCaseName(copyTestCase.getCaseName());
		createCase.setEnvironment(copyTestCase.getEnvironment());
		createCase.setSubEnvironment(copyTestCase.getSubEnvironment());
		createCase.setAuthor(userName);
		createCase.setOwner(copyTestCase.getOwner());
		createCase.setDesription(copyTestCase.getDesription());
		createCase.setExecutedDate(copyTestCase.getExecutedDate());
		createCase.setCreatedDate(getCreatedDate());
		createCase.setPrerequisites(copyTestCase.getPrerequisites());
		createCase.setPriority(copyTestCase.getPriority());
		createCase.setStatus(TEST_NOT_RUN);

		isCreateFlag = false;
		testCaseServise.saveTestCase(createCase);
		
	}
	
	public void cancel(){
		isCreateFlag = false;
		isUpdateFlag = false;
	}
	
	public void saveSuit(){
		TestSuit suit = new TestSuit();
		
		suit.setTestSuitName(getSuitName());
		suit.setExpectation(getExpecation());
		
		testSuitService.save(suit);
	}
	
	public void deleteSuit(){
		int id = Integer.parseInt(selectSuit.get(0));
		TestSuit suit = testSuitService.findById(id);
		testSuitService.delete(suit);
	}
	
	private LineChartModel createAnimatedModel(){
		chart = new LineChartModel();
		
		chart.setAnimate(true);
		chart.setLegendPosition("se");
		chart.setSeriesColors("268436,D64B28,C0C0C0,E8E81B");
		chart.getAxes().put(AxisType.X, new CategoryAxis("Day/Month"));
		
		
		Axis yAxis = chart.getAxis(AxisType.Y);	
		Axis xAxis = chart.getAxis(AxisType.X);
		
		yAxis.setMin(0);
		yAxis.setMax(20);
		yAxis.setLabel("Test cases");
		
		xAxis.setMin(0);
		xAxis.setMax(10);
		
		LineChartSeries casePassed = new LineChartSeries();
		casePassed.setLabel(TEST_PASSED);		 		
		
		for(int i = 1; i != 10; i++){
			drawNodeOnGrid(casePassed, TEST_PASSED, i);
		}
		
		LineChartSeries caseFailed = new LineChartSeries();
		caseFailed.setLabel(TEST_FAILED);
		
		for(int i = 1; i != 10; i++){
			drawNodeOnGrid(caseFailed, TEST_FAILED, i);
		}
		
		LineChartSeries caseNotRun = new LineChartSeries();
		caseNotRun.setLabel(TEST_NOT_RUN);
		
		for(int i = 1; i != 10; i++){
			drawNodeOnGrid(caseNotRun, TEST_NOT_RUN, i);
		}
		
		LineChartSeries caseBlocked = new LineChartSeries();
		caseBlocked.setLabel(TEST_BLOCKED);
		
		for(int i = 1; i != 10; i++){
			drawNodeOnGrid(caseBlocked, TEST_BLOCKED, i);
		}
		
		chart.addSeries(casePassed);
		chart.addSeries(caseFailed);
		chart.addSeries(caseNotRun);
		chart.addSeries(caseBlocked);
		
		return chart;
	}
	
	public String dateFormat(int i, String pattern){
		int d = i;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		
		cal.add(Calendar.DAY_OF_MONTH, -d);
		String day = dateFormat.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, d);
		
		return day;
	}
	
	public void drawNodeOnGrid(LineChartSeries series, String status, int i){
		String day = dateFormat(i, PatternDayMonthYear);
		Long point = testCaseServise.countTestcases(day, status);
		series.set(dateFormat(i, PatternDayMonth), point);
	}
		
	public Long getTestPassed() {
		Long l = testCaseServise.countCases(TEST_PASSED);
		if(TotalAmount != 0){
			PercentTestPassed = (l * 100 / TotalAmount);
			
			if(l <= 0)
				return (long) 0;
			return l;
		}
		PercentTestPassed = (long) 0;
		return (long) 0;
	}
	public void setTestPassed(Long testPassed) {
		TestPassed = testPassed;
	}
	public Long getTestFailed() {
		Long l = testCaseServise.countCases(TEST_FAILED);
		if(TotalAmount != 0){
			PercentTestFailed = (l * 100 / TotalAmount);			
			if(l <= 0)
				return (long) 0;
			return l;
		}
		PercentTestFailed = (long) 0;
		return (long) 0;
	}
	public void setTestFailed(Long testFailed) {
		TestFailed = testFailed;
	}
	public Long getTestNotRun() {
		Long l = testCaseServise.countCases(TEST_NOT_RUN);
		if(TotalAmount != 0){
			PercentTestNotRun = (l * 100 / TotalAmount);
			if(l <= 0)
				return (long) 0;
			return l;
		}
		PercentTestNotRun = (long) 0;
		return (long) 0;
	}
	public void setTestNotRun(Long testNotRun) {
		TestNotRun = testNotRun;
	}
	public Long getTestBlocked() {
		Long l = testCaseServise.countCases(TEST_BLOCKED);
		if(TotalAmount != 0){
			PercentTestBlocked = (l * 100 / TotalAmount);
			if(l <= 0)
				return (long) 0;
			return l;
		}
		PercentTestBlocked = (long) 0;
		return (long) 0;
	}
	public void setTestBlocked(Long testBlocked) {
		TestBlocked = testBlocked;
	}
	public Long getPercentTestPassed() {
		return PercentTestPassed;
	}
	public void setPercentTestPassed(Long percentTestPassed) {
		PercentTestPassed = percentTestPassed;
	}
	public Long getPercentTestFailed() {
		return PercentTestFailed;
	}
	public void setPercentTestFailed(Long percentTestFailed) {
		PercentTestFailed = percentTestFailed;
	}
	public Long getPercentTestNotRun() {
		return PercentTestNotRun;
	}
	public void setPercentTestNotRun(Long percentTestNotRun) {
		PercentTestNotRun = percentTestNotRun;
	}
	public Long getPercentTestBlocked() {
		return PercentTestBlocked;
	}
	public void setPercentTestBlocked(Long percentTestBlocked) {
		PercentTestBlocked = percentTestBlocked;
	}

	public String getSuitName() {
		return suitName;
	}

	public void setSuitName(String suitName) {
		this.suitName = suitName;
	}

	public String getExpecation() {
		return expecation;
	}

	public void setExpecation(String expecation) {
		this.expecation = expecation;
	}

	public Long getTotalAmount() {
		return TotalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		TotalAmount = totalAmount;
	}

	public List<String> getSelectSuit() {
		return selectSuit;
	}

	public void setSelectSuit(List<String> selectSuit) {
		this.selectSuit = selectSuit;
	}

	public void setDailyRecords(String dailyRecords) {
		this.dailyRecords = dailyRecords;
	}

	public void setAllCase(List<TestCase> allCase) {
		AllCase = allCase;
	}

	public void setAllSuit(List<TestSuit> allSuit) {
		AllSuit = allSuit;
	}

	public LineChartModel getChart() {
		return chart;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public TestCase getUpdateTestCase() {
		return updateTestCase;
	}

	public void setUpdateTestCase(TestCase updateTestCase) {
		this.updateTestCase = updateTestCase;
	}

	public List<Integer> getSelectedCase() {
		return selectedCase;
	}

	public void setSelectedCase(List<Integer> selectedCase) {
		this.selectedCase = selectedCase;
	}

	public Boolean getIsUpdateFlag() {
		return isUpdateFlag;
	}

	public void setIsUpdateFlag(Boolean isUpdateFlag) {
		this.isUpdateFlag = isUpdateFlag;
	}

	public Boolean getIsCreateFlag() {
		return isCreateFlag;
	}

	public void setIsCreateFlag(Boolean isCreateFlag) {
		this.isCreateFlag = isCreateFlag;
	}

	public TestCase getCopyTestCase() {
		return copyTestCase;
	}

	public void setCopyTestCase(TestCase copyTestCase) {
		this.copyTestCase = copyTestCase;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
