package hr.vsite.services.interfaces;

import java.util.List;

import hr.vsite.model.TestCase;

public interface TestCaseService {

	void save(TestCase testCase);
	
	void delete(TestCase testcase);
	
	TestCase findbyId(int id);
	
	List<TestCase> getDailyTests();
	
	List<TestCase> ovredueOrAssignedTests(String name);
	
	Long countOvredueTests(String user);
	
	List<TestCase> findAllCasesWithParametar(String SuitName);
	
	Long countTestcases(String date, String status);
	
	Long countCases(String Status, Integer start, Integer end);
	
	List<TestCase> findAllTestCases();
	
	Long countStartEndDate(Integer d1, Integer d2);
	
	List<TestCase> findByCaseNameAuthorOwner(String name, String author, String owner);
}
