package hr.vsite.services.interfaces;

import java.util.List;

import hr.vsite.model.TestCase;

public interface TestCaseServise {

	void save(TestCase testCase);
	
	void delete(TestCase testcase);
	
	void update(TestCase testCase);
	
	TestCase findbyId(int id);
	
	List<TestCase> getDailyTests();
	
	List<TestCase> ovredueTests(String name);
	
	List<TestCase> testHasBeenAssigned(String username);
	
	Long countOvredueTests(String user);
	
	List<TestCase> findAllCasesWithParametar(String SuitName);
	
	Long countTestcases(String date, String status);
	
	Long countCases(String Status);
	
	Long countTotal();
	
	List<TestCase> findAllTestCases();

	void saveTestCase(TestCase testCase);
	
	
}
